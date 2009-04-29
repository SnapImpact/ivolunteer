//
//  SettingsViewController.h
//  iPhone
//
//  Created by Ryan Schneider on 4/13/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface SettingsViewController : UIViewController<UIActionSheetDelegate> {
   IBOutlet UITextField *zipcodeField;
   IBOutlet UITextField *nameField;
   IBOutlet UITextField *emailField;
   IBOutlet UISwitch *useZipCode;
   IBOutlet UIScrollView *scrollView;
   IBOutlet UIButton *backgroundButton;
   IBOutlet UIView *floatingView;

   NSMutableDictionary* settings;
}

@property (nonatomic, retain) UIButton *backgroundButton;
@property (nonatomic, retain) UITextField *zipcodeField;
@property (nonatomic, retain) UITextField *nameField;
@property (nonatomic, retain) UITextField *emailField;
@property (nonatomic, retain) UISwitch *useZipCode;
@property (nonatomic, retain) UIScrollView *scrollView;
@property (nonatomic, retain) UIView *floatingView;
@property (nonatomic, retain) NSMutableDictionary *settings;

- (IBAction) zipcodeUpdated;
- (IBAction) dismissKeyboard;
- (IBAction)scrollDown;
- (IBAction)scrollUp;
- (IBAction)saveSettings;
- (IBAction)resetSettings;

@end

