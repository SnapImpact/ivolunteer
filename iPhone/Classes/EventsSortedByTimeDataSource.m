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

@synthesize dateNamesForEvents;
@synthesize eventsSortedByTime;

#pragma mark RootViewDataSourceProtocol
- (BOOL) isEmpty {
    return ([self.eventsSortedByTime count] == 0);
}

// this property determines the style of table view displayed
- (UITableViewStyle) tableViewStyle {
    return UITableViewStyleGrouped;
}

// provides a standardized means of asking for the element at the specific
// index path, regardless of the sorting or display technique for the specific
// datasource
- (NSObject *)objectForIndexPath:(NSIndexPath *)indexPath {
    if([self isEmpty]) {
        return nil;
    }
    NSArray* events = [self.eventsSortedByTime objectAtIndex: [indexPath section]]; 
    Event* event = [events objectAtIndex: [indexPath row]];
    return event;
}

- (enum NavigationLevel) navigationLevel {
    return NAV_DETAIL;
}

#pragma mark UITableViewDataSource protocol

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    if([self isEmpty]) {
        static UITableViewCell* _emptyCell = nil;
        if(!_emptyCell) {
            _emptyCell = [[UITableViewCell alloc] initWithFrame: CGRectZero reuseIdentifier: @"EmptyEventCell" ];
            _emptyCell.text = @"No Events.";
        }
        
        return _emptyCell;
    }
    
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
    
    NSArray* events = [self.eventsSortedByTime objectAtIndex: [indexPath section]]; 
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
	NSUInteger count = [self.dateNamesForEvents count ];
    if(!count)
        return 1;
    return count;
}

- (NSInteger)tableView:(UITableView *)tableView sectionForSectionIndexTitle:(NSString *)title atIndex:(NSInteger)index {
	return index;
}


- (NSInteger)tableView:(UITableView *)tableView  numberOfRowsInSection:(NSInteger)section {
	//return the number of elements in the given section
    if([self isEmpty]) {
        return 1;
    }
    else {
        NSArray* arr = [self.eventsSortedByTime objectAtIndex: section];
        return [arr count];
    }
}


- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section {
    if([self isEmpty]) {
        return @"";
    }
    if (section > [self.dateNamesForEvents count]) {
        return nil;
    }
    return [self.dateNamesForEvents objectAtIndex: section ];
}

+ (id) dataSource {
    return nil;
}

@end


