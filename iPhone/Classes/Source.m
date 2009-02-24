//
//  Source.m
//  iPhone
//
//  Created by Ryan Schneider on 2/22/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "Source.h"


@implementation Source

@dynamic uid;
@dynamic name;
@dynamic url;

+ (id) sourceWithId: (NSString*) uid
               name: (NSString*) name
                url: (NSString*) url
{
   Source* s = [Source alloc];
   return [[s initWithId: uid
                   name: name
                    url: url] autorelease];
}

- (id) initWithId: (NSString*) uid
             name: (NSString*) name
              url: (NSString*) url
{
   self.uid = uid;
   self.name = name;
   self.url = [NSURL URLWithString: url ];
   return self;
}

- (void) dealloc {
   self.uid = nil;
   self.name = nil;
   self.url = nil;
   [super dealloc];
}


@end
