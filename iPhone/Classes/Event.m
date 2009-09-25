//
//  Event.m
//  iPhone
//
//  Created by Ryan Schneider on 2/10/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "Event.h"
#import "iVolunteerData.h"


@implementation Event

@synthesize originalId;
@synthesize details;

@synthesize organization;
@synthesize contact;
@synthesize date;
@synthesize duration;
@synthesize url;
@synthesize source;
@synthesize location;
@synthesize interestAreas;
@synthesize signedUp;

- (double) distanceFrom: (CLLocation*) origin {
    double originLat = origin.coordinate.latitude;
    double originLon = origin.coordinate.longitude;
    double myLat = self.location.location.coordinate.latitude;
    double myLon = self.location.location.coordinate.longitude;
    double x = 69.1 * (originLat - myLat);
    double y = 69.1 * (originLon - myLon) * cos(myLat/57.3);
    distance_ = sqrt(x*x + y*y);
    NSLog(@"Distance: %lf",distance_);   
    return distance_;
}

+ (id) eventWithId: (NSString*) uid
              name: (NSString*) name
           details: (NSString*) details
      organization: (Organization*) organization
           contact: (Contact*) contact
               url: (NSURL*) url
            source: (Source*) source
          location: (Location*) location
     interestAreas: (NSArray*) interestAreas
              date:  (NSDate*) date
          duration: (NSNumber*) duration
        originalId: (NSString*) originalId
{
   Event* e = [Event alloc];
   return [[e initWithId: uid
                    name: name
                 details: details
            organization: organization
                 contact: contact
                     url: url
                  source: source
                location: location
           interestAreas: interestAreas
                    date: date
                duration: duration 
              originalId: originalId] autorelease ];
}

- (id) initWithId: (NSString*) uid_
             name: (NSString*) name_
      details: (NSString*) details_
     organization: (Organization*) organization_
          contact: (Contact*) contact_
              url: (NSURL*) url_
           source: (Source*) source_
         location: (Location*) location_
    interestAreas: (NSArray*) interestAreas_
             date:  (NSDate*) date_
         duration: (NSNumber*) duration_
       originalId: (NSString*) originalId_
{
   [super initWithId: uid_ name: name_ ];
   self.details = details_;
   self.organization = organization_;
   self.contact = contact_;
    self.url = url_;
   self.source = source_;
   self.location = location_;
   self.interestAreas = interestAreas_;
   self.date = date_;
   self.duration = duration_;
   self.signedUp = [NSNumber numberWithBool: NO ];
   self.originalId = originalId_;
   return self;
}

- (NSNumber*) distance {
    return [NSNumber numberWithDouble: distance_];
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
   self.originalId = nil;
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
   ENCODE_PROP(originalId)
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
   DECODE_PROP(originalId)
   END_DECODER()
   return self;
}


@end

