//
//  SettingsViewController.m
//  iPhone
//
//  Created by Ryan Schneider on 4/13/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "SettingsViewController.h"


@implementation SettingsViewController

@synthesize backgroundButton;
@synthesize zipcodeField;
@synthesize nameField;
@synthesize emailField;
@synthesize useZipCode;
@synthesize scrollView;
@synthesize floatingView;
@synthesize settings;

#define SETTINGS_KEY @"Settings"
#define SETTINGS_KEY_NAME @"name"
#define SETTINGS_KEY_EMAIL @"email"
#define SETTINGS_KEY_ZIPCODE @"zipcode"
#define SETTINGS_KEY_USE_ZIPCODE @"useZipcode"

/*
// The designated initializer. Override to perform setup that is required before the view is loaded.
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        // Custom initialization
    }
    return self;
}
*/

/*
// Implement loadView to create a view hierarchy programmatically, without using a nib.
- (void)loadView {
}
*/

- (void) updateSettings {
   self.nameField.text = [self.settings objectForKey: SETTINGS_KEY_NAME];
   self.emailField.text = [self.settings objectForKey: SETTINGS_KEY_EMAIL];
   self.zipcodeField.text = [self.settings objectForKey: SETTINGS_KEY_ZIPCODE];
   self.useZipCode.on = [[self.settings objectForKey: SETTINGS_KEY_USE_ZIPCODE] boolValue];
}

- (void) loadSettings {
   [self.settings setObject: self.nameField.text forKey: SETTINGS_KEY_NAME ];
   [self.settings setObject: self.emailField.text forKey: SETTINGS_KEY_EMAIL ];
   [self.settings setObject: self.zipcodeField.text forKey: SETTINGS_KEY_ZIPCODE ];
   [self.settings setObject: [NSNumber numberWithBool: self.useZipCode.on] forKey: SETTINGS_KEY_USE_ZIPCODE ];   
}

// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
   [super viewDidLoad];
   self.scrollView.contentSize = CGSizeMake(320, 550);
	self.scrollView.delaysContentTouches = NO;
   NSMutableDictionary* settings_ = (NSMutableDictionary*) CFPreferencesCopyAppValue((CFStringRef) SETTINGS_KEY, 
                                                          kCFPreferencesCurrentApplication);
   if (settings_) {
      self.settings = settings_;
   }
   else {
      self.settings = [NSMutableDictionary dictionary];
      [self loadSettings];
   }

   [self updateSettings];
}

/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
*/

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning]; // Releases the view if it doesn't have a superview
    // Release anything that's not essential, such as cached data
}


- (void)dealloc {
   [super dealloc];
   self.zipcodeField = nil;
   self.nameField = nil;
   self.emailField = nil;
   self.scrollView = nil;
   self.settings = nil;
   self.useZipCode = nil;
   self.floatingView = nil;
   self.backgroundButton = nil;
}

- (IBAction)zipcodeUpdated
{
	if ([self.zipcodeField.text length] >= 5)
	{
      [self dismissKeyboard];
	}
}

- (IBAction) dismissKeyboard
{
   [ self.zipcodeField resignFirstResponder ]; 
   [ self.nameField resignFirstResponder ]; 
   [ self.emailField resignFirstResponder ]; 
   [ self scrollUp ];
}

-(IBAction)scrollDown
{
	[self.scrollView setContentOffset:CGPointMake(0,100) animated:YES];
}

-(IBAction)scrollUp
{
	[self.scrollView setContentOffset:CGPointMake(0,0) animated:YES];
}

-(IBAction)saveSettings
{
   [self loadSettings];
   CFPreferencesSetAppValue((CFStringRef) SETTINGS_KEY, self.settings, kCFPreferencesCurrentApplication);
   UIAlertView* alert = [[UIAlertView alloc] initWithTitle: @"Settings Saved" message: @"Your settings have been saved." delegate: nil cancelButtonTitle: @"Ok" otherButtonTitles: nil];
   [alert show];
   [alert release];
}

-(IBAction)resetSettings
{
   UIActionSheet* action = [[UIActionSheet alloc] initWithTitle: @"Reset All Settings?" delegate: self cancelButtonTitle: @"Cancel" destructiveButtonTitle: @"Reset" otherButtonTitles: nil ];
   [action showInView: self.floatingView];
}

- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex
{
   if(buttonIndex == 0) {
      [self.settings setObject: @"" forKey: SETTINGS_KEY_NAME ];
      [self.settings setObject: @"" forKey: SETTINGS_KEY_EMAIL ];
      [self.settings setObject: @"" forKey: SETTINGS_KEY_ZIPCODE ];
      [self.settings setObject: [NSNumber numberWithBool: NO] forKey: SETTINGS_KEY_USE_ZIPCODE ];   
      [self updateSettings];
   }
}

@end

