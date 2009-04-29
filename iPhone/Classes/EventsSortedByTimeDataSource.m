//
//  EventsSortedByTimeDataSource.m
//  iPhone
//
//  Created by Ryan Schneider on 2/15/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "EventsSortedByTimeDataSource.h"
#import "iVolunteerData.h"

#import "EventTableCell.h"

@implementation EventsSortedByTimeDataSource

+ (id) dataSource 
{
   EventsSortedByTimeDataSource* ds = [[[EventsSortedByTimeDataSource alloc] init] autorelease ];
   return ds;
}

#pragma mark RootViewDataSourceProtocol
// these properties are used by the view controller
// for the navigation and tab bar
- (NSString*) name {
   return @"Upcoming";
}

- (NSString*) navigationBarName {
   return @"Upcoming Events";
}

- (UIImage* )tabBarImage {
   return nil;
}

// this property determines the style of table view displayed
- (UITableViewStyle) tableViewStyle {
   return UITableViewStyleGrouped;
}

// provides a standardized means of asking for the element at the specific
// index path, regardless of the sorting or display technique for the specific
// datasource
- (NSObject *)objectForIndexPath:(NSIndexPath *)indexPath {
   NSArray* events = [[[iVolunteerData sharedVolunteerData] eventsSortedIntoDays] objectAtIndex: [indexPath section]]; 
   Event* event = [events objectAtIndex: [indexPath row]];
   return event;
}

- (enum NavigationLevel) navigationLevel {
   return NAV_DETAIL;
}

#pragma mark UITableViewDataSource protocol

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
   EventTableCell* cell = (EventTableCell*) [tableView dequeueReusableCellWithIdentifier: [EventTableCell reuseIdentifier]];
   if(cell == nil) {
      NSArray *nib = [[NSBundle mainBundle] loadNibNamed: @"EventTableCell" owner: self options: nil ];
      NSEnumerator* e = [nib objectEnumerator];
      id objectInNib;
      while(objectInNib = [e nextObject]) {
         if( [objectInNib isKindOfClass: [EventTableCell class]] ) {
            cell = (EventTableCell*)objectInNib;
         }
      }
   }
   
   NSArray* events = [[[iVolunteerData sharedVolunteerData] eventsSortedIntoDays] objectAtIndex: [indexPath section]]; 
   Event* event = [events objectAtIndex: [indexPath row]];
   
   NSLog( @"Class of cell is %@", [cell class] );
   cell.event = event;
   cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
   return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
   return [ EventTableCell height];
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
	return [[[iVolunteerData sharedVolunteerData] daysWithEvents] count ];
}

- (NSInteger)tableView:(UITableView *)tableView sectionForSectionIndexTitle:(NSString *)title atIndex:(NSInteger)index {
	return index;
}


- (NSInteger)tableView:(UITableView *)tableView  numberOfRowsInSection:(NSInteger)section {
	//return the number of elements in the given section
   //something like:
   NSArray* arr = [[iVolunteerData sharedVolunteerData] eventsInDateSection: section];
   return [arr count];
}


- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section {
   return [[[iVolunteerData sharedVolunteerData] daysWithEvents] objectAtIndex: section ];
}

@end
