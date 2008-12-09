// ==========================================================================
// App.Distance Fixtures
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

require('core') ;

App.FIXTURES = App.FIXTURES.concat([
{
    type: 'Distance',
    "@uri":"http://localhost:8080/server/resources/location/2DA08E5C-6C10-492E-8F98-39BFC1A4DE02/",
    "bucket":"5",
    "id":"2DA08E5C-6C10-492E-8F98-39BFC1A4DE02"
},{
    type: 'Distance',
    "@uri":"http://localhost:8080/server/resources/location/668E2229-5C16-40E8-9A7A-6450785AE930/",
    "bucket":"10",
    "id":"668E2229-5C16-40E8-9A7A-6450785AE930"
},{
    type: 'Distance',
    "@uri":"http://localhost:8080/server/resources/location/1971B3EF-F8AB-4B70-A783-BBD7773ABB44/",
    "bucket":"25",
    "id":"1971B3EF-F8AB-4B70-A783-BBD7773ABB44"
},{
    type: 'Distance',
    "@uri":"http://localhost:8080/server/resources/location/785ED2FC-F6FC-45A2-BEA1-6BCDC60C2479/",
    "bucket":"50",
    "id":"785ED2FC-F6FC-45A2-BEA1-6BCDC60C2479"
},{
    type: 'Distance',
    "@uri":"http://localhost:8080/server/resources/location/265F52EC-D37C-4F22-9AB2-6C75BA7D2105/",
    "bucket":"100",
    "id":"265F52EC-D37C-4F22-9AB2-6C75BA7D2105"
}

// TODO: Add your data fixtures here.
// All fixture records must have a unique guid and a type matching the
// name of your contact.  See the example below.

// { guid: 1,
//   type: 'Contact',
//   firstName: "Michael",
//   lastName: "Scott"
// },
//
// { guid: 2,
//   type: 'Contact',
//   firstName: "Dwight",
//   lastName: "Schrute"
// },
//
// { guid: 3,
//   type: 'Contact',
//   firstName: "Jim",
//   lastName: "Halpert"
// },
//
// { guid: 4,
//   type: 'Contact',
//   firstName: "Pam",
//   lastName: "Beesly"
// },
//
// { guid: 5,
//   type: 'Contact',
//   firstName: "Ryan",
//   lastName: "Howard"
// }

]);
