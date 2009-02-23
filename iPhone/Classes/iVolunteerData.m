//
//  iVolunteerData.m
//  iPhone
//
//  Created by Ryan Schneider on 2/10/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "iVolunteerData.h"
#import "DateUtilities.h"

@implementation iVolunteerData

@dynamic organizations;
@dynamic contacts;
@dynamic sources;
@dynamic locations;
@dynamic interestAreas;
@dynamic events;

@dynamic eventsSortedIntoDays;  //nested array of events sorted into days
@dynamic daysWithEvents; //array of NSDates with upcoming events
@dynamic interestAreasByName;

static iVolunteerData* _sharedInstance = nil;

+ (id) sharedVolunteerData 
{
   if( _sharedInstance == nil ) {
      _sharedInstance = [[iVolunteerData alloc] initWithTestData];
      return _sharedInstance;
   }
    
   return _sharedInstance;
}

- (id) init 
{
   self = [iVolunteerData alloc];
   
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
              description: @"Come volunteer at our animal shelter.  You will help clean cages and other fun stuff."
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
              description: @"Help us feed the homeless."
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
              description: @"Help us clean up ths area."
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
      unsigned int uid = (i+4);
      e = [Event eventWithId: [NSString stringWithFormat: @"event%u", uid]
                        name: [NSString stringWithFormat: @"Random Event #%u", uid ]
                 description: @"Random Event description."
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
   NSMutableArray* eventsSortedIntoDays = [NSMutableArray arrayWithCapacity: 1 ];
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
         
         [eventsSortedIntoDays addObject: [NSMutableArray array]];
         [[eventsSortedIntoDays objectAtIndex: currentSubArray ] addObject: event ];
      }
      else {
         if(![DateUtilities isSameDay: currentDate date: [event date]]) {
            currentDate = [event date];
            [dayNames addObject: [DateUtilities formatShortDate: currentDate ]];
            [eventsSortedIntoDays addObject: [NSMutableArray array]];
            currentSubArray++;
         }
         [[eventsSortedIntoDays objectAtIndex: currentSubArray ] addObject: event ];
      }
   }
   
   self.daysWithEvents = dayNames;
   self.eventsSortedIntoDays = eventsSortedIntoDays;

}
                               
                               

                                           
@end








