//
//  InterestArea.m
//  iPhone
//
//  Created by Ryan Schneider on 2/10/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "InterestArea.h"


@implementation InterestArea

+ (id) interestAreaWithId: (NSString*) uid
                     name: (NSString*) name
{
   InterestArea* i = [InterestArea alloc];
   return [[ i initWithId: uid
                    name: name ] autorelease ];
}

- (id) initWithId: (NSString*) uid_
             name: (NSString*) name_
{
   [super initWithId: uid_ name: name_ ];
   return self;
}

- (void) dealloc {
   [super dealloc];
}

- (void)encodeWithCoder:(NSCoder *)encoder
{
   BEGIN_ENCODER()
   END_ENCODER()
}

- (id)initWithCoder:(NSCoder *)decoder
{
   BEGIN_DECODER()
   END_DECODER()
   return self;
}


@end
