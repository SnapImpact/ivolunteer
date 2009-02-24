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
   return nil;
}

- (enum NavigationLevel) navigationLevel {
   return NAV_DETAIL;
}

#pragma mark UITableViewDataSource protocol

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
   /*
	AtomicElementTableViewCell *cell = (AtomicElementTableViewCell *)[tableView dequeueReusableCellWithIdentifier:@"AtomicElementTableViewCell"];
	if (cell == nil) {
		cell = [[[AtomicElementTableViewCell alloc] initWithFrame:CGRectZero reuseIdentifier:@"AtomicElementTableViewCell"] autorelease];
	}
   
	// configure cell contents
	// all the rows should show the disclosure indicator
	if ([self showDisclosureIcon])
		cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
	
	// set the element for this cell as specified by the datasource. The atomicElementForIndexPath: is declared
	// as part of the ElementsDataSource Protocol and will return the appropriate element for the index row
	cell.element = [self atomicElementForIndexPath:indexPath];
   */
   
   static NSString* CellIdentifier = @"EventTableViewCell";
   
   EventTableCell* cell = (EventTableCell*) [tableView dequeueReusableCellWithIdentifier: CellIdentifier];
   if(cell == nil) {
      NSArray *nib = [[NSBundle mainBundle] loadNibNamed: @"EventTableCell" owner: self options: nil ];
      cell = [nib objectAtIndex:0];
   }
   
   NSArray* events = [[[iVolunteerData sharedVolunteerData] eventsSortedIntoDays] objectAtIndex: [indexPath section]]; 
   Event* event = [events objectAtIndex: [indexPath row]];
   
   cell.event = event;
   cell.accessoryType = UITableViewCellAccessoryDetailDisclosureButton;
   return cell;
}

/*
- (NSArray *)sectionIndexTitlesForTableView:(UITableView *)tableView {
	// returns the array of section titles. 
   // Something like:
   //  Today
   //  This Week
   //  This Month
   //  Further Out
   return [[iVolunteerData sharedVolunteerData] daysWithEvents];
   //return [ NSArray arrayWithObjects: @"Today", @"This Week", @"This Month", @"Further Out", nil ];
}
*/

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
