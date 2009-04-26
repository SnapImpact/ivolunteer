//
//  RegisterConfirmationViewController.h
//  iPhone
//
//  Created by Ryan Schneider on 4/21/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Event.h"

@protocol RegisterConfirmationProtocol <NSObject>
@optional
- (void) didConfirmRegistration;
- (void) didCancelRegistration;
@end

@interface RegisterConfirmationViewController : UIViewController {
   IBOutlet UITextField* nameField;
   IBOutlet UITextField* emailField;
   IBOutlet UILabel* eventName;
   IBOutlet UILabel* eventOrganization;
   IBOutlet UILabel* eventDate;
   IBOutlet UILabel* eventTime;
   IBOutlet UIButton* confirmButton;
   IBOutlet UIButton* cancelButton;
   
   UIWindow* window;
   NSObject<RegisterConfirmationProtocol>* delegate;
   Event* event;
}

@property (nonatomic, retain) UIWindow *window;
@property (nonatomic, retain) UIButton *cancelButton;
@property (nonatomic, retain) UIButton *confirmButton;
@property (nonatomic, retain) Event *event;
@property (nonatomic, retain) UILabel *eventTime;
@property (nonatomic, retain) UILabel *eventDate;
@property (nonatomic, retain) UILabel *eventOrganization;
@property (nonatomic, retain) UILabel *eventName;
@property (nonatomic, retain) UITextField *emailField;
@property (nonatomic, retain) UITextField *nameField;
@property (nonatomic, retain) NSObject<RegisterConfirmationProtocol> *delegate;

- (IBAction) confirmClicked;
- (IBAction) cancelClicked;
- (IBAction) dismissKeyboard;
- (IBAction) moveUpForKeyboard;

+ (void) displaySheetForEvent: (Event*) event inWindow: (UIWindow*) window delegate: (NSObject<RegisterConfirmationProtocol>*) delegate;

@end




