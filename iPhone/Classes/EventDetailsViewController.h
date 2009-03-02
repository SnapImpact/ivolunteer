//
//  EventDetailsViewController.h
//  iPhone
//
//  Created by Ryan Schneider on 2/26/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Event.h"

@interface EventDetailsViewController : UIViewController {
   Event* event;
}

@property (retain) Event* event;

+ (id) view;

@end
