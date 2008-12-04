// ==========================================================================
// App.Timeframe Fixtures
// ==========================================================================

require('core') ;

App.FIXTURES = App.FIXTURES.concat([

    {
        type: 'Timeframe',
        "@uri":"http://localhost:8080/server/resources/location/0EBCA9B6-A050-4441-ACDD-D6703A9B1A6D/",
        "bucket":"0",
        "id":"0EBCA9B6-A050-4441-ACDD-D6703A9B1A6D",
        "name":"All"
    },{
        type: 'Timeframe',
        "@uri":"http://localhost:8080/server/resources/location/9534A02F-21B5-4FFE-AD60-F998BE6DED95/",
        "bucket":"604800000",
        "id":"9534A02F-21B5-4FFE-AD60-F998BE6DED95",
        "name":"1 Week"
    },{
        type: 'Timeframe',
        "@uri":"http://localhost:8080/server/resources/location/87DC7D7B-CCC7-4EC2-B56F-80EAA3776255/",
        "bucket":"1209600000",
        "id":"87DC7D7B-CCC7-4EC2-B56F-80EAA3776255",
        "name":"2 Weeks"
    },{
        type: 'Timeframe',
        "@uri":"http://localhost:8080/server/resources/location/D94883FA-92F7-43A6-B72A-115985583E7C/",
        "bucket":"2419200000",
        "id":"D94883FA-92F7-43A6-B72A-115985583E7C",
        "name":"1 Month"
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
