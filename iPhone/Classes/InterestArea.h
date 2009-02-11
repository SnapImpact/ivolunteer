//
//  InterestArea.h
//  iPhone
//
//  Created by Ryan Schneider on 2/10/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "RMModelObject.h"

@interface InterestArea : RMModelObject {
}

@property (readonly, retain) NSString* uid;
@property (readonly, retain) NSString* name;

@property (readonly, retain) NSMutableDictionary* events;
@property (readonly, retain) NSMutableDictionary* organizations;

@end
