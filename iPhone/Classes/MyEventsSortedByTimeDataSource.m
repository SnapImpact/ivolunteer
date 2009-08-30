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

- (void)            tableView:(UITableView *)tableView 
           commitEditingStyle:(UITableViewCellEditingStyle)editingStyle 
            forRowAtIndexPath:(NSIndexPath *)indexPath
{
    //Delete here
    Event* event = (Event*)[self objectForIndexPath:indexPath];
    if(event) {
        event.signedUp = [NSNumber numberWithBool: NO];
        [[iVolunteerData sharedVolunteerData] updateMyEventsDataSource: event];
        [tableView deleteRowsAtIndexPaths: [NSArray arrayWithObject: indexPath] withRowAnimation: UITableViewScrollPositionBottom];
        //[tableView performSelector: @selector(reloadData) withObject: nil afterDelay: 0.5 ];
    }
}

- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath {
    return YES;
}

- (void) refresh {
    self.eventsSortedByTime = [[iVolunteerData sharedVolunteerData] myEventsSortedIntoDays];
    self.dateNamesForEvents = [[iVolunteerData sharedVolunteerData] daysWithMyEvents];
}

@end
