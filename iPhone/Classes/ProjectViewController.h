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
}
@property (nonatomic,retain) id <BusyIndicatorDelegate> busyIndicatorDelegate;
@property (nonatomic, retain) UIButton* refreshButton;
@property (nonatomic, retain) EventDetailsTableViewController* detailsController;
@property (nonatomic, retain) EventsSortedByTimeDataSource* dataSource;

@end
