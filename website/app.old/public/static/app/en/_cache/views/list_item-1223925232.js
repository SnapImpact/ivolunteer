/* Start ----------------------------------------------------- views/list_item.js*/

// ==========================================================================
// App.ListItemView
// ==========================================================================

require('core');

/** @class

  (Document Your View Here)

  @extends SC.View
  @author AuthorName
  @version 0.1
*/
App.ListItemView = SC.View.extend(
/** @scope App.ListItemView.prototype */ {

  // TODO: Add your own code here.
  
  emptyElement: '<div class="list-item"></div>',
  
  /** The record we want to display. */
  content: null,
  
  /** Selection state. */
  isSelected: false,
  
  _contentObserver: function() {
    var content = this.get('content') ;
    var value = (content) ? content.get('name') : '(No Value)';
    this.set('asHTML', value) ;
  }.observes('*organziation.name'),
  
  _isSelectedObserver: function() {
    this.setClassName('sel', this.get('isSelected')) ;
  }.observes('isSelected')
  
}) ;


/* End ------------------------------------------------------- views/list_item.js*/

