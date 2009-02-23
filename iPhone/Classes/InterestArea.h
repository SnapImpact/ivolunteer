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

@property (copy) NSString* uid;
@property (copy) NSString* name;

+ (id) interestAreaWithId: (NSString*) uid
                     name: (NSString*) name;

- (id) initWithId: (NSString*) uid
             name: (NSString*) name;

@end
