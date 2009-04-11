//
//  ModelBase.h
//  iPhone
//
//  Created by Ryan Schneider on 2/26/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface ModelBase : NSObject<NSCoding> {
   NSString* uid;
   NSString* name;
}

@property (copy) NSString* uid;
@property (copy) NSString* name;

- (void)encodeWithCoder:(NSCoder *)encoder;

- (id) initWithCoder:(NSCoder *)decoder;
- (id) initWithId: (NSString*) uid_ name: (NSString*) name_;

- (void) dealloc;

@end

#define BEGIN_ENCODER() /* encoder start */ \
   [super encodeWithCoder: encoder ];

#define ENCODE_PROP(p) /*NSLog(@"Encoding: %@.%@", [self description], @ # p );*/ [encoder encodeObject: p forKey: @ # p ];

#define END_ENCODER() /* encoder end */

#define BEGIN_DECODER() /* decoder start */ \
   if (self = [super initWithCoder: decoder ] ) { \


#define DECODE_PROP(p) /*NSLog(@"Decoding: %@.%@", [self description], @ # p );*/ self.p = [decoder decodeObjectForKey: @ # p ];

#define END_DECODER() /* decoder end */ \
   }
