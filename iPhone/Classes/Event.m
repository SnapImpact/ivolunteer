//
//  Event.m
//  iPhone
//
//  Created by Ryan Schneider on 2/10/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "Event.h"


@implementation Event

@synthesize details;

@synthesize organization;
@synthesize contact;
@synthesize date;
@synthesize duration;
@synthesize source;
@synthesize location;
@synthesize interestAreas;
@synthesize signedUp;


+ (id) eventWithId: (NSString*) uid
              name: (NSString*) name
           details: (NSString*) details
      organization: (Organization*) organization
           contact: (Contact*) contact
            source: (Source*) source
          location: (Location*) location
     interestAreas: (NSArray*) interestAreas
              date:  (NSDate*) date
          duration: (NSNumber*) duration
{
   Event* e = [Event alloc];
   return [[e initWithId: uid
                    name: name
                 details: details
            organization: organization
                 contact: contact
                  source: source
                location: location
           interestAreas: interestAreas
                    date: date
                duration: duration ] autorelease ];
}

- (id) initWithId: (NSString*) uid_
             name: (NSString*) name_
      details: (NSString*) details_
     organization: (Organization*) organization_
          contact: (Contact*) contact_
           source: (Source*) source_
         location: (Location*) location_
    interestAreas: (NSArray*) interestAreas_
             date:  (NSDate*) date_
         duration: (NSNumber*) duration_
{
   [super initWithId: uid_ name: name_ ];
   self.details = details_;
   self.organization = organization_;
   self.contact = contact_;
   self.source = source_;
   self.location = location_;
   self.interestAreas = interestAreas_;
   self.date = date_;
   self.duration = duration_;
   self.signedUp = [NSNumber numberWithBool: NO ];
   return self;
}

- (void) dealloc {
   self.details = nil;
   self.organization = nil;
   self.contact = nil;
   self.source = nil;
   self.location = nil;
   self.interestAreas = nil;
   self.date = nil;
   self.duration = nil;
   [super dealloc];
}

- (void)encodeWithCoder:(NSCoder *)encoder
{
   BEGIN_ENCODER()
   ENCODE_PROP(details)
   ENCODE_PROP(organization)
   ENCODE_PROP(contact)
   ENCODE_PROP(date)
   ENCODE_PROP(duration)
   ENCODE_PROP(source)
   ENCODE_PROP(location)
   ENCODE_PROP(interestAreas)
   ENCODE_PROP(signedUp);
   END_ENCODER()
}

- (id)initWithCoder:(NSCoder *)decoder
{
   BEGIN_DECODER()
   DECODE_PROP(details)
   DECODE_PROP(organization)
   DECODE_PROP(contact)
   DECODE_PROP(date)
   DECODE_PROP(duration)
   DECODE_PROP(source)
   DECODE_PROP(location)
   DECODE_PROP(interestAreas)   
   DECODE_PROP(signedUp);
   END_DECODER()
   return self;
}


@end
