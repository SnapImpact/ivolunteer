/* Start ----------------------------------------------------- controllers/organization_list.js*/

// ==========================================================================
// App.OrganizationListController
// ==========================================================================

require('core');

/** @class

  (Document Your View Here)

  @extends SC.CollectionController
  @author AuthorName
  @version 0.1
  @static
*/
App.organizationListController = SC.CollectionController.create(
/** @scope App.organizationListController */ {

  // TODO: Add your own code here.
  content: SC.Collection.create({ recordType: App.Organizations } )

}) ;


/* End ------------------------------------------------------- controllers/organization_list.js*/

