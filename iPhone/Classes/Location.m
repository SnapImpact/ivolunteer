//
//  Location.m
//  iPhone
//
//  Created by Ryan Schneider on 2/10/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "Location.h"


@implementation Location

@synthesize uid;
@synthesize street;
@synthesize location;

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

- (id) initWithId: (NSString*) uid_
           street: (NSString*) street_
         latitude: (double) latitude_
        longitude: (double) longitude_
{
   [super initWithId: uid_ name: street_ ];
   self.location = [[CLLocation alloc] initWithLatitude: latitude_ 
                                              longitude: longitude_ ];
   return self;
}

- (NSString*) street
{
   return self.name;
}

- (void) setStreet: (NSString*) street_ 
{
   self.name = street_;
}

- (void) dealloc {
   self.location = nil;
   [super dealloc];
}

- (void)encodeWithCoder:(NSCoder *)encoder
{
   BEGIN_ENCODER()
   ENCODE_PROP(location)
   END_ENCODER()
}

- (id)initWithCoder:(NSCoder *)decoder
{
   BEGIN_DECODER()
   DECODE_PROP(location)
   END_DECODER()
   return self;
}



@end




