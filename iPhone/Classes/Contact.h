//
//  Contact.h
//  iPhone
//
//  Created by Ryan Schneider on 2/22/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ModelBase.h"

@interface Contact : ModelBase {
   NSString* email;
   NSString* phone;   
}

@property (copy) NSString* email;
@property (copy) NSString* phone;

+ (id) contactWithId: (NSString*) uid
                name: (NSString*) name
               email: (NSString*) email
               phone: (NSString*) phone;

- (id) initWithId: (NSString*) uid
             name: (NSString*) name
            email: (NSString*) email
            phone: (NSString*) phone;

@end
