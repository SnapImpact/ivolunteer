//
//  Event.h
//  iPhone
//
//  Created by Ryan Schneider on 2/10/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ModelBase.h"
#import "Organization.h"
#import "Contact.h"
#import "Source.h"
#import "Location.h"
#import "InterestArea.h"

@interface Event : ModelBase {
   NSString* details;
   Organization* organization;
   Contact* contact;
   NSDate* date;
   NSNumber* duration;
	NSURL* url;
   Source* source;
   Location* location;
   NSArray* interestAreas;
   NSNumber* signedUp;
   double distance_; 
   NSString* originalId;
}

@property (copy) NSString* details;
@property (retain) Organization* organization;
@property (retain) Contact* contact;
@property (retain) NSDate* date;
@property (retain) NSNumber* duration;
@property (retain) NSURL *url;
@property (retain) Source* source;
@property (retain) Location* location;
@property (retain) NSArray* interestAreas;
@property (retain) NSNumber* signedUp;
@property (readonly) NSNumber* distance;
@property (retain) NSString* originalId;

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
        originalId: (NSString*) originalId;

- (id) initWithId: (NSString*) uid
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
       originalId: (NSString*) originalid;

- (double) distanceFrom: (CLLocation*) origin ;

@end

