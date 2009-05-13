//
//  InterestAreaFilterController.h
//  iPhone
//
//  Created by Ryan Schneider on 5/12/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "InterestAreaSelectionDelegateProtocol.h"

@interface InterestAreaFilterController : UIViewController<UITableViewDelegate, UITableViewDataSource, InterestAreaSelectionDelegateProtocol> {
    NSMutableArray* allInterestAreas;
    NSMutableArray* selectedInterestAreas;    
    IBOutlet UITableView* view;
}

@property (nonatomic, retain) NSMutableArray *allInterestAreas;
@property (nonatomic, retain) UITableView *view;
@property (nonatomic, retain) NSMutableArray *selectedInterestAreas;

- (IBAction) save;

@end



