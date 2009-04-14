//
//  SettingsViewController.h
//  iPhone
//
//  Created by Ryan Schneider on 4/13/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface SettingsViewController : UIViewController<UIPickerViewDelegate, UIPickerViewDataSource> {
   NSArray *radiusPickerData;
   IBOutlet UIPickerView* radiusPicker;
   IBOutlet UILabel *radiusLabel;
   IBOutlet UITextField *zipcodeField;
   IBOutlet UITextField *nameField;
   IBOutlet UITextField *emailField;
   IBOutlet UISegmentedControl *zipOrLocationSegment;
}

@property (nonatomic, retain) UIPickerView* radiusPicker;
@property (nonatomic, retain) UILabel* radiusLabel;
@property (nonatomic, retain) NSArray* radiusPickerData;
@property (nonatomic, retain) UITextField *zipcodeField;
@property (nonatomic, retain) UITextField *nameField;
@property (nonatomic, retain) UITextField *emailField;
@property (nonatomic, retain) UISegmentedControl *zipOrLocationSegment;

- (IBAction) zipcodeUpdated;
- (IBAction) zipOrLocationSegmentChanged;
- (IBAction) dismissKeyboard;

@end
