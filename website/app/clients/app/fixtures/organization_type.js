// ==========================================================================
// App.OrganizationType Fixtures
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
        type: 'OrganizationType',
        "@uri":"http://localhost:8080/server/resources/organizationType/8190FFB1-DCFB-4BC5-86D5-586FCADE7EC6/",
        "id":"8190FFB1-DCFB-4BC5-86D5-586FCADE7EC6",
        "name":"Non-Profit"
    },{
        type: 'OrganizationType',
        "@uri":"http://localhost:8080/server/resources/organizationType/FD96F94A-7B44-4EE1-B4A6-0D9686ED4C48/",
        "id":"FD96F94A-7B44-4EE1-B4A6-0D9686ED4C48",
        "name":"Government"
    },{
        type: 'OrganizationType',
        "@uri":"http://localhost:8080/server/resources/organizationType/6BCDFFC9-6480-4A31-B1EC-5C70F40C81F6/",
        "id":"6BCDFFC9-6480-4A31-B1EC-5C70F40C81F6",
        "name":"School"
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
