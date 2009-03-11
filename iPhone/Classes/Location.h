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
}

@property (retain) CLLocation* location;
@property (retain) NSString* street;

+ (id) locationWithId: (NSString*) uid
               street: (NSString*) street
             latitude: (double) latitude
            longitude: (double) longitude;

- (id) initWithId: (NSString*) uid
           street: (NSString*) street
         latitude: (double) latitude
        longitude: (double) longitude;


@end




