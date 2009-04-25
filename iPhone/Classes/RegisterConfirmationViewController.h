//
//  RegisterConfirmationViewController.h
//  iPhone
//
//  Created by Ryan Schneider on 4/21/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface RegisterConfirmationViewController : UIViewController {
   IBOutlet UITextField* nameField;
   IBOutlet UITextField* emailField;
   IBOutlet UILabel* eventName;
   IBOutlet UILabel* eventOrganization;
   IBOutlet UILabel* eventDate;
   IBOutlet UILabel* eventTime;
}

@property (nonatomic, retain) UILabel *eventTime;
@property (nonatomic, retain) UILabel *eventDate;
@property (nonatomic, retain) UILabel *eventOrganization;
@property (nonatomic, retain) UILabel *eventName;
@property (nonatomic, retain) UITextField *emailField;
@property (nonatomic, retain) UITextField *nameField;

- (IBAction) confirmClicked;
- (IBAction) cancelClicked;
- (IBAction) dismissKeyboard;

@end
