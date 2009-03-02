//
//  EventDetailsTableViewController.h
//  iPhone
//
//  Created by Ryan Schneider on 3/1/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Event.h"
#import "EventDetailsHeaderCell.h"
#import "ActionsView.h"

@interface EventDetailsTableViewController : UITableViewController {
   Event* event;
   EventDetailsHeaderCell* headerCell;
   ActionsView* headerActions;
   UITableViewCell* descriptionCell;
   CGSize descriptionSize;
   UIFont* smallFont;
   UIFont* mediumFont;
   UIFont* largeFont;
}

@property (retain) Event* event;
@property (retain) EventDetailsHeaderCell* headerCell;
@property (retain) ActionsView* headerActions;
@property (retain) UITableViewCell* descriptionCell;
@property CGSize descriptionSize;
@property (retain) UIFont* smallFont;
@property (retain) UIFont* mediumFont;
@property (retain) UIFont* largeFont;


+ (id) viewWithEvent: (Event*) event;

@end
