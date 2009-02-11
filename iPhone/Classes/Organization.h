//
//  Organizations.h
//  iPhone
//
//  Created by Ryan Schneider on 2/10/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "RMModelObject.h"

@interface Organization : RMModelObject {
}

@property (readonly, retain) NSString *uid;
@property (readonly, retain) NSString *name;
@property (readonly, retain) NSString *email;
@property (readonly, retain) NSString *phone;
@property (readonly, retain) NSURL *url;
@property (readonly, retain) NSMutableDictionary *organizationTypes;
@property (readonly, retain) NSMutableDictionary *events;
@property (readonly, retain) NSMutableDictionary *interestAreas;
@property (readonly, retain) NSMutableDictionary *locations;

@end









