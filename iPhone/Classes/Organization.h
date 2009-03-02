//
//  Organizations.h
//  iPhone
//
//  Created by Ryan Schneider on 2/10/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ModelBase.h"

@interface Organization : ModelBase {
   NSString *email;
   NSString *phone;
   NSURL *url;
   
}

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









