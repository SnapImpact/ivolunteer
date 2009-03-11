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
}

@property (nonatomic, retain) UIButton* refreshButton;
@property (nonatomic, retain) EventDetailsTableViewController* detailsController;
@property (nonatomic, retain) EventsSortedByTimeDataSource* dataSource;

@end
