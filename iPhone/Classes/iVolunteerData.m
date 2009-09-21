//
//  iVolunteerData.m
//  iPhone
//
//  Created by Ryan Schneider on 2/10/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "iVolunteerData.h"
#import "DateUtilities.h"
#import "TouchJSON/JSON/CJSONDeserializer.h"
#import "iPhoneAppDelegate.h"

@implementation iVolunteerData

@synthesize reachable;
@synthesize organizations;
@synthesize contacts;
@synthesize sources;
@synthesize locations;
@synthesize interestAreas;
@synthesize events;

@synthesize version;

@synthesize upcomingEventsSortedIntoDays;  //nested array of events sorted into days
@synthesize myEventsSortedIntoDays;
@synthesize daysWithUpcomingEvents; //array of NSDates with upcoming events
@synthesize daysWithMyEvents; //array of NSDates with upcoming events
@synthesize interestAreasByName;
@synthesize myLocation;
@synthesize homeZip;

static iVolunteerData* _sharedInstance = nil;
static NSString* kVolunteerDataRootKey = @"Root";
static NSString* kVolunteerDataVersion = @"v1.8";

+ (id) sharedVolunteerData {
    if( _sharedInstance == nil ) {
        _sharedInstance = [[iVolunteerData alloc] initWithTestData];
        return _sharedInstance;
    }
    
    return _sharedInstance;
}

+ (NSString*) archivePath {
    NSArray* paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString* filename = [NSString stringWithFormat: @"ivolunteer.data.%@", kVolunteerDataVersion ]; 
    return [[paths objectAtIndex: 0 ] stringByAppendingPathComponent: filename ];
}

+ (BOOL) archive {
    NSMutableData* data = [NSMutableData data];
    NSKeyedArchiver* archiver = [[NSKeyedArchiver alloc] initForWritingWithMutableData: data];
    [archiver encodeObject: _sharedInstance forKey: kVolunteerDataRootKey ];
    [archiver finishEncoding];
    BOOL success = [data writeToFile: [iVolunteerData archivePath] atomically: YES];
    [archiver release];
    return success;
}

+ (BOOL) restore {
    NSData* data = [[NSData alloc] initWithContentsOfFile: [iVolunteerData archivePath]];
    NSKeyedUnarchiver* unarchiver = [[NSKeyedUnarchiver alloc] initForReadingWithData: data];
    
    iVolunteerData* restored = nil;
    
    @try {
        restored = [unarchiver decodeObjectForKey: kVolunteerDataRootKey ];
    }
    @catch (NSException* exception) {
        restored = nil;
        //delete the file
        NSLog( @"Exception decoding persisted data, deleting data store: %@", exception );
        NSError* err;
        if(![[NSFileManager defaultManager] removeItemAtPath: [iVolunteerData archivePath] error: &err]) {
            NSLog( @"Error deleting data store: %@ ", err );
        }
    }
    [unarchiver finishDecoding];
    [unarchiver release];
    [data release];
    
    if((restored != nil) && ([restored.version isEqual: kVolunteerDataVersion])) {
        if(_sharedInstance != nil) {
            [_sharedInstance release];
            _sharedInstance = nil;
        }
        _sharedInstance = [restored retain];
        
        for(Event* event in [restored.events objectEnumerator]) {
            if([event.signedUp boolValue]) {
                [restored updateMyEventsDataSource: event];
            }
        }
        return YES;
    }
    else {
        return NO;
    }
}

- (id) init 
{
    self = [iVolunteerData alloc];
    self.version = kVolunteerDataVersion;
    
    self.organizations = [NSMutableDictionary dictionary];
    self.contacts = [NSMutableDictionary dictionary];
    self.sources = [NSMutableDictionary dictionary];
    self.locations = [NSMutableDictionary dictionary];
    self.interestAreas = [NSMutableDictionary dictionary];
    self.events = [NSMutableDictionary dictionary];
    
    self.interestAreasByName = nil;
    self.upcomingEventsSortedIntoDays = nil;
    self.myEventsSortedIntoDays = nil;
    self.daysWithUpcomingEvents = nil;
    
    return self;
}

- (id) initWithTestData 
{
    self = [self init];
    
    /*
     Organization* o;
     o = [ Organization organizationWithId: @"org1"
     name: @"ActionFeed"
     email: @"someone@snapimpact.org"
     phone: @"303-555-0001"
     url: @"http://snapimpact.org" ];
     [self.organizations setObject: o forKey: o.uid];
     
     o = [ Organization organizationWithId: @"org2"
     name: @"Another Organization"
     email: @"someone@another.org"
     phone: @"303-555-0002"
     url: @"http://another.org" ];
     [self.organizations setObject: o forKey: o.uid ];
     
     
     o = [ Organization organizationWithId: @"org3"
     name: @"Organization #3"
     email: @"someone@number3.org"
     phone: @"303-555-0003"
     url: @"http://number3.org" ];
     [self.organizations setObject: o forKey: o.uid ];
     
     NSLog( @"Organization dictionary has %u elements. ", [self.organizations count] );
     
     Contact* c;
     c = [Contact contactWithId: @"contact1"
     name: @"Ryan Schneider"
     email: @"ryanleeschneider@gmail.com"
     phone: @"303-926-5498" ];
     [self.contacts setObject: c forKey: c.uid ];
     
     c = [Contact contactWithId: @"contact2"
     name: @"Dave Angulo"
     email: @"dave@snapimpact.org"
     phone: @"303-555-2000" ];
     [self.contacts setObject: c forKey: c.uid ];
     
     Source* s;
     s = [Source sourceWithId: @"source1"
     name: @"One Volunteer Source"
     url: @"http://onevol.org/" ];
     [self.sources setObject: s forKey: s.uid ];
     
     s = [Source sourceWithId: @"source2"
     name: @"Another Source"
     url: @"http://another-source.org/" ];
     [self.sources setObject: s forKey: s.uid ];
     
     
     Location* l;
     l = [Location locationWithId: @"loc1"
     street: @"719 Walnut St. Boulder, CO 80301"
     latitude: 40.015521 
     longitude: -105.285833 ];
     [self.locations setObject: l forKey: l.uid ];
     
     l = [Location locationWithId: @"loc2"
     street: @"4001 Discovery Dr. Boulder, CO 80303"
     latitude: 40.007378
     longitude: -105.243448 ];
     [self.locations setObject: l forKey: l.uid ];
     
     InterestArea* ia;
     ia = [InterestArea interestAreaWithId: @"area1"
     name: @"Environment" ];
     [self.interestAreas setObject: ia forKey: ia.uid ];
     
     ia = [InterestArea interestAreaWithId: @"area2"
     name: @"Social Work" ];
     [self.interestAreas setObject: ia forKey: ia.uid ];
     
     ia = [InterestArea interestAreaWithId: @"area3"
     name: @"Animals" ];
     [self.interestAreas setObject: ia forKey: ia.uid ];
     
     Event* e;
     e = [Event eventWithId: @"event1"
     name: @"Animal Shelter Help"
     details: @"Come volunteer at our animal shelter.  You will help clean cages and other fun stuff."
     organization: [self.organizations objectForKey: @"org3" ]
     contact: [self.contacts objectForKey: @"contact1" ]
     source: [self.sources objectForKey: @"source1" ]
     location: [self.locations objectForKey: @"loc2" ]
     interestAreas: [NSArray arrayWithObjects: [self.interestAreas objectForKey: @"area3" ], nil ]
     date: [NSDate dateWithTimeIntervalSinceNow: 3600 ]
     duration: [NSNumber numberWithInt: 3600 ]
     ];
     [self.events setObject: e forKey: e.uid ];
     
     e = [Event eventWithId: @"event2"
     name: @"Volunteer Soup Kitchen"
     details: @"Help us feed the homeless."
     organization: [self.organizations objectForKey: @"org2" ]
     contact: [self.contacts objectForKey: @"contact2" ]
     source: [self.sources objectForKey: @"source2" ]
     location: [self.locations objectForKey: @"loc1" ]
     interestAreas: [NSArray arrayWithObjects: [self.interestAreas objectForKey: @"area2" ], nil ]
     date: [NSDate dateWithTimeIntervalSinceNow: 7200 ]
     duration: [NSNumber numberWithInt: (3600 * 4) ]
     ];
     [self.events setObject: e forKey: e.uid ];
     
     e = [Event eventWithId: @"event3"
     name: @"Wildlife Area Maintenance"
     details: @"Help us clean up ths area."
     organization: [self.organizations objectForKey: @"org1" ]
     contact: [self.contacts objectForKey: @"contact1" ]
     source: [self.sources objectForKey: @"source1" ]
     location: [self.locations objectForKey: @"loc1" ]
     interestAreas: [NSArray arrayWithObjects: [self.interestAreas objectForKey: @"area3" ], [self.interestAreas objectForKey: @"area1" ], nil ]
     date: [NSDate dateWithTimeIntervalSinceNow: 3600 * 27 ]
     duration: [NSNumber numberWithInt: 3600 * 10 ]
     ];
     [self.events setObject: e forKey: e.uid ];
     
     srand(0);
     for(unsigned int i = 0; i < 100; i++ ) {
     unsigned int event_id = (i+4);
     e = [Event eventWithId: [NSString stringWithFormat: @"event%u", event_id]
     name: [NSString stringWithFormat: @"Random Event #%u", event_id ]
     details: @"Random Event details."
     organization: [self.organizations objectForKey: [NSString stringWithFormat: @"org%d", (rand() % [self.organizations count])+1 ]]
     contact: [self.contacts objectForKey: [NSString stringWithFormat: @"contact%d", (rand() % [self.contacts count])+1 ]]
     source: [self.sources objectForKey: [NSString stringWithFormat: @"source%d", (rand() % [self.sources count])+1 ]]
     location: [self.locations objectForKey: [NSString stringWithFormat: @"loc%d", (rand() % [self.locations count])+1 ]]
     interestAreas: [NSArray arrayWithObjects: [self.interestAreas objectForKey: @"area3" ], [self.interestAreas objectForKey: @"area1" ], nil ]
     date: [NSDate dateWithTimeIntervalSinceNow: (3600 * (rand()%24) * i) ]
     duration: [NSNumber numberWithInt: 3600 * 4 ]
     ];
     [self.events setObject: e forKey: e.uid ];
     
     }
     */
    
    [self sortData];
    return self;
}

NSInteger _SortEventsByDate(id e1, id e2, void *context)
{
    return [[ e1 date ] compare: [e2 date]];
}

NSInteger _SortInterestAreasByName(id i1, id i2, void* context)
{
    return [[ i1 name] compare: [i2 name]];
}

- (NSString*) dateToString: (NSDate*) date {
    if( [DateUtilities isToday: date])
        return [NSString stringWithString: @"Today"];
    else
        return [NSString stringWithString: [DateUtilities formatShortDate: date]];
}

- (void) filterEvents {
    //cull any events which are older than today
    for (Event* event in [self.events allValues]) {
        NSDate* date = event.date;
        if( [[DateUtilities today] compare: date] == NSOrderedDescending ) {
            [self.events removeObjectForKey: event.uid ];
        }
        //Get rid of some way in the future too, for performance
        else if ([date timeIntervalSinceNow] > (3600*24*60)) {
            if(![event.signedUp boolValue]) {
                [self.events removeObjectForKey: event.uid ];
            }
        }
    }
    
    //remove any events not matched by our interest area filter
    NSArray* filteredInterestAreas = [InterestArea loadInterestAreasFromPreferences];
    if(!filteredInterestAreas || ![filteredInterestAreas count]) {
        return;
    }
    
    for(Event* event in [self.events allValues]) {
        BOOL include = NO;
        if(event.interestAreas == nil || [event.interestAreas count] == 0) {
            NSLog(@"Including event (%@,%@) because it does not have Interest Areas", event.uid, event.name);
            continue;
        }
        for(InterestArea* i in filteredInterestAreas) {
            if(event.interestAreas) {
                if([event.interestAreas containsObject: i]){
                    include = YES;
                    //NSLog(@"Event (%@,%@) DOES have interest (%@,%@)", event.uid, event.name, i.uid, i.name);
                    break;
                }
                else {
                    //NSLog(@"Event (%@,%@) does NOT have interest (%@,%@)", event.uid, event.name, i.uid, i.name);
                }
            }
        }
        if(!include) {
            NSLog(@"Removing Event (%@,%@) w/ interest(s) (%@) because it doesn't match your interest areas.", event.uid, event.name, event.interestAreas);
            [self.events removeObjectForKey: event.uid];
        }
    }
}

- (void) sortData
{
    NSMutableArray* sortedInterestAreas = [NSMutableArray arrayWithCapacity: [self.interestAreas count ]];
    NSEnumerator *enumerator = [self.interestAreas objectEnumerator];
    id interestArea;
    while ((interestArea = [enumerator nextObject])) {
        [ sortedInterestAreas addObject: interestArea ];
    }
    
    NSLog(@"iVolunteerData:sortData: using myLocation = %@", self.myLocation);
    NSMutableArray *toDelete = [NSMutableArray array];
    for(Event* event in [self.events objectEnumerator]) {
        [event distanceFrom: self.myLocation];
        if((![event.signedUp boolValue]) && [event.distance doubleValue] > 100.0) {
            [toDelete addObject: event.uid];
        }
    }
    if([toDelete count]) {
        [self.events removeObjectsForKeys: toDelete];
    }
    
    // IMPORTANT: this needs to be able to return all the interest areas possible in order for [InterestArea loadInterestAreasFromPreferences] to work correctly
    self.interestAreasByName = (NSMutableArray*)[sortedInterestAreas sortedArrayUsingFunction: _SortInterestAreasByName context: self];
    
    //sort events by date
    NSMutableArray* eventsByDate = [NSMutableArray arrayWithArray: [self.events allValues]];
    [eventsByDate sortUsingFunction: _SortEventsByDate context: self ];
    
    NSMutableArray* dayNames = [NSMutableArray arrayWithCapacity: 1 ];
    NSMutableArray* _eventsSortedIntoDays = [NSMutableArray arrayWithCapacity: 1 ];
    NSDate* currentDate = nil;
    unsigned currentSubArray = 0;
    
    for(Event* event in eventsByDate) {
        if(currentDate == nil) {
            currentDate = [event date];
            if( [DateUtilities isToday: currentDate])
                [ dayNames addObject: @"Today" ];
            else
                [ dayNames addObject: [DateUtilities formatShortDate: currentDate]];
            
            [_eventsSortedIntoDays addObject: [NSMutableArray array]];
            [[_eventsSortedIntoDays objectAtIndex: currentSubArray ] addObject: event ];
        }
        else {
            if(![DateUtilities isSameDay: currentDate date: [event date]]) {
                currentDate = [event date];
                [dayNames addObject: [DateUtilities formatShortDate: currentDate ]];
                [_eventsSortedIntoDays addObject: [NSMutableArray array]];
                currentSubArray++;
            }
            [[_eventsSortedIntoDays objectAtIndex: currentSubArray ] addObject: event ];
        }
    }
    
    self.daysWithUpcomingEvents = dayNames;
    self.upcomingEventsSortedIntoDays = _eventsSortedIntoDays;
}

- (void) updateMyEventsDataSource: (Event*) eventToAdd {
    if (!self.myEventsSortedIntoDays) {
        self.myEventsSortedIntoDays = [NSMutableArray array];
    }
    
    if(eventToAdd && [eventToAdd.signedUp boolValue]) {
        NSString* date = [self dateToString: eventToAdd.date];
        int i = 0;
        for( NSString* existingDay in self.daysWithMyEvents ) {
            if ([existingDay isEqualToString: date]) {
                [[self.myEventsSortedIntoDays objectAtIndex: i] addObject: eventToAdd];
                [[self.myEventsSortedIntoDays objectAtIndex: i] sortUsingFunction: _SortEventsByDate context: self ];
                return;
            }
            i++;
        }
        
        if (!self.daysWithMyEvents) {
            self.daysWithMyEvents = [NSMutableArray array];
        }
        [self.daysWithMyEvents addObject: date];
        NSUInteger index = [self.daysWithMyEvents count]-1;
        [self.myEventsSortedIntoDays addObject: [NSMutableArray array]];
        [[self.myEventsSortedIntoDays objectAtIndex: index] addObject: eventToAdd];
        [[self.myEventsSortedIntoDays objectAtIndex: index] sortUsingFunction: _SortEventsByDate context: self ];
    }
    else if( eventToAdd && ![eventToAdd.signedUp boolValue]) {
        //remove the event
        unsigned int i = 0;
        for(NSMutableArray* dayEvents in self.myEventsSortedIntoDays) {
            [dayEvents removeObject: eventToAdd];
            if([dayEvents count] == 0) {
                //[self.daysWithMyEvents removeObjectAtIndex: i];
            }
            i++;
        }
    }
}

- (void) dealloc {
    self.organizations = nil;
    self.contacts = nil;
    self.sources = nil;
    self.locations = nil;
    self.interestAreas = nil;
    self.events = nil;
	self.myLocation = nil;
	self.homeZip = nil;
    self.upcomingEventsSortedIntoDays = nil;
    self.myEventsSortedIntoDays = nil;
    self.daysWithUpcomingEvents = nil;
    self.interestAreasByName = nil;
    [super dealloc];
}

- (void)encodeWithCoder:(NSCoder *)encoder
{
    BEGIN_ENCODER()
    ENCODE_PROP(version)
    ENCODE_PROP(organizations)
    ENCODE_PROP(contacts)
    ENCODE_PROP(sources)
    ENCODE_PROP(locations)
    ENCODE_PROP(interestAreas)
    ENCODE_PROP(events)
    ENCODE_PROP(homeZip)
    ENCODE_PROP(myLocation)
    //ENCODE_PROP(upcomingEventsSortedIntoDays)
    //ENCODE_PROP(myEventsSortedIntoDays)
    //ENCODE_PROP(daysWithUpcomingEvents)
    //ENCODE_PROP(daysWithMyEvents)
    //ENCODE_PROP(interestAreasByName)
    END_ENCODER()
}

- (id)initWithCoder:(NSCoder *)decoder
{
    BEGIN_DECODER()
    DECODE_PROP(version)
    DECODE_PROP(organizations)
    DECODE_PROP(contacts)
    DECODE_PROP(sources)
    DECODE_PROP(locations)
    DECODE_PROP(interestAreas)
    DECODE_PROP(events)
    DECODE_PROP(homeZip)
    DECODE_PROP(myLocation)
    NSLog(@"iVolunteerData:initWithCoder: restoring myLocation = %@", self.myLocation);
    //DECODE_PROP(upcomingEventsSortedIntoDays)
    //DECODE_PROP(myEventsSortedIntoDays)
    //DECODE_PROP(daysWithUpcomingEvents)
    //DECODE_PROP(daysWithMyEvents)
    //DECODE_PROP(interestAreasByName)
    END_DECODER()
    
    if(self) {
        [self filterEvents];  
    }
    
    return self;
}

- (void) parseConsolidatedJson: (NSData*) data
{
    /*
     self.organizations = [NSMutableDictionary dictionary];
     self.contacts = [NSMutableDictionary dictionary];
     self.sources = [NSMutableDictionary dictionary];
     self.locations = [NSMutableDictionary dictionary];
     self.interestAreas = [NSMutableDictionary dictionary];
     self.events = [NSMutableDictionary dictionary];
     */
    
    NSString* utf8 = [NSString stringWithUTF8String: [data bytes]];
    NSData* utf32Data = [utf8 dataUsingEncoding: NSUTF32BigEndianStringEncoding ];
    NSError* error =  nil;
    NSDictionary *json = [[CJSONDeserializer deserializer] deserializeAsDictionary: utf32Data error: &error];
    
    NSLog(@"Consolidated: %@", json);
    
    //setup a temp autorelease pool here for performance
    NSAutoreleasePool *pool = [[NSAutoreleasePool alloc] init];
    
    @try {
        //See if we got a good lat/lon from the feed
        NSString* feedLatitude = [json objectForKey: @"latitude"];
        NSString* feedLongitude = [json objectForKey: @"longitude"];        
        if(feedLatitude && feedLongitude && [feedLatitude length] && [feedLongitude length]) {
            self.myLocation = [[CLLocation alloc] initWithLatitude: [feedLatitude doubleValue]
                                                         longitude: [feedLongitude doubleValue]];
            NSLog(@"iVolunteerData:parseConsolidatedJson: set myLocation to: %@", self.myLocation);
        }
        
        //First let's do organizations
        NSArray* orgArray = [ json objectForKey: @"organizations"];
        if( orgArray != nil ) {
            NSEnumerator* e = [orgArray objectEnumerator];
            NSDictionary* org;
            while((org = (NSDictionary*)[e nextObject])) {
                if(org != nil) {
                    //find it in the current list of organizations
                    //or insert it
                    Organization* o = [ self.organizations objectForKey: [org objectForKey: @"id"]];
                    if( o !=  nil ) {
                        //update the properties
                        o.uid = [[org objectForKey: @"id" ] stringByTrimmingCharactersInSet: [NSCharacterSet whitespaceCharacterSet]];
                        o.name = [[org objectForKey: @"name"] stringByTrimmingCharactersInSet: [NSCharacterSet whitespaceCharacterSet]];
                        o.email = [[org objectForKey:@"email"] stringByTrimmingCharactersInSet: [NSCharacterSet whitespaceCharacterSet]];
                        o.phone = [[org objectForKey:@"phone"] stringByTrimmingCharactersInSet: [NSCharacterSet whitespaceCharacterSet]];
                        NSString* url = [org objectForKey: @"url"];
                        if(url) {
                            o.url = [NSURL URLWithString: [url stringByTrimmingCharactersInSet: [NSCharacterSet whitespaceCharacterSet]]];
                        }
                        else {
                            o.url = nil;
                        }
                    }
                    else {
                        //add it
                        o = [Organization organizationWithId: [[org objectForKey: @"id" ] stringByTrimmingCharactersInSet: [NSCharacterSet whitespaceCharacterSet]]
                                                        name: [[org objectForKey: @"name"] stringByTrimmingCharactersInSet: [NSCharacterSet whitespaceCharacterSet]]
                                                       email: [[org objectForKey: @"email"] stringByTrimmingCharactersInSet: [NSCharacterSet whitespaceCharacterSet]]
                                                       phone: [[org objectForKey: @"phone" ] stringByTrimmingCharactersInSet: [NSCharacterSet whitespaceCharacterSet]]
                                                         url: [[org objectForKey: @"url"] stringByTrimmingCharactersInSet: [NSCharacterSet whitespaceCharacterSet]]];
                        
                        [self.organizations setObject: o forKey: o.uid ];
                    }
                }
            }
        }
        
        //now let's get the timestamps into a useable format
        NSArray* timestampsArray = [json objectForKey: @"timestamps" ];
        NSMutableDictionary* timestamps = [NSMutableDictionary dictionaryWithCapacity: (timestampsArray != nil) ? [timestampsArray count] : 0];
        if( timestampsArray != nil ) {
            NSEnumerator* e = [timestampsArray objectEnumerator];
            NSDictionary* timestamp;
            while((timestamp = (NSDictionary*) [e nextObject])) {
                NSDate* date = [DateUtilities parseIS08601Date: [timestamp objectForKey: @"timestamp"]];
                [ timestamps setObject: date
                                forKey: [timestamp objectForKey: @"id"]];
            }
        }
        
        //source
		id sourcesTemp = [json objectForKey:@"sources"];
		if([sourcesTemp isKindOfClass: [NSDictionary class]]) {
			//single source
			NSDictionary* source = sourcesTemp;
			NSString* sourceId  = [source objectForKey: @"id"];
			NSString* sourceName = [source objectForKey: @"name"];
			NSString* sourceUrl = [source objectForKey: @"url"];
			
			Source* s = [self.sources objectForKey: sourceId];
			if (s != nil) {
				//update the properties
				s.name = [sourceName stringByTrimmingCharactersInSet: [NSCharacterSet whitespaceCharacterSet]];
				s.url = [NSURL URLWithString: [sourceUrl stringByTrimmingCharactersInSet: [NSCharacterSet whitespaceCharacterSet]]];
			}
			else {
                s = [Source sourceWithId: sourceId
                                    name: sourceName
                                     url: sourceUrl ];
                
                [self.sources setObject: s forKey: s.uid ];
			}
		}
		else {
			//multiple sources
			NSArray* sourcesArray = [json objectForKey:@"sources"];
			if (sourcesArray != nil ) {
				NSEnumerator* e2 = [sourcesArray objectEnumerator];
				NSDictionary* source;
				while((source = (NSDictionary*)[e2 nextObject])) {
					if (source != nil) {
						//find it in the current list of sources
						//or insert it
						Source* s = [ self.sources objectForKey:@"id" ];
						if (s != nil) {
							//update the properties
							s.name = [[source objectForKey: @"name"] stringByTrimmingCharactersInSet: [NSCharacterSet whitespaceCharacterSet]];
							NSString* url = [source objectForKey: @"url"];
							s.url = [NSURL URLWithString: [url stringByTrimmingCharactersInSet: [NSCharacterSet whitespaceCharacterSet]]];
						}
						else {
							//add it
							NSString* sourceId  = [source objectForKey: @"id"];
							NSString* sourceName = [source objectForKey: @"name"];
							NSString* sourceUrl = [source objectForKey: @"url"];
							s = [Source sourceWithId: sourceId
												name: sourceName
												 url: sourceUrl ];
							
							[self.sources setObject: s forKey: s.uid ];
						}
					}
				}
			}
		}
        
        //location
        NSArray* locationsArray = [json objectForKey: @"locations"];
        if(locationsArray != nil ) {
            for (NSDictionary* location in locationsArray) {
                NSString* address = [location objectForKey: @"address"];
                NSString* city = [location objectForKey: @"city"];
                NSString* state = [location objectForKey: @"state"];
                NSString* zip = [location objectForKey: @"zip"];
                NSString* latString = [location objectForKey: @"latitude"];
                double lat = 0.0;
                if(latString) {
                    lat = [latString doubleValue];
                }
                NSString* lonString = [location objectForKey: @"longitude"];
                double lon = 0.0;
                if(lonString) {
                    lon = [lonString doubleValue];
                }
                NSString* location_id = [location objectForKey: @"id"];
                Location * location = [Location locationWithId: location_id 
                                                       address: address
                                                          city: city
                                                         state: state
                                                           zip: zip
                                                      latitude: lat 
                                                     longitude: lon];
                [self.locations setObject: location forKey: location_id];
            }
        }
        
        //And finally, the events
        NSArray* eventsArray = [json objectForKey: @"events" ];
        if(eventsArray != nil ) {
            NSEnumerator* e = [eventsArray objectEnumerator];
            NSDictionary* event;
            while((event = (NSDictionary*)[e nextObject])) {
                id tsC = [event objectForKey: @"timestampCollection"];
                NSArray* timestampCollection;
                if( [tsC isKindOfClass: [NSArray class]] )
                    timestampCollection = tsC;
                else {
                    timestampCollection = [NSArray arrayWithObject: tsC ];
                }
                
                NSEnumerator* ts_e = [timestampCollection objectEnumerator];
                NSString* ts;
                while(( ts = (NSString*)[ts_e nextObject] )) {
                    NSString* original_id = [event objectForKey: @"id"];
                    NSString* event_id = [NSString stringWithFormat: @"event:%@-timestamp:%@", original_id, ts];
                    NSString* event_name = [event objectForKey: @"title" ];
                    NSNumber* duration = [NSNumber numberWithInt: [[event objectForKey: @"duration"] intValue]];
                    NSString* description = [event objectForKey:@"description"];
                    NSString* temp_url = [event objectForKey:@"url"];
                    NSURL* url = [NSURL URLWithString: temp_url];
                    
                    NSDate* timestamp = [timestamps objectForKey: ts];
                    if([timestamp timeIntervalSinceNow] > (3600*24*30)) {
                        //More than 30 days from now, skip it
                        NSLog(@"Skipping event in the future at %@", timestamp);
                        continue;
                    }
                    
                    NSString* org_id;
                    id orgCollection = [event objectForKey: @"organizationCollection"];
                    if( [orgCollection isKindOfClass: [NSArray class]] ) {
                        //TODO: handle multiple orgs here
                        //for now just use the first one
                        org_id = [orgCollection objectAtIndex: 0 ];
                    }
                    else {
                        org_id = orgCollection;
                    }
                    
                    NSMutableArray* event_interestAreas = [NSMutableArray array];
                    id interestAreaCollection = [event objectForKey: @"interestAreaCollection"];
                    if( [interestAreaCollection isKindOfClass: [NSArray class]] ) {
                        NSEnumerator* iaCe = [interestAreaCollection objectEnumerator];
                        id ia_id;
                        while((ia_id = [iaCe nextObject])) {
                            InterestArea* ia = [self.interestAreas objectForKey: ia_id];
                            if(ia != nil) 
                                [event_interestAreas addObject: ia];
                        }
                    }
                    else {
                        id ia_id = interestAreaCollection;
                        InterestArea* ia = [self.interestAreas objectForKey:ia_id];
                        if(ia !=  nil)
                            [event_interestAreas addObject:ia];
                    }
                    
                    //contacts
                    //So, the feed doesn't have a discrete contact object
                    //So the logic is:
                    // if event has prop("phone")
                    //  contact.phone = event.phone
                    // else
                    //  contact.phone = organization.phone
                    // else
                    //  contact.phone = nil
                    
                    NSString *contactName, *contactPhone, *contactEmail;
                    contactName = [event objectForKey: @"name"];
                    if(!contactName) {
                        contactName = [[self.organizations objectForKey: org_id] name];
                    }
                    contactPhone = [event objectForKey: @"phone"];
                    if(!contactPhone) {
                        contactPhone = [[self.organizations objectForKey: org_id] phone];
                    }
                    contactEmail = [event objectForKey: @"email"];
                    if(!contactEmail) {
                        contactEmail = [[self.organizations objectForKey: org_id] email];
                    }
                    NSString* contactUid = [ NSString stringWithFormat:@"name=%@:phone=%@:email=%@", contactName, contactPhone, contactEmail];
                    Contact* contact = [Contact contactWithId: contactUid
                                                         name: contactName
                                                        email: contactEmail
                                                        phone: contactPhone ];
                    NSAssert(contact, @"Contact is nil");
                    
                    id locationCollection = [event objectForKey: @"locationCollection" ];
                    NSString *location_id;
                    if ([locationCollection isKindOfClass: [NSArray class]]) {
                        location_id = [locationCollection objectAtIndex: 0];
                    }
                    else {
                        location_id = locationCollection;
                    }
                    Location* location = [self.locations objectForKey: location_id];
					
					NSString*source_id = [event objectForKey: @"sourceId"];
					Source* source = [self.sources objectForKey: source_id ];
					
                    Event* e = [self.events objectForKey: event_id];
                    if(e != nil) {
                        //update
                        NSLog(@"Already exists.");
                        if([e.signedUp boolValue]) {
                            NSLog(@"And we're signed up!");
                        }
                    }
                    else {
                        //add
                        e = [Event eventWithId: event_id
                                          name: event_name
                                       details: description
                                  organization: [self.organizations objectForKey: org_id]
                                       contact: contact
                                           url: url
                                        source: source 
                                      location: location 
                                 interestAreas: event_interestAreas
                                          date: [timestamps objectForKey: ts]
                                      duration: duration
                                    originalId: original_id];
                        [self.events setObject: e forKey: e.uid ];
                    }            
                }
            }
        }
        
        [self filterEvents];
        [self sortData];
    }
    @finally {
        //close down our temp pool
        [pool release];
    }   
}

- (void) parseFilterDataJson: (NSData*) data
{
    NSString* utf8 = [NSString stringWithUTF8String: [data bytes]];
    NSData* utf32Data = [utf8 dataUsingEncoding: NSUTF32BigEndianStringEncoding ];
    NSError* error =  nil;
    NSDictionary *json = [[CJSONDeserializer deserializer] deserializeAsDictionary: utf32Data error: &error];
    
    //setup a temp autorelease pool here for performance
    NSAutoreleasePool *pool = [[NSAutoreleasePool alloc] init];
    
    @try {
        //NSLog(@"%@", json);
        NSArray* interestAreasArray = [ json objectForKey: @"interestAreas"];
        for(NSDictionary* interestAreasDict in interestAreasArray) {
            NSString* interestUid = [interestAreasDict objectForKey: @"id"];
            NSString* interestName = [interestAreasDict objectForKey: @"name"];
            InterestArea* interestArea = [InterestArea interestAreaWithId: interestUid name: interestName];
            [self.interestAreas setObject: interestArea forKey: interestUid];
        }
    }
    @finally {
        //close down our temp pool
        [pool release];
    }   
}

- (BOOL) registerForEventOnBackend: (Event*) event
                          withName: (NSString*) name_
                          andEmail: (NSString*) email_
{
    NSString* encodedName = [ name_ stringByAddingPercentEscapesUsingEncoding: NSASCIIStringEncoding];
    NSString* encodedEmail = [ email_ stringByAddingPercentEscapesUsingEncoding: NSASCIIStringEncoding];
    NSString* encodedEventId = [ event.originalId stringByAddingPercentEscapesUsingEncoding: NSASCIIStringEncoding];
    NSString* urlStr = [ NSString stringWithFormat: @"http://snapimpact.org/server/resources/attendEvent?event=%@&name=%@&email=%@",
                        encodedEventId,
                        encodedName,
                        encodedEmail
                        ];
    NSURL* url = [ NSURL URLWithString: urlStr ];
    NSURLRequest* request = [NSURLRequest requestWithURL: url];
    NSURLResponse* response = nil;
    NSError* error = nil;
    NSData* result = [NSURLConnection sendSynchronousRequest: request
                                           returningResponse: &response
                                                       error: &error];
    
    if(error) {
        NSLog(@"%d", [error code]);
        return NO;
    }
    if(response && [response isKindOfClass: [NSHTTPURLResponse class]]) {
        NSInteger statusCode = [(NSHTTPURLResponse*)response statusCode];
        NSLog(@"Response code: %d", statusCode);
        if (statusCode != 200) {
            return NO;
        }
    }
        
    NSLog([NSString stringWithUTF8String: [result bytes]]);
    return YES;
}



@end










