//
//  EventTableCell.h
//  iPhone
//
//  Created by Ryan Schneider on 2/23/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Event.h"

@interface EventTableCell : UITableViewCell {
   IBOutlet UILabel* time;
   IBOutlet UILabel* distance;
   IBOutlet UILabel* name;
   IBOutlet UILabel* details;
   Event* event;
}

+ (CGFloat) height;
+ (NSString*) reuseIdentifier;

@property (nonatomic, retain) UILabel* time;
@property (nonatomic, retain) UILabel* distance;
@property (nonatomic, retain) UILabel* name;
@property (nonatomic, retain) UILabel* details;
@property (nonatomic, retain) Event* event;

@end
