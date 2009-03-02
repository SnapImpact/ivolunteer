//
//  Source.m
//  iPhone
//
//  Created by Ryan Schneider on 2/22/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "Source.h"


@implementation Source

@synthesize url;

+ (id) sourceWithId: (NSString*) uid
               name: (NSString*) name
                url: (NSString*) url
{
   Source* s = [Source alloc];
   return [[s initWithId: uid
                   name: name
                    url: url] autorelease];
}

- (id) initWithId: (NSString*) uid_
             name: (NSString*) name_
              url: (NSString*) url_
{
   [super initWithId: uid_ name: name_ ];
   self.url = [NSURL URLWithString: url_ ];
   return self;
}

- (void) dealloc {
   self.url = nil;
   [super dealloc];
}

- (void)encodeWithCoder:(NSCoder *)encoder
{
   BEGIN_ENCODER()
   ENCODE_PROP(url)
   END_ENCODER()
}

- (id)initWithCoder:(NSCoder *)decoder
{
   BEGIN_DECODER()
   DECODE_PROP(url)
   END_DECODER()
   return self;
}


@end
