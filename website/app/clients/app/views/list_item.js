// ==========================================================================
// App.ListItemView
// ==========================================================================

/*
 *  Copyright (c) 2008 Boulder Community Foundation - iVolunteer
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

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
  emptyElement: '<div class="list-item"><br></div>',
  
  /** The record we want to display. */
  content: [],
  contentBindingDefault: SC.Binding.MultipleNotEmpty,
  key: "name",


  render: function() {
    var html = [];
    var content = this.get('content');
    var key = this.get('key');

     content.each( function(record){
        html.push(record.get(key));
        html.push('<br>');
    });
    this.set('innerHTML', html.join(" "));
}.observes('content')

}) ;
