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
}

@property (retain) Event* event;
@property (retain) EventDetailsHeaderCell* headerCell;
@property (retain) ActionsView* headerActions;

+ (id) viewWithEvent: (Event*) event;

@end
