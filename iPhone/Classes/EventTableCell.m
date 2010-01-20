//
//  EventTableCell.m
//  iPhone
//
//  Created by Ryan Schneider on 2/23/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "EventTableCell.h"
#import "DateUtilities.h"


@implementation EventTableCell


@synthesize time;
@synthesize distance;
@synthesize name;
@synthesize details;
@synthesize event;

+ (CGFloat) height
{
    return 54;
}

- (id)initWithFrame:(CGRect)frame reuseIdentifier:(NSString *)reuseIdentifier {
    if (self = [super initWithFrame:frame reuseIdentifier:reuseIdentifier]) {
        // Initialization code
    }
    return self;
}

+ (NSString*) reuseIdentifier
{
    return @"EventTableCell";
}


- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    
    [super setSelected:selected animated:animated];
    
    // Configure the view for the selected state
}


- (Event*) event {
    return event;
}

- (void) setEvent: (Event*) e {
    event = [e retain];
    self.name.text = e.name;
    self.details.text = e.details;
    NSString* time_ = [DateUtilities formatShortTime: e.date ];
    self.time.text = time_;
    double distance_ = [e.distance doubleValue];
    if(distance_ < 1.51) {
        self.distance.text = @"Nearby";
    }
    else {
        self.distance.text = [NSString stringWithFormat: @"%l.0f miles", distance_];
    }
}


- (void)dealloc {
    self.name =  nil;
    self.details = nil;
    self.time = nil;
    self.distance = nil;
    [event release];
    event = nil;
    
    [super dealloc];
}


@end
