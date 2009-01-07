// ==========================================================================
// App.FilterItemView
// ==========================================================================

require('core');

/** @class

  (Document Your View Here)

  @extends SC.View
  @author AuthorName
  @version 0.1
*/
App.FilterItemView = SC.View.extend(
/** @scope App.FilterItemView.prototype */ {

  // TODO: Add your own code here.
  emptyElement: '<div class="filter-item"></div>',
  
  content: [],
  contentBindingDefault: SC.Binding.MultipleNotEmpty,


  render: function() {
    var html = [];
    var content = this.get('content');

    html.push(content.get('name'));

    this.set('innerHTML', html.join(" "));
}.observes('content')

}) ;
