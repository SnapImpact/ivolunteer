/* Start ----------------------------------------------------- models/event.js*/

// ==========================================================================
// App.Event
// ==========================================================================

require('core');

/**
 * @class
 * 
 * (Document your class here)
 * 
 * @extends SC.Record
 * @author AuthorName
 * @version 0.1
 */
App.Event = SC.Record.extend(
/** @scope App.Event.prototype */ {

  // TODO: Add your own code here.
	properties: ['name', 
	             'organizationName', 
	             'date', 
	             'time', 
	             'location', 
	             'contactName', 
	             'website', 
	             'isRecurring', 
	             'interestArea', 
	             'phoneNumber', 
	             'contactEmail'],

}) ;


/* End ------------------------------------------------------- models/event.js*/

