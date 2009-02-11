//
//  Timestamp.h
//  iPhone
//
//  Created by Ryan Schneider on 2/10/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "RMModelObject.h"

@interface Timestamp : RMModelObject {
}

@property (readonly, retain) NSString *uid;
@property (readonly, retain) NSDate *date;
@property (readonly, retain) NSMutableDictionary *events;

@end