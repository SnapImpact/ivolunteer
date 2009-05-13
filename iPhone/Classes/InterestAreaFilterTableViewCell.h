//
//  InterestAreaFilterTableViewCell.h
//  iPhone
//
//  Created by Ryan Schneider on 5/12/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "InterestArea.h"
#import "InterestAreaSelectionDelegateProtocol.h"

@interface InterestAreaFilterTableViewCell : UITableViewCell {
    IBOutlet UIButton* selectedButton;
    IBOutlet UILabel* label;
    InterestArea* interestArea;
    IBOutlet NSObject<InterestAreaSelectionDelegateProtocol> *delegate;
}

@property (nonatomic, retain) InterestArea *interestArea;
@property (nonatomic, retain) UILabel *label;
@property (nonatomic, retain) UIButton *selectedButton;
@property (nonatomic, retain) NSObject<InterestAreaSelectionDelegateProtocol> *delegate;

-(IBAction)toggle;

@end



