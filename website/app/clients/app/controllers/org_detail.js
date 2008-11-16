// ==========================================================================
// App.OrgDetailController
// ==========================================================================

require('core');

/** @class

  (Document Your View Here)

  @extends SC.Object
  @author AuthorName
  @version 0.1
  @static
*/
App.orgDetailController = SC.ObjectController.create(
/** @scope App.orgDetailController */ {

  // TODO: Add your own code here.
  contentBinding: 'App.orgMasterController.selection',

  commitChangesImmediately: false

}) ;
