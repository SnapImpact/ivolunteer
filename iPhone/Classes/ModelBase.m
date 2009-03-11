//
//  ModelBase.m
//  iPhone
//
//  Created by Ryan Schneider on 2/26/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "ModelBase.h"


@implementation ModelBase

@synthesize uid;
@synthesize name;

- (void)encodeWithCoder:(NSCoder *)encoder
{
   ENCODE_PROP(uid);
   ENCODE_PROP(name);
}

- (id)initWithCoder:(NSCoder *)decoder
{
   if (self = [super init]) {
      DECODE_PROP(uid)
      DECODE_PROP(name)
   }
   
   return self;
}

- (id)initWithId: (NSString*) uid_ name: (NSString*) name_
{
   self.uid = uid_;
   self.name = name_;
   return self;
}

- (void) dealloc
{
   self.uid = nil;
   self.name = nil;
   [super dealloc];
}

@end
