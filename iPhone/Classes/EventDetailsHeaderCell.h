//
//  EventDetailsHeaderViewController.h
//  iPhone
//
//  Created by Ryan Schneider on 2/28/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Event.h"

@interface EventDetailsHeaderCell : UITableViewCell {
   Event* event;
   IBOutlet UILabel* name;
   IBOutlet UILabel* organization;
   IBOutlet UILabel* date;
   IBOutlet UILabel* time;
}

@property (retain) Event* event;
@property (retain) UILabel* name;
@property (retain) UILabel* organization;
@property (retain) UILabel* date;
@property (retain) UILabel* time;

+ (NSString*) reuseIdentifier;
+ (CGFloat) height;

@end
