//
//  Organizations.h
//  iPhone
//
//  Created by Ryan Schneider on 2/10/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "RMModelObject.h"

@interface Organization : RMModelObject {
}

@property (copy) NSString *uid;
@property (copy) NSString *name;
@property (copy) NSString *email;
@property (copy) NSString *phone;
@property (retain) NSURL *url;

+ (id) organizationWithId: (NSString*) uid 
                     name: (NSString*) name 
                    email: (NSString*) email 
                    phone: (NSString*) phone
                      url: (NSString*) url;

- (id) initWithId: (NSString*) uid 
             name: (NSString*) name 
            email: (NSString*) email 
            phone: (NSString*) phone
              url: (NSString*) url;

@end









