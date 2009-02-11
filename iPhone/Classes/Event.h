//
//  Event.h
//  iPhone
//
//  Created by Ryan Schneider on 2/10/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "RMModelObject.h"

@interface Event : RMModelObject {
}

@property (readonly, retain) NSString* uid;
@property (readonly, retain) NSString* title;
@property (readonly, retain) NSString* description;
@property (readonly, retain) NSNumber* duration;

@property (readonly, retain) NSMutableDictionary* interestAreas;
@property (readonly, retain) NSMutableDictionary* timestamps;
@property (readonly, retain) NSMutableDictionary* locations;
@property (readonly, retain) NSMutableDictionary* organizations;

@end
