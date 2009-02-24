//
//  Event.m
//  iPhone
//
//  Created by Ryan Schneider on 2/10/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "Event.h"


@implementation Event

@dynamic uid;
@dynamic name;
@dynamic description;

@dynamic organization;
@dynamic contact;
@dynamic date;
@dynamic duration;
@dynamic source;
@dynamic location;
@dynamic interestAreas;


+ (id) eventWithId: (NSString*) uid
              name: (NSString*) name
       description: (NSString*) description
      organization: (Organization*) organization
           contact: (Contact*) contact
            source: (Source*) source
          location: (Location*) location
     interestAreas: (NSArray*) interestAreas
              date:  (NSDate*) date
          duration: (NSNumber*) duration
{
   Event* e = [Event alloc];
   return [[e initWithId: uid
                   name: name
            description: description
           organization: organization
                contact: contact
                 source: source
               location: location
          interestAreas: interestAreas
                   date: date
               duration: duration ] autorelease ];
}

- (id) initWithId: (NSString*) uid
             name: (NSString*) name
      description: (NSString*) description
     organization: (Organization*) organization
          contact: (Contact*) contact
           source: (Source*) source
         location: (Location*) location
    interestAreas: (NSArray*) interestAreas
             date:  (NSDate*) date
         duration: (NSNumber*) duration
{
   self.uid = uid;
   self.name = name;
   self.description = description;
   self.organization = organization;
   self.contact = contact;
   self.source = source;
   self.location = location;
   self.interestAreas = interestAreas;
   self.date = date;
   self.duration = duration;
   return self;
}

- (void) dealloc {
   self.uid = nil;
   self.name = nil;
   self.description = nil;
   self.organization = nil;
   self.contact = nil;
   self.source = nil;
   self.location = nil;
   self.interestAreas = nil;
   self.date = nil;
   self.duration = nil;
   [super dealloc];
}

@end
