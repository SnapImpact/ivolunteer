//
//  RootViewController.h
//  iPhone
//
//  Created by Dave Angulo on 10/18/08.
//  Copyright __MyCompanyName__ 2008. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "EventDetailsTableViewController.h"
#import "EventsSortedByTimeDataSource.h"
#import "BusyIndicatorDelegate.h"
#import "LocationAvailabilityDelegate.h"

@interface ProjectViewController : UITableViewController <LocationAvailabilityDelegate>{
   IBOutlet UIButton* refreshButton;
   id <BusyIndicatorDelegate> busyIndicatorDelegate;
   EventDetailsTableViewController* detailsController;
   EventsSortedByTimeDataSource* dataSource;
   UIBarButtonItem* settingsButtonItem;
   UIBarButtonItem* sortButtonItem;
}

@property (nonatomic,retain) id <BusyIndicatorDelegate> busyIndicatorDelegate;
@property (retain) UIButton* refreshButton;
@property (retain) EventDetailsTableViewController* detailsController;
@property (retain) EventsSortedByTimeDataSource* dataSource;
@property (retain) UIBarButtonItem* settingsButtonItem;
@property (retain) UIBarButtonItem* sortButtonItem;

- (void) settings ;
- (void) sort ;


@end
