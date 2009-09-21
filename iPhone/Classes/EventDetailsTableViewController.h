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
#import "BusyIndicatorDelegate.h"
#import "RegisterConfirmationViewController.h"

@interface EventDetailsTableViewController : UITableViewController<RegisterConfirmationProtocol, UIAlertViewDelegate> {
   id <BusyIndicatorDelegate> busyIndicatorDelegate;
   Event* event;
   EventDetailsHeaderCell* headerCell;
   ActionsView* headerActions;
   UIFont* smallFont;
   UIFont* mediumFont;
   UIFont* largeFont;
   NSString* signUpString;
   NSString* signedUpString;
   IBOutlet UIView* floatingView;
	int descriptionHeight;
}

@property (nonatomic, retain) UIView *floatingView;
@property (nonatomic,retain) id <BusyIndicatorDelegate> busyIndicatorDelegate;
@property (retain) Event* event;
@property (retain) EventDetailsHeaderCell* headerCell;
@property (retain) ActionsView* headerActions;
@property (retain) UIFont* smallFont;
@property (retain) UIFont* mediumFont;
@property (retain) UIFont* largeFont;
@property (copy) NSString* signedUpString;
@property (copy) NSString* signUpString;

- (void) didConfirmRegistration;
- (void) didCancelRegistration;

+ (id) viewWithEvent: (Event*) event;

@end

