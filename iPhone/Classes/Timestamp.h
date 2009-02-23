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

@property (copy) NSString *uid;
@property (retain) NSDate *date;

@end