<?php

/*
Plugin Name: iVolunteer
Plugin URI: http://wordpress.org/extend/plugins/iVolunteer/
Description: Retrieves local community volunteer opportunities and displays them in the sidebar. After activating the Plugin, go to Widgets to add iVolunteer to your sidebar.
Version: 1.1.3
Author: Neil Simon
Author URI: http://actionfeed.org/
*/

/*
 Copyright 2009 iVolunteer.

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 51 Franklin St, 5th Floor, Boston, MA 02110 USA
*/

// From wordpress271/js/tinymce/plugins/spellchecker/classes/utils
include_once ('JSON.php');

// Constants
define ('IVOLUNTEER_PLUGIN_VERSION', 'iVolunteer-v1.1.3');
define ('IVOLUNTEER_OPTIONS',        'iVolunteer_options');
define ('IVOLUNTEER_API_URL',        'http://actionfeed.org/server/resources/events/consolidated/');
define ('IVOLUNTEER_URL',            'http://actionfeed.org/');

function iVolunteer_cURL ()
    {
    // Init curl session
    $hCurl = curl_init ();

    // Return response from curl_exec() into variable
    curl_setopt ($hCurl, CURLOPT_RETURNTRANSFER, 1);

    // Max seconds to allow cURL to execute
    curl_setopt ($hCurl, CURLOPT_TIMEOUT, 5);

    // Get the visitor's ip address
    if (($ip = $_SERVER ['REMOTE_ADDR']) == "127.0.0.1")
        {
        // Use this for testing on //localhost
        $ip = "67.166.52.44";
        }

    // Set the API URL
    curl_setopt ($hCurl, CURLOPT_URL, IVOLUNTEER_API_URL . '?ip=' . $ip);

    // Exec the call
    $response = curl_exec ($hCurl);

    // Close the session
    curl_close ($hCurl);

    // Output the results
    return ($response);
    }

function iVolunteer_buildSidebar (&$buf)
    {
    $rc = 1;  // Reset to 0 on success

    // Get the iVolunteer local events list -- returned as XML
    if (($response = iVolunteer_cURL ()) == FALSE)
        {
        // This can happen when the site is found, but the API is down
        // -- just exit
        printf ("No opportunities found -- please try later.<br />");
        }

    elseif (strpos ($response, "Not Found"))
        {
        // This can happen when the site is NOT found -- just exit
        printf ("No opportunities found - please try later.<br />");
        }

    else
        {
        // Instantiate JSON object
        $json_iVolunteer = new Moxiecode_JSON ();

        // Decode the returned JSON
        if (($consolidated = $json_iVolunteer->decode ($response)) == 0)
            {
            // No data came back
            printf ("No opportunities found, please try later.<br />");
            }

        else
            {
            // Parse JSON and load sidebar buf
            parse_json ($consolidated, $buf);

            $rc = 0;
            }

        // NULL the object
        $json_iVolunteer = 0;
        }

    return ($rc);
    }

function parse_json ($consolidated, &$buf)
    {
    $organizations = $consolidated ['organizations'];
    $timestamps    = $consolidated ['timestamps'];     // iso 8601 format
    $events        = $consolidated ['events'];
    $locations     = $consolidated ['locations'];

    $locationsNormalized = array ();
    foreach ($locations as $location) 
        {
        $locationsNormalized [$location ['id']] = $location;
        }

    $timestampsNormalized = array ();
    foreach ($timestamps as $timestamp) 
        {
        $timestampsNormalized [$timestamp ['id']] = $timestamp;
        }

    $buf .= 'Upcoming Volunteer Opportunities:<br /><br />';

    // Open list
    $buf .= '<ul>';

    // Load existing options from wp database
    $iVolunteer_options = get_option (IVOLUNTEER_OPTIONS);

    // Get option for number of events to show
    $maxEvents = $iVolunteer_options ['numEventsToDisplay'];

    // If not setup...
    if ($maxEvents == 0)
        {
        // Default to 5
        $maxEvents = 5;
        }

    $eventCount = 0;

    foreach ($events as $event)
        {
        if ($eventCount++ < $maxEvents)
            {
            $organizationIds = (array) $event ['organizationCollection'];
            $timestampIds    = (array) $event ['timestampCollection'];
            $locationIds     = (array) $event ['locationCollection'];

            $event ['organizations'] = array ();
            $event ['timestamps']    = array ();
            $event ['locations']     = array ();

            foreach ($organizationIds as $organizationId)
                {
                $event ['organizations'] [$organizationId] = $organizations [$organizationId];
                }

            foreach ($timestampIds as $timestampId)
                {
                $event ['timestamp'] = $timestampsNormalized [$timestampId];
                }

            foreach ($locationIds as $locationId)
                {
                $event ['location'] = $locationsNormalized [$locationId];
                }

            $eventTitle    = $event ['title'];
            $eventTime     = $event ['timestamp'] ['timestamp'];
            $eventLocation = $event ['location'] ['city']  . ', ' .
                             $event ['location'] ['state'] . ', ' .
                             $event ['location'] ['zip'];
            $eventPhone    = $event ['phone'];
            $eventUrl      = $event ['url'];

            // Open the list item
            $buf .= '<li>';

            // Remove non-ascii characters from $eventTitle
            $eventTitleCleaned = preg_replace ('/[^(\x20-\x7F)]*/', '', $eventTitle);

            // Display the event title as a clickable link
            $buf .= '<a href="' . $eventUrl . '" target="_blank">' . $eventTitleCleaned . '</a><br />';

            // Reformat the time/date to "Monthname, dd, yyyy  [hh:mm] am/pm"
            iVolunteer_format_datetime ($eventTime, $formattedDateTime);

            // Display the date -- but stay on the same line
            $buf .= $formattedDateTime;

            // Line break
            $buf .= '<br />';

            // Some locations have a trailing space after city name (replace " ," with ", ")
            $eventLoation = iVolunteer_ireplace (" ,", ", ", $eventLocation);

            // Some locations have double spaces in them (replace "  " with " ")
            $eventLoation = iVolunteer_ireplace ("  ", " ", $eventLocation);

            // Display the location line (city, state, zip)
            $buf .= $eventLocation    . '<br />';

            // If the phone number is NOT blank...
            if ($eventPhone != "")
                {
                $formattedPhone = "";

                // Reformat the phone number to "(xxx) xxx-xxxx" style
                iVolunteer_format_phone ($eventPhone, $formattedPhone);

                // Display it
                $buf .= $formattedPhone . '<br />';
                }

            // Close the list item
            $buf .= '<br /></li>';
            }
        }

    // Close list
    $buf .= '</ul>';
    }

function iVolunteer_ireplace ($needle, $replacement, $haystack)
    {
    $i = 0;

    while ($pos = strpos (strtolower ($haystack), $needle, $i))
        {
        $haystack = substr ($haystack, 0, $pos) . $replacement . substr ($haystack, $pos + strlen ($needle));

        $i = $pos + strlen ($replacement);
        }

    return ($haystack);
    }


function iVolunteer_format_datetime ($eventTime, &$formattedDateTime)
    {
    // Place 8601 time into a Unix-timestamp, for the date() function below
    $unixTime = mktime (substr ($eventTime, 11, 2), // hour
                        substr ($eventTime, 14, 2), // minute
                        0,                          // second
                        substr ($eventTime, 5, 2),  // month
                        substr ($eventTime, 8, 2),  // day
                        substr ($eventTime, 0, 4)); // year

    // Split-out the time (ex. 08:00)
    $timePart = substr ($eventTime, 11, 5);

    // If there is NOT an actual time
    if ($timePart == "00:00")
        {
        // Don't show time
        $formattedDateTime = date ("F j, Y", $unixTime);
        }
    else
        {
        // Show time as "Monthname, dd, yyyy  [hh:mm] am/pm"
        $formattedDateTime = date ("F j, Y  g:i a", $unixTime);
        }
    }

function iVolunteer_format_phone ($eventPhone, &$formattedPhone)
    {
    // Strip-out all dots, parens, spaces, hyphens... leaving only a 10-char numeric string
    $allNumeric = preg_replace ("/[\. \(\)\-]/", "", $eventPhone);

    // Format into "(xxx) xxx-xxxx" style
    for ($i = 0; $i < 10; $i++)
        {
        if ($i == 0)
            {
            $formattedPhone .= "(";
            }
        elseif ($i == 3)
            {
            $formattedPhone .= ") ";
            } 
        elseif ($i == 6)
            {
            $formattedPhone .= "-";
            }

        $formattedPhone .= substr ($allNumeric, $i, 1);
        }
    }

function iVolunteer_initWidget ()
    {
    // MUST be able to register the widget... else exit
    if (function_exists ('register_sidebar_widget'))
        {
        // Declare function -- called from Wordpress -- during page-loads
        function iVolunteer_widget ($args)
            {
            // Load existing options from wp database
            $iVolunteer_options = get_option (IVOLUNTEER_OPTIONS);

            // Accept param array passed-in from WP (e.g. $before_widget, $before_title, etc.), theme CSS styles
            extract ($args);

            // Display sidebar title above the about-to-be-rendered iVolunteer events table
            echo $before_widget . $before_title .
                 '<a href="http://actionfeed.org" title="Visit iVolunteer" target="_blank">iVolunteer</a>' .
                 $after_title;

            // Open a plugin-version-tracking DIV tag
            printf ("<div id=\"%s\">", IVOLUNTEER_PLUGIN_VERSION);

            $buf = '';

            // Dynamically build the table and display it
            if (iVolunteer_buildSidebar ($buf) == 0)
                {
                printf ("%s", $buf);
                }

            // Close the plugin-version-tracking DIV tag
            printf ("</div>");

            echo $after_widget;
            }

        // Register the widget function to be called from Wordpress on each page-load
        register_sidebar_widget ('iVolunteer', 'iVolunteer_widget');
        }
    }

function iVolunteer_sidebar ()
    {
    // This function is not called from within the Plugin.
    //
    // It is documented on the iVolunteer Plugin Installation page
    // for manual sidebar.php installs for non-widgetized themes.
    //
    // From non-widgetized themes, call this function directly from sidebar.php.
    //
    // For specific instructions, please see the Instructions in the readme.txt.

    // Load existing options from wp database
    $iVolunteer_options = get_option (IVOLUNTEER_OPTIONS);

    // Display sidebar title above the about-to-be-rendered iVolunteer events table
    $buf  = '<li><a href="http://actionfeed.org" title="Visit iVolunteer" target="_blank"><h2>iVolunteer</h2></a>'; 

    // Open a plugin-version-tracking DIV tag
    $buf .= '<div id="' . IVOLUNTEER_PLUGIN_VERSION . '">';

    // Dynamically build the table and display it
    iVolunteer_buildSidebar ($buf);

    // Close the plugin-version-tracking DIV tag
    $buf .= '</div></li>';

    printf ("%s", $buf);
    }

function iVolunteer_createOptions ()
    {
    // Create the initialOptions array of keys/values
    $iVolunteer_initialOptions = array ('numEventsToDisplay' => 5);

    // Store the initial options to the wp database
    add_option (IVOLUNTEER_OPTIONS, $iVolunteer_initialOptions);
    }

function iVolunteer_deleteOptions ()
    {
    // Remove the iVolunteer options array from the wp database
    delete_option (IVOLUNTEER_OPTIONS);
    }

function iVolunteer_updateSettingsPage ()
    {
    // Load existing options from wp database
    $iVolunteer_options = get_option (IVOLUNTEER_OPTIONS);

    // Localize all displayed strings
    $iVolunteer_enterOptionsStr       = __('Please enter the iVolunteer Plugin options:',    'ivolunteer');
    $iVolunteer_numEventsToDisplayStr = __('Number of events to display in the sidebar:',    'ivolunteer');
    $iVolunteer_saveStr               = __('Save',                                           'ivolunteer');
    $iVolunteer_optionsSavedStr       = __('iVolunteer Plugin options saved successfully.',  'ivolunteer');
    $iVolunteer_show5Str              = __('Show 5 events in the sidebar',                   'ivolunteer');
    $iVolunteer_show10Str             = __('Show 10 events in the sidebar',                  'ivolunteer');
    $iVolunteer_show15Str             = __('Show 15 events in the sidebar',                  'ivolunteer');

    // If data fields contain values...
    if (isset ($_POST ['numEventsToDisplay']))
        {
        // Copy the fields to the persistent wp options array
        if ($_POST ['numEventsToDisplay'] == "5")
            {
            $iVolunteer_options ['numEventsToDisplay'] = 5;
            }

        else if ($_POST ['numEventsToDisplay'] == "10")
            {
            $iVolunteer_options ['numEventsToDisplay'] = 10;
            }

        else   // must be 15
            {
            $iVolunteer_options ['numEventsToDisplay'] = 15;
            }

        // Store changed options back to wp database
        update_option (IVOLUNTEER_OPTIONS, $iVolunteer_options);

        // Display update message to user
        echo '<div id="message" class="updated fade"><p>' . $iVolunteer_optionsSavedStr . '</p></div>';
        }

    // Initialize data fields for radio buttons
    $show5  = "";
    $show10 = "";
    $show15 = "";

    // Set variable for form to use to show sticky-value for radio button
    if ($iVolunteer_options ['numEventsToDisplay'] == 5)
        {
        $show5 = "checked";
        }

    else if ($iVolunteer_options ['numEventsToDisplay'] == 10)
        {
        $show10 = "checked";
        }

    else // must be 15
        {
        $show15 = "checked";
        }

    // Display the options form to the user

    echo
     '<div class="wrap">

      <h3>&nbsp;' . $iVolunteer_enterOptionsStr . '</h3>

      <form action="" method="post">

      <table border="0" cellpadding="10">

      <tr>
      <td width="300"><input type="radio" name="numEventsToDisplay" value="5"  ' . $show5  . ' />
      ' . $iVolunteer_show5Str . '<br />
                      <input type="radio" name="numEventsToDisplay" value="10" ' . $show10 . ' />
      ' . $iVolunteer_show10Str . '<br />
                      <input type="radio" name="numEventsToDisplay" value="15" ' . $show15 . ' />
      ' . $iVolunteer_show15Str . '</td>

      </tr>

      </table>

      <p>&nbsp;<input type="submit" value="' . $iVolunteer_saveStr . '" /></p>

      </form>

      </div>';
    }

function iVolunteer_addSubmenu ()
    {
    // Define the options for the submenu page
    add_submenu_page ('options-general.php',             // Parent page
                      'iVolunteer page',                 // Page title, shown in titlebar
                      'iVolunteer',                      // Menu title
                      10,                                // Access level all
                      __FILE__,                          // This file displays the options page
                      'iVolunteer_updateSettingsPage');  // Function that displays options page
    }

// Initialize for localized strings
load_plugin_textdomain ('ivolunteer', 'wp-content/plugins/ivolunteer');

// Runs only once at activation time
register_activation_hook (__FILE__, 'iVolunteer_createOptions');

// Runs only once at deactivation time
register_deactivation_hook (__FILE__, 'iVolunteer_deleteOptions');

// Load the widget, show it in the widget control in the admin section
add_action ('plugins_loaded', 'iVolunteer_initWidget');

// Add the iVolunteer submenu to the Settings page
add_action ('admin_menu', 'iVolunteer_addSubmenu');

?>
