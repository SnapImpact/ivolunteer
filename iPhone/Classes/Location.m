//
//  Location.m
//  iPhone
//
//  Created by Ryan Schneider on 2/10/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "Location.h"


@implementation Location

@synthesize zip;
@synthesize state;
@synthesize address;
@synthesize city;
@synthesize location;

+ (id) locationWithId: (NSString*) uid
              address: (NSString*) address
                 city: (NSString*) city
                state: (NSString*) state
                  zip: (NSString*) zip
             latitude: (double) latitude
            longitude: (double) longitude
{
   Location* l = [Location alloc];
   return [[l initWithId: uid
                 address: address
                    city: city
                   state: state
                     zip: zip            
               latitude: latitude
              longitude: longitude ] autorelease ];
}

- (id) initWithId: (NSString*) uid_
          address: (NSString*) address_
             city: (NSString*) city_
            state: (NSString*) state_
              zip: (NSString*) zip_
         latitude: (double) latitude_
        longitude: (double) longitude_
{
    NSString* name_ = [NSString stringWithFormat: @"%@ %@,%@ %@", address_, city_, state_, zip_];
    [super initWithId: uid_ name: name_ ];
    self.address = address_;
    self.city = city_;
    self.state = state_;
    self.zip = zip_;
    self.location = [[CLLocation alloc] initWithLatitude: latitude_ 
                                               longitude: longitude_ ];
   return self;
}

- (void) dealloc {
   self.location = nil;
   [super dealloc];
}

- (void)encodeWithCoder:(NSCoder *)encoder
{
    BEGIN_ENCODER()
    ENCODE_PROP(location)
    ENCODE_PROP(address)
    ENCODE_PROP(city)
    ENCODE_PROP(state)
    ENCODE_PROP(zip)
    END_ENCODER()
}

- (id)initWithCoder:(NSCoder *)decoder
{
    BEGIN_DECODER()
    DECODE_PROP(location)
    DECODE_PROP(address)
    DECODE_PROP(city)
    DECODE_PROP(state)
    DECODE_PROP(zip)
    END_DECODER()
    return self;
}



@end








