//
//  Timestamp.m
//  iPhone
//
//  Created by Ryan Schneider on 2/10/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "Timestamp.h"


@implementation Timestamp

@dynamic uid;
@dynamic date;

- (void) dealloc {
   self.uid = nil;
   self.date = nil;
   [super dealloc];
}


@end



