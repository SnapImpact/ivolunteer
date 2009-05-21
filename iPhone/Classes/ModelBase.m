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

- (NSString *)description
{
	return [NSString stringWithFormat:@"uid=%@ name=%@", self.uid, self.name];
}

- (BOOL)isEqual:(id)anObject
{
	if ([anObject isKindOfClass:[ModelBase class]])
	{
		ModelBase * aModelObject = (ModelBase *)anObject;
		return [self.uid isEqualToString:[aModelObject uid]] && [self.name isEqualToString:[aModelObject name]];
	}
	else
	{
		return [super isEqual:anObject];
	}
}

- (void) dealloc
{
   self.uid = nil;
   self.name = nil;
   [super dealloc];
}

@end
