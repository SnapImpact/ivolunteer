// ==========================================================================
// App.Location Fixtures
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
        type : 'Location',
        "@uri":"http://localhost:8080/server/resources/location/da5081ce-0ad5-43c7-a275-09805dadbb79/",
        "id":"da5081ce-0ad5-43c7-a275-09805dadbb79",
        "street":"null null"
    },{
        type : 'Location',
        "@uri":"http://localhost:8080/server/resources/location/62af945c-8f65-43e3-8e8a-9fa990f6fbb5/",
        "city":"Minneapolis",
        "eventCollection":["7c0ff6ce-dd42-406a-a5f2-52b9d9325888","81ed2b4d-968a-4450-ba40-ccc88906c7e4","8d787049-b625-42b1-9cff-fc3d68ef357f"],
        "id":"62af945c-8f65-43e3-8e8a-9fa990f6fbb5",
        "state":"MN",
        "street":"null null",
        "zip":"55403"
    },{
        type : 'Location',
        "@uri":"http://localhost:8080/server/resources/location/4fd8c1fd-ef17-47c0-8e6c-d8104ad7608f/",
        "id":"4fd8c1fd-ef17-47c0-8e6c-d8104ad7608f",
        "street":"null null"
    },{
        type : 'Location',
        "@uri":"http://localhost:8080/server/resources/location/7217b236-6549-4fec-a9fc-c07c1695b029/",
        "city":"Minneapolis",
        "eventCollection":["7eae6ba2-173e-45e8-b6d1-da7b9ac60d07","70adfab2-96c5-4774-9757-4988ccef4b61","8824055a-3759-4ee6-ae23-82e4820bcbd0","b15dad27-4bdd-4367-8d04-4779f5760f2a","607cf66d-6031-42ed-a4ce-dff17d7e9155","79a4ea7c-c9ef-4105-9c09-5925979eaa05","1ba30cb5-5b3a-4e68-b047-2ff738e17290","0082e352-0486-478a-9a22-1d06ada225c5"],
        "id":"7217b236-6549-4fec-a9fc-c07c1695b029",
        "state":"MN",
        "street":"null null",
        "zip":"55404"
    },{
        type : 'Location',
        "@uri":"http://localhost:8080/server/resources/location/375356f6-e8a5-444d-b02e-f0e68b338b39/",
        "id":"375356f6-e8a5-444d-b02e-f0e68b338b39",
        "street":"null null"
    },{
        type : 'Location',
        "@uri":"http://localhost:8080/server/resources/location/8a2c898d-91a0-401f-9ab5-75147eeca6ce/",
        "city":"Coon Rapids",
        "eventCollection":["5faed825-dc6d-403d-81b4-b817385b1b83","cb57ee4b-d548-4c8a-a8b7-9d0d48fe711d"],
        "id":"8a2c898d-91a0-401f-9ab5-75147eeca6ce",
        "state":"MN",
        "street":"null null",
        "zip":"55433"
    },{
        type : 'Location',
        "@uri":"http://localhost:8080/server/resources/location/50fbe776-c1b0-4f4c-a6a8-5432b12f38bd/",
        "id":"50fbe776-c1b0-4f4c-a6a8-5432b12f38bd",
        "street":"null null"
    },{
        type : 'Location',
        "@uri":"http://localhost:8080/server/resources/location/2afa41f5-6aa2-4c3f-ae4b-3691a3fa2a8e/",
        "city":"Roseville",
        "eventCollection":["f86a0202-d999-494b-a258-391d3be608e9","95005b61-eb6a-44fc-b493-80c3473c1df5"],
        "id":"2afa41f5-6aa2-4c3f-ae4b-3691a3fa2a8e",
        "state":"MN",
        "street":"null null",
        "zip":"55113"
    },{
        type : 'Location',
        "@uri":"http://localhost:8080/server/resources/location/186c294e-c63d-4729-8aa1-f63c7636abbd/",
        "id":"186c294e-c63d-4729-8aa1-f63c7636abbd",
        "street":"null null"
    },{
        type : 'Location',
        "@uri":"http://localhost:8080/server/resources/location/d7d41b50-df9d-422c-8844-a5a42d230166/",
        "city":"Fridley",
        "eventCollection":"7ca1c7e6-dbcd-4c4d-a5c9-5d3bc2b3c86f",
        "id":"d7d41b50-df9d-422c-8844-a5a42d230166",
        "state":"MN",
        "street":"null null",
        "zip":"55432"
    }
    //
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
