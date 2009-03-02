//
//  InterestArea.h
//  iPhone
//
//  Created by Ryan Schneider on 2/10/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ModelBase.h"

@interface InterestArea : ModelBase {
}

+ (id) interestAreaWithId: (NSString*) uid
                     name: (NSString*) name;

- (id) initWithId: (NSString*) uid
             name: (NSString*) name;

@end
