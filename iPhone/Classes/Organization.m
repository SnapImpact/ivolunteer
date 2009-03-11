//
//  Organizations.m
//  iPhone
//
//  Created by Ryan Schneider on 2/10/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "Organization.h"


@implementation Organization

@synthesize email;
@synthesize phone;
@synthesize url;

+ (id) organizationWithId: (NSString*) uid 
                     name: (NSString*) name 
                    email: (NSString*) email 
                    phone: (NSString*) phone
                      url: (NSString*) url
{
   Organization* org = [Organization alloc];
   return [[org initWithId: uid 
                     name: name 
                    email: email 
                    phone: phone 
                      url: url] autorelease ];
}

- (id) initWithId: (NSString*) uid_ 
             name: (NSString*) name_ 
            email: (NSString*) email_ 
            phone: (NSString*) phone_
              url: (NSString*) url_
{
   [super initWithId: uid_ name: name_ ];
   self.email = email_;
   self.phone = phone_;
   self.url = [NSURL URLWithString: url_ ];
   
   return self;
}

- (void) dealloc {
   self.email = nil;
   self.phone = nil;
   self.url = nil;
   [super dealloc];
}

- (void)encodeWithCoder:(NSCoder *)encoder
{
   BEGIN_ENCODER()
   ENCODE_PROP(email)
   ENCODE_PROP(phone)
   ENCODE_PROP(url)
   END_ENCODER()
}

- (id)initWithCoder:(NSCoder *)decoder
{
   BEGIN_DECODER()
   DECODE_PROP(email)
   DECODE_PROP(phone)
   DECODE_PROP(url)
   END_DECODER()
   return self;
}

@end









