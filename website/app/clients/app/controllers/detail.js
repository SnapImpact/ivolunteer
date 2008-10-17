// ==========================================================================
// App.DetailController
// ==========================================================================

require('core');

/** @class

  (Document Your View Here)

  @extends SC.ObjectController
  @author AuthorName
  @version 0.1
  @static
*/
App.detailController = SC.ObjectController.create(
/** @scope App.detailController */ {

  // TODO: Add your own code here.
  contentBinding: 'App.masterController.selection',
  
  commitChangesImmediately: false

}) ;
