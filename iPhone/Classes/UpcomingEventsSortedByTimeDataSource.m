//
//  UpcomingEventsSortByTimeDataSource.m
//  iPhone
//
//  Created by Ryan Schneider on 4/28/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "UpcomingEventsSortedByTimeDataSource.h"
#import "iVolunteerData.h"

@implementation UpcomingEventsSortedByTimeDataSource

- (id) init {
   [super init];
   self.eventsSortedByTime = [[iVolunteerData sharedVolunteerData] upcomingEventsSortedIntoDays];
   self.dateNamesForEvents = [[iVolunteerData sharedVolunteerData] daysWithUpcomingEvents];
   return self;
}

+ (id) dataSource 
{
   UpcomingEventsSortedByTimeDataSource* ds = [[[UpcomingEventsSortedByTimeDataSource alloc] init] autorelease ];
   return ds;
}

@end
