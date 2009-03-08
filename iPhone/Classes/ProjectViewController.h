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

@interface ProjectViewController : UITableViewController {
   IBOutlet UIButton* refreshButton;
   EventDetailsTableViewController* detailsController;
   EventsSortedByTimeDataSource* dataSource;
   UIBarButtonItem* settingsButtonItem;
   UIBarButtonItem* sortButtonItem;
}

@property (retain) UIButton* refreshButton;
@property (retain) EventDetailsTableViewController* detailsController;
@property (retain) EventsSortedByTimeDataSource* dataSource;
@property (retain) UIBarButtonItem* settingsButtonItem;
@property (retain) UIBarButtonItem* sortButtonItem;

- (void) settings ;
- (void) sort ;

@end
