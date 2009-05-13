//
//  InterestAreaFilterTableViewCell.m
//  iPhone
//
//  Created by Ryan Schneider on 5/12/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "InterestAreaFilterTableViewCell.h"


@implementation InterestAreaFilterTableViewCell

@synthesize interestArea;
@synthesize label;
@synthesize selectedButton;
@synthesize delegate;

- (id)initWithFrame:(CGRect)frame reuseIdentifier:(NSString *)reuseIdentifier {
    if (self = [super initWithFrame:frame reuseIdentifier:reuseIdentifier]) {
        // Initialization code
    }
    return self;
}

- (void) setInterestArea: (InterestArea*) interestArea_ {
    if(interestArea)
        [interestArea release];
    
    interestArea = [interestArea_ retain];
    //update the label
    self.label.text = interestArea.name;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {

    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

-(IBAction)toggle
{
    [delegate toggleInterestArea:self.interestArea];
}

- (void)dealloc {
    self.selectedButton = nil;
    self.label = nil;
    self.interestArea = nil;
    self.delegate = nil;
    [super dealloc];
}


@end



