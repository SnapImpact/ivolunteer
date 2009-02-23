//
//  Source.h
//  iPhone
//
//  Created by Ryan Schneider on 2/22/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "RMModelObject.h"

@interface Source : RMModelObject {

}

@property (copy) NSString* uid;
@property (copy) NSString* name;
@property (retain) NSURL* url;

+ (id) sourceWithId: (NSString*) uid
               name: (NSString*) name
                url: (NSString*) url;

- (id) initWithId: (NSString*) uid
             name: (NSString*) name
              url: (NSString*) url;

@end
