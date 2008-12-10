// ==========================================================================
// App
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

// This is the function that will start your app running.  The default
// implementation will load any fixtures you have created then instantiate
// your controllers and awake the elements on your page.
//
// As you develop your application you will probably want to override this.
// See comments for some pointers on what to do next.
//
function main() {

  // Step 1: Load Your Model Data
  // The default code here will load the fixtures you have defined.
  // Comment out the preload line and add something to refresh from the server
  // when you are ready to pull data from your server.
  App.server.preload(App.FIXTURES) ;
  
  /*
  App.server.listFor({recordType: App.Organizations});
  App.server.listFor({recordType: App.Events});
  App.server.listFor({recordType: App.Location});
  App.server.listFor({recordType: App.Timestamp});
  App.server.listFor({recordType: App.OrganizationType});
  App.server.listFor({recordType: App.Timeframe});
  App.server.listFor({recordType: App.InterestArea});
  App.server.listFor({recordType: App.Distance});
  */

  // TODO: refresh() any collections you have created to get their records.
  // ex: App.contacts.refresh() ;

  // Step 2: Instantiate Your Views
  // The default code just activates all the views you have on the page. If
  // your app gets any level of complexity, you should just get the views you
  // need to show the app in the first place, to speed things up.
  SC.page.awake() ;

  // Step 3. Set the content property on your primary controller.
  // This will make your app come alive!

  // TODO: Set the content property on your primary controller
  // ex: App.contactsController.set('content',App.contacts);
  var records = App.Events.collection() ;
  App.masterController.set('content', records) ;  
  records.refresh() ;

  var orgRecords = App.Organizations.collection();
  App.orgMasterController.set('content', orgRecords);
  orgRecords.refresh();

  var filterRecords = App.FilterBase.collection();
  filterRecords.set('orderBy', ['type','name']);
  App.filterController.set('content', filterRecords);
  filterRecords.refresh();

} ;
