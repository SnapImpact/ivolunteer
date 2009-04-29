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

@implementation iVolunteerData

@synthesize organizations;
@synthesize contacts;
@synthesize sources;
@synthesize locations;
@synthesize interestAreas;
@synthesize events;

@synthesize version;

@synthesize eventsSortedIntoDays;  //nested array of events sorted into days
@synthesize daysWithEvents; //array of NSDates with upcoming events
@synthesize interestAreasByName;
@synthesize myLocation;
@synthesize homeZip;

static iVolunteerData* _sharedInstance = nil;
static NSString* kVolunteerDataRootKey = @"Root";
static NSString* kVolunteerDataVersion = @"v1.2";

+ (id) sharedVolunteerData {
   if( _sharedInstance == nil ) {
      _sharedInstance = [[iVolunteerData alloc] initWithTestData];
      return _sharedInstance;
   }
    
   return _sharedInstance;
}

+ (NSString*) archivePath {
   NSArray* paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
   return [[paths objectAtIndex: 0 ] stringByAppendingPathComponent: @"volunteer.data" ];
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
   self.eventsSortedIntoDays = nil;
   self.daysWithEvents = nil;
   
   return self;
}

- (id) initWithTestData 
{
   self = [self init];
   
   Organization* o;
   o = [ Organization organizationWithId: @"org1"
                                    name: @"ActionFeed"
                                   email: @"someone@actionfeed.org"
                                   phone: @"303-555-0001"
                                     url: @"http://actionfeed.org" ];
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
                        email: @"dave@actionfeed.org"
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
   
   [self sortData];
   return self;
}

- (NSArray*) eventsInDateSection: (unsigned int) section
{
   return [ self.eventsSortedIntoDays objectAtIndex: section ];
}

NSInteger _SortEventsByDate(id e1, id e2, void *context)
{
   return [[ e1 date ] compare: [e2 date]];
}

NSInteger _SortInterestAreasByName(id i1, id i2, void* context)
{
   return [[ i1 name] compare: [i2 name]];
}

- (void) sortData
{
   NSMutableArray* sortedInterestAreas = [NSMutableArray arrayWithCapacity: [self.interestAreas count ]];
   NSEnumerator *enumerator = [self.interestAreas objectEnumerator];
   id interestArea;
   while ((interestArea = [enumerator nextObject])) {
      [ sortedInterestAreas addObject: interestArea ];
   }
   
   self.interestAreasByName = [sortedInterestAreas sortedArrayUsingFunction: _SortInterestAreasByName context: self];
   
   //sort events by date
   NSMutableArray* eventsByDate = [NSMutableArray arrayWithCapacity: [self.events count]];
   enumerator = [self.events objectEnumerator ];
   id event;
   while((event = [enumerator nextObject])) {
      [eventsByDate addObject: event ];
   }
   
   [eventsByDate sortUsingFunction: _SortEventsByDate context: self ];
   
   NSMutableArray* dayNames = [NSMutableArray arrayWithCapacity: 1 ];
   NSMutableArray* _eventsSortedIntoDays = [NSMutableArray arrayWithCapacity: 1 ];
   enumerator = [eventsByDate objectEnumerator];
   NSDate* currentDate = nil;
   unsigned currentSubArray = 0;
   while(( event = [enumerator nextObject])) {
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
   
   self.daysWithEvents = dayNames;
   self.eventsSortedIntoDays = _eventsSortedIntoDays;

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
   self.eventsSortedIntoDays = nil;
   self.daysWithEvents = nil;
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
	
      ENCODE_PROP(eventsSortedIntoDays)
      ENCODE_PROP(daysWithEvents)
      ENCODE_PROP(interestAreasByName)
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
	
      DECODE_PROP(eventsSortedIntoDays)
      DECODE_PROP(daysWithEvents)
      DECODE_PROP(interestAreasByName)
   END_DECODER()
   
   if(self) {
      //cull any events which are older than today
      NSEnumerator *enumerator = [self.events objectEnumerator];
      NSMutableArray* eventsToCull = [NSMutableArray array];
      id event;
      while((event = [enumerator nextObject])) {
         if( [[DateUtilities today] compare: [event date]] == NSOrderedDescending ) {
            [eventsToCull addObject: [event uid] ];
         }
      }
      if( [eventsToCull count] ) {
         [ self.events removeObjectsForKeys: eventsToCull ];
      }
      [self sortData];      
   }
   
   return self;
}

- (void) parseJson: (NSData*) data
{
   NSString* utf8 = [NSString stringWithUTF8String: [data bytes]];
   NSData* utf32Data = [utf8 dataUsingEncoding: NSUTF32BigEndianStringEncoding ];
   NSError* error =  nil;
   NSDictionary *json = [[CJSONDeserializer deserializer] deserializeAsDictionary: utf32Data error: &error];
   
   //setup a temp autorelease pool here for performance
   NSAutoreleasePool *pool = [[NSAutoreleasePool alloc] init];
   
   @try {
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
                  o.url = [NSURL URLWithString: [[org objectForKey:@"url"] stringByTrimmingCharactersInSet: [NSCharacterSet whitespaceCharacterSet]]];
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
               NSString* event_id = [NSString stringWithFormat: @"event:%@-timestamp:%@", [event objectForKey: @"id"], ts];
               NSString* event_name = [event objectForKey: @"title" ];
               NSNumber* duration = [NSNumber numberWithInt: [[event objectForKey: @"duration"] intValue]];
               NSString* description = [event objectForKey:@"description"];
               
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
               
               Event* e = [self.events objectForKey: event_id];
               if(e != nil) {
                  //update
                  
               }
               else {
                  //add
                  e = [Event eventWithId:event_id
                                    name:event_name
                                 details:description
                            organization: [self.organizations objectForKey: org_id]
                                 contact: nil
                                  source: nil 
                                location: nil 
                           interestAreas:event_interestAreas
                                    date: [timestamps objectForKey: ts]
                                duration: duration ];
                  //don't add them for now until we hande nils in the UI
                  //[self.events setObject: e forKey: e.uid ];
               }            
            }
         }
      }
   }
   @finally {
      //close down our temp pool
      [pool release];
   }   
}
   
@end








