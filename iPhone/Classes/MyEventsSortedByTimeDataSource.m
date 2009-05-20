//
//  MyEventsSortedByTimeDataSource.m
//  iPhone
//
//  Created by Ryan Schneider on 4/28/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "MyEventsSortedByTimeDataSource.h"
#import "iVolunteerData.h"


@implementation MyEventsSortedByTimeDataSource

- (id) init {
   [super init];
   self.eventsSortedByTime = [[iVolunteerData sharedVolunteerData] myEventsSortedIntoDays];
   self.dateNamesForEvents = [[iVolunteerData sharedVolunteerData] daysWithMyEvents];
   return self;
}

+ (id) dataSource 
{
   MyEventsSortedByTimeDataSource* ds = [[[MyEventsSortedByTimeDataSource alloc] init] autorelease ];
   return ds;
}

@end
