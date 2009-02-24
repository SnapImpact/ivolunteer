//
//  Location.m
//  iPhone
//
//  Created by Ryan Schneider on 2/10/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "Location.h"


@implementation Location

@dynamic uid;
@dynamic street;
@dynamic location;

+ (id) locationWithId: (NSString*) uid
               street: (NSString*) street
             latitude: (double) latitude
            longitude: (double) longitude
{
   Location* l = [Location alloc];
   return [[l initWithId: uid
                 street: street
               latitude: latitude
              longitude: longitude ] autorelease ];
}

- (id) initWithId: (NSString*) uid
           street: (NSString*) street
         latitude: (double) latitude
        longitude: (double) longitude
{
   self.uid = uid;
   self.street = street;
   self.location = [[CLLocation alloc] initWithLatitude: latitude 
                                              longitude: longitude ];
   return self;
}

- (void) dealloc {
   self.uid = nil;
   self.street = nil;
   self.location = nil;
   [super dealloc];
}



@end




