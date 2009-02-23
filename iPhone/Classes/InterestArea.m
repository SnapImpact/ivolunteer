//
//  InterestArea.m
//  iPhone
//
//  Created by Ryan Schneider on 2/10/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "InterestArea.h"


@implementation InterestArea

@dynamic uid;
@dynamic name;

+ (id) interestAreaWithId: (NSString*) uid
                     name: (NSString*) name
{
   InterestArea* i = [InterestArea alloc];
   return [[ i initWithId: uid
                    name: name ] autorelease ];
}

- (id) initWithId: (NSString*) uid
             name: (NSString*) name
{
   self.uid = uid;
   self.name = name;
   return self;
}

@end
