//
//  Organizations.m
//  iPhone
//
//  Created by Ryan Schneider on 2/10/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "Organization.h"


@implementation Organization

@dynamic uid;
@dynamic name;
@dynamic email;
@dynamic phone;
@dynamic url;

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

- (id) initWithId: (NSString*) uid 
             name: (NSString*) name 
            email: (NSString*) email 
            phone: (NSString*) phone
              url: (NSString*) url
{
   self.uid = uid;
   self.name = name;
   self.email = email;
   self.phone = phone;
   self.url = [NSURL URLWithString: url ];
   
   return self;
}

- (void) dealloc {
   self.uid = nil;
   self.name = nil;
   self.email = nil;
   self.phone = nil;
   self.url = nil;
   [super dealloc];
}


@end









