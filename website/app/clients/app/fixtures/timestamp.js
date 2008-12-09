// ==========================================================================
// App.Timestamp Fixtures
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
        type: 'Timestamp',
        "@uri":"http://localhost:8080/server/resources/timestamp/4d34ddf7-e158-4f7b-b653-92ebb0116c7e/",
        "eventCollection":"7c0ff6ce-dd42-406a-a5f2-52b9d9325888",
        "id":"4d34ddf7-e158-4f7b-b653-92ebb0116c7e",
        "timestamp":"2008-12-17T16:00:00-07:00"
    },{
        type: 'Timestamp',
        "@uri":"http://localhost:8080/server/resources/timestamp/8d4b8aa9-a813-4e6c-8e41-d3bbde1cf1b2/",
        "eventCollection":"b15dad27-4bdd-4367-8d04-4779f5760f2a",
        "id":"8d4b8aa9-a813-4e6c-8e41-d3bbde1cf1b2",
        "timestamp":"2008-12-12T14:00:00-07:00"
    },{
        type: 'Timestamp',
        "@uri":"http://localhost:8080/server/resources/timestamp/a3f01968-e8b4-488c-96a2-d3efaf927e36/",
        "eventCollection":"5faed825-dc6d-403d-81b4-b817385b1b83",
        "id":"a3f01968-e8b4-488c-96a2-d3efaf927e36",
        "timestamp":"2008-12-11T17:30:00-07:00"
    },{
        type: 'Timestamp',
        "@uri":"http://localhost:8080/server/resources/timestamp/beeeb4c8-a772-49a1-a061-d3c3099572ce/",
        "eventCollection":"f86a0202-d999-494b-a258-391d3be608e9",
        "id":"beeeb4c8-a772-49a1-a061-d3c3099572ce",
        "timestamp":"2008-12-03T05:00:00-07:00"
    },{
        type: 'Timestamp',
        "@uri":"http://localhost:8080/server/resources/timestamp/5376aa7d-4505-409d-8af6-8036512da59a/",
        "eventCollection":["3ea4c48d-8679-403f-944c-70ce37ebaf67","b86eca97-0873-4c05-97b9-bd029338c592","3969b43a-93bb-4c36-ad41-3aeb156b213f","f86a0202-d999-494b-a258-391d3be608e9"],
        "id":"5376aa7d-4505-409d-8af6-8036512da59a",
        "timestamp":"2008-12-03T08:00:00-07:00"
    },{
        type: 'Timestamp',
        "@uri":"http://localhost:8080/server/resources/timestamp/b07ef10d-019d-466b-ac2c-5c98bb9ff545/",
        "eventCollection":"f86a0202-d999-494b-a258-391d3be608e9",
        "id":"b07ef10d-019d-466b-ac2c-5c98bb9ff545",
        "timestamp":"2008-12-03T11:00:00-07:00"
    },{
        type: 'Timestamp',
        "@uri":"http://localhost:8080/server/resources/timestamp/807f801d-0a30-4396-b8d6-4aec2f94ce1f/",
        "eventCollection":["3ea4c48d-8679-403f-944c-70ce37ebaf67","f86a0202-d999-494b-a258-391d3be608e9"],
        "id":"807f801d-0a30-4396-b8d6-4aec2f94ce1f",
        "timestamp":"2008-12-03T14:00:00-07:00"
    },{
        type: 'Timestamp',
        "@uri":"http://localhost:8080/server/resources/timestamp/33c2dd19-0140-4a6a-bad8-a658bcd8fe78/",
        "eventCollection":"f86a0202-d999-494b-a258-391d3be608e9",
        "id":"33c2dd19-0140-4a6a-bad8-a658bcd8fe78",
        "timestamp":"2008-12-03T17:00:00-07:00"
    },{
        type: 'Timestamp',
        "@uri":"http://localhost:8080/server/resources/timestamp/3621ff38-38f6-4242-a1c1-c6dde536642f/",
        "eventCollection":"f86a0202-d999-494b-a258-391d3be608e9",
        "id":"3621ff38-38f6-4242-a1c1-c6dde536642f",
        "timestamp":"2008-12-04T05:00:00-07:00"
    },{
        type: 'Timestamp',
        "@uri":"http://localhost:8080/server/resources/timestamp/28ca96b1-35ce-4e5f-abdd-544a67c3ff6c/",
        "eventCollection":["3969b43a-93bb-4c36-ad41-3aeb156b213f","f86a0202-d999-494b-a258-391d3be608e9"],
        "id":"28ca96b1-35ce-4e5f-abdd-544a67c3ff6c",
        "timestamp":"2008-12-04T08:00:00-07:00"
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
