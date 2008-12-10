// ==========================================================================
// App.FilterBase
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

  (Document your class here)

  @extends SC.Record
  @author AuthorName
  @version 0.1
*/
App.FilterBase = SC.Record.extend(
/** @scope App.FilterBase.prototype */ {

    dataSource: App.server,

    /*
  A list of all the properties which should be handled by the framework.
  Additionally to the here mentioned ones, **guid** and **id** (?) are added implicitly.
  */
    properties: [],

    /*
  define the URL for this Record type.
     - updates will be POSTed to '/ajaxcom/contact/update'
     - new records will be POSTed to '/ajaxcom/contact/create'
     - and existing records will be fetched (GET) from
       '/ajacom/contact/show/23' (if the record has guid=23 and
        only one record is fetched)
  */
    resourceURL: '',

    type: function() {
        if ( this.instanceOf(App.Distance) )
        {
            return 'Distance';
        } else if (this.instanceOf(App.InterestArea))
        {
            return 'Interest Area';
        } else if (this.instanceOf(App.OrganizationType))
        {
            return 'Organization Type';
        }
        else if (this.instanceOf(App.Timeframe))
        {
            return 'Timeframe';
        }
        
        return '';
    }.property()

}) ;
