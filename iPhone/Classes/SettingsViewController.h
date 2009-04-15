//
//  SettingsViewController.h
//  iPhone
//
//  Created by Ryan Schneider on 4/13/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface SettingsViewController : UIViewController {
   IBOutlet UITextField *zipcodeField;
   IBOutlet UITextField *nameField;
   IBOutlet UITextField *emailField;
   IBOutlet UIScrollView *scrollView;
}

@property (nonatomic, retain) UITextField *zipcodeField;
@property (nonatomic, retain) UITextField *nameField;
@property (nonatomic, retain) UITextField *emailField;
@property (nonatomic, retain) UIScrollView *scrollView;

- (IBAction) zipcodeUpdated;
- (IBAction) dismissKeyboard;
- (IBAction)scrollDown;
- (IBAction)scrollUp;

@end
