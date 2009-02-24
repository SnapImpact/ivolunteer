//
//  iVolunteerData.h
//  iPhone
//
//  Created by Ryan Schneider on 2/10/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "Event.h"

@interface iVolunteerData : RMModelObject {
}

@property (retain) NSMutableDictionary *organizations;
@property (retain) NSMutableDictionary *contacts;
@property (retain) NSMutableDictionary *sources;
@property (retain) NSMutableDictionary *locations;
@property (retain) NSMutableDictionary *interestAreas;
@property (retain) NSMutableDictionary *events;

@property (retain) NSArray* eventsSortedIntoDays;  //nested array of events sorted into days
@property (retain) NSArray* daysWithEvents; //array of strings of dates with upcoming events
@property (retain) NSArray* interestAreasByName; //array of InterestAreas by name
@property (copy)   NSString* version;

+ (id) sharedVolunteerData;
+ (BOOL) archive;
+ (BOOL) restore;
+ (NSString*) archivePath;

- (id) init;
- (id) initWithTestData; //make a bogus test object

- (NSArray*) eventsInDateSection: (unsigned int) section;
- (void) sortData;

@end








