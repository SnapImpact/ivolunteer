// ==========================================================================
// App.Organizations
// ==========================================================================

require('core');

/** @class

  (Document your class here)

  @extends SC.Record
  @author AuthorName
  @version 0.1
*/
App.Organizations = SC.Record.extend(
/** @scope App.Organizations.prototype */ {

  // TODO: Add your own code here.
  // EM: I think this is the correct property name
  dataSource: App.server, 

  /*
  A list of all the properties which should be handled by the framework.
  Additionally to the here mentioned ones, **guid** and **id** (?) are added implicitly.
  */
  properties: ['name', 'description', 'phone', 'email',
       'url', 'street', 'city', 'state', 'zip', 'source_key', 'source_url'],
  primaryKey: 'id',

  /*
  define the URL for this Record type. 
     - updates will be POSTed to '/ajaxcom/contact/update' 
     - new records will be POSTed to '/ajaxcom/contact/create' 
     - and existing records will be fetched (GET) from
       '/ajacom/contact/show/23' (if the record has guid=23 and
        only one record is fetched)
  */
  wrapperElement: "organizations",
  resourceURL: 'resources/organizations'

}) ;

App.Organizations.allRecords = SC.Collection.create({ recordType: App.Organizations }); // returns all records
