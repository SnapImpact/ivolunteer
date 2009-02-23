//
//  Contact.m
//  iPhone
//
//  Created by Ryan Schneider on 2/22/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "Contact.h"


@implementation Contact

@dynamic uid;
@dynamic name;
@dynamic email;
@dynamic phone;

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

- (id) initWithId: (NSString*) uid
             name: (NSString*) name
            email: (NSString*) email
            phone: (NSString*) phone
{
   self.uid = uid;
   self.name = name;
   self.email = email;
   self.phone = phone;
   return self;
}

@end
