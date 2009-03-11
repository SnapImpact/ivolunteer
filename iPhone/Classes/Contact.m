//
//  Contact.m
//  iPhone
//
//  Created by Ryan Schneider on 2/22/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "Contact.h"


@implementation Contact

@synthesize email;
@synthesize phone;

+ (id) contactWithId: (NSString*) uid
                name: (NSString*) name
               email: (NSString*) email
               phone: (NSString*) phone
{
   Contact* c = [Contact alloc];
   return [[c initWithId: uid
                   name: name
                  email: email
                  phone: phone ] autorelease];
}

- (id) initWithId: (NSString*) uid_
             name: (NSString*) name_
            email: (NSString*) email_
            phone: (NSString*) phone_
{
   [super initWithId: uid_ name: name_ ];
   self.email = email_;
   self.phone = phone_;
   return self;
}

- (void) dealloc {
   self.email = nil;
   self.phone = nil;
   [super dealloc];
}

- (void)encodeWithCoder:(NSCoder *)encoder
{
   BEGIN_ENCODER()
   ENCODE_PROP(email)
   ENCODE_PROP(phone)
   END_ENCODER()
}

- (id)initWithCoder:(NSCoder *)decoder
{
   BEGIN_DECODER()
   DECODE_PROP(email)
   DECODE_PROP(phone)
   END_DECODER()
   return self;
}

@end
