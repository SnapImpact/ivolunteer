//
//  Location.h
//  iPhone
//
//  Created by Ryan Schneider on 2/10/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreLocation/CoreLocation.h>
#import "ModelBase.h"

@interface Location : ModelBase {
    CLLocation* location;
    NSString* address;
    NSString* city;
    NSString* state;
    NSString* zip;
}

@property (nonatomic, retain) NSString *zip;
@property (nonatomic, retain) NSString *state;
@property (nonatomic, retain) NSString *address;
@property (nonatomic, retain) NSString *city;
@property (retain) CLLocation* location;

+ (id) locationWithId: (NSString*) uid
               address: (NSString*) address
                 city: (NSString*) city
                state: (NSString*) state
                  zip: (NSString*) zip
             latitude: (double) latitude
            longitude: (double) longitude;

- (id) initWithId: (NSString*) uid
          address: (NSString*) address
             city: (NSString*) city
            state: (NSString*) state
              zip: (NSString*) zip
         latitude: (double) latitude
        longitude: (double) longitude;


@end








