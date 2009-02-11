//
//  iVolunteerData.h
//  iPhone
//
//  Created by Ryan Schneider on 2/10/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "Event.h"
#import "InterestArea.h"
#import "Location.h"
#import "Organization.h"
#import "Timestamp.h"
#import "OrganizationTypes.h"
#import "RMModelObject.h"

@interface iVolunteerData : RMModelObject {
}

@property (nonatomic, retain) NSMutableDictionary *events;
@property (nonatomic, retain) NSMutableDictionary *interestAreas;
@property (nonatomic, retain) NSMutableDictionary *locations;
@property (nonatomic, retain) NSMutableDictionary *organizations;
@property (nonatomic, retain) NSArray *sortedEvents;
@property (nonatomic, retain) NSArray *sortedLocations;
@property (nonatomic, retain) NSArray *sortedInterestAreas;
@property (nonatomic, retain) NSArray *sortedOrganizations;

@end








