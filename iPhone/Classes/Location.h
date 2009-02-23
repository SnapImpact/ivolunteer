//
//  Location.h
//  iPhone
//
//  Created by Ryan Schneider on 2/10/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreLocation/CoreLocation.h>
#import "RMModelObject.h"

@interface Location : RMModelObject {
}

@property (copy) NSString *uid;
@property (copy) NSString *street;
@property (retain) CLLocation* location;

+ (id) locationWithId: (NSString*) uid
               street: (NSString*) street
             latitude: (double) latitude
            longitude: (double) longitude;

- (id) initWithId: (NSString*) uid
           street: (NSString*) street
         latitude: (double) latitude
        longitude: (double) longitude;


@end




