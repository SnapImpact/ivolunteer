//
//  UpcomingEventsViewController.h
//  iPhone
//
//  Created by Ryan Schneider on 4/28/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "EventsViewController.h"
#import "RestControllerDelegate.h"

@interface UpcomingEventsViewController : EventsViewController<RestControllerDelegate> {

}

-(IBAction) refresh;

@end
