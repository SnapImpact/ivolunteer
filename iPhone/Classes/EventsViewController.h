//
//  EventsViewController.h
//  iPhone
//
//  Created by Ryan Schneider on 4/28/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "EventDetailsTableViewController.h"
#import "RootViewDataSourceProtocol.h"
#import "BusyIndicatorDelegate.h"
#import "LocationAvailabilityDelegate.h"

@interface EventsViewController : UITableViewController<LocationAvailabilityDelegate> {
   IBOutlet UIButton* refreshButton;
   id <BusyIndicatorDelegate> busyIndicatorDelegate;
   EventDetailsTableViewController* detailsController;
   NSObject<RootViewDataSourceProtocol, UITableViewDataSource>* dataSource;   
}

@property (nonatomic,retain) id <BusyIndicatorDelegate> busyIndicatorDelegate;
@property (retain) UIButton* refreshButton;
@property (retain) EventDetailsTableViewController* detailsController;
@property (retain) NSObject<RootViewDataSourceProtocol, UITableViewDataSource>* dataSource;


@end
