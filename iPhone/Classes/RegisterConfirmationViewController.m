//
//  RegisterConfirmationViewController.m
//  iPhone
//
//  Created by Ryan Schneider on 4/21/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "RegisterConfirmationViewController.h"
#import "DateUtilities.h"


@implementation RegisterConfirmationViewController

@synthesize window;
@synthesize cancelButton;
@synthesize confirmButton;
@synthesize event;
@synthesize eventTime;
@synthesize eventDate;
@synthesize eventOrganization;
@synthesize eventName;
@synthesize emailField;
@synthesize nameField;
@synthesize delegate;

/*
// The designated initializer. Override to perform setup that is required before the view is loaded.
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        // Custom initialization
    }
    return self;
}
*/

/*
// Implement loadView to create a view hierarchy programmatically, without using a nib.
- (void)loadView {
}
*/


// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
   [super viewDidLoad];
   UIImage* buttonGrey = [[UIImage imageNamed:@"whiteButton.png"] stretchableImageWithLeftCapWidth: 12.0 topCapHeight: 0 ];
   UIImage* buttonGreen = [[UIImage imageNamed:@"greenButton.png"] stretchableImageWithLeftCapWidth: 12.0 topCapHeight: 0 ];
   
   [self.cancelButton setBackgroundImage: buttonGrey forState: UIControlStateNormal ];
   [self.confirmButton setBackgroundImage: buttonGreen forState: UIControlStateNormal ];
}

/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
*/

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning]; // Releases the view if it doesn't have a superview
    // Release anything that's not essential, such as cached data
}


- (void)dealloc {
   self.eventTime = nil;
   self.eventDate = nil;
   self.eventOrganization = nil;
   self.eventName = nil;
   self.emailField = nil;
   self.nameField = nil;
   self.event = nil;
   self.delegate = nil;
   self.window = nil;
   [super dealloc];
}

- (void) setEvent: (Event*) event_ 
{
   if(event)
      [event release];
   
   event = [event_ retain];
   self.eventName.text = event_.name;
   self.eventOrganization.text = event_.organization.name;
   self.eventDate.text = [DateUtilities formatMediumDate: event_.date ];
   self.eventTime.text = [DateUtilities formatShortTime: event_.date ];
}

- (void) dismissSheet {
   [UIView beginAnimations: @"Sheet slide out" context: nil];
   self.view.frame = CGRectMake(0, 480, self.view.frame.size.width, self.view.frame.size.height);
   [UIView commitAnimations];
   [self.view removeFromSuperview];
   [self dealloc];
}

- (IBAction) confirmClicked {
   NSObject<RegisterConfirmationProtocol>* delegate_ = self.delegate;
   [self dismissSheet];
   if(delegate_ && [delegate_ respondsToSelector:@selector(didConfirmRegistration)]) {
      [delegate_ didConfirmRegistration];
   }
}

- (IBAction) cancelClicked {
   NSObject<RegisterConfirmationProtocol>* delegate_ = self.delegate;
   [self dismissSheet];
   if(delegate_ && [delegate_ respondsToSelector:@selector(didCancelRegistration)]) {
      [delegate_ didCancelRegistration];
   }
}

- (IBAction) moveUpForKeyboard {
   
   [UIView beginAnimations: @"Slide up for Keyboard" context: nil];
   self.view.frame = CGRectMake(0, -45, self.view.frame.size.width, self.view.frame.size.height);
   [UIView commitAnimations];
}

- (IBAction) dismissKeyboard {
   [ self.emailField resignFirstResponder ];
   [ self.nameField resignFirstResponder ];

   [UIView beginAnimations: @"Slide up for Keyboard" context: nil];
   self.view.frame = CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height);
   [UIView commitAnimations];
   
}

+ (void) displaySheetForEvent: (Event*) event inWindow: (UIWindow*) window delegate: (NSObject<RegisterConfirmationProtocol>*) delegate
{
   RegisterConfirmationViewController* controller = [[RegisterConfirmationViewController alloc] initWithNibName: @"RegisterConfirmationView" bundle: [NSBundle mainBundle]];
   controller.delegate = delegate;
   controller.window = window;
   
   [window addSubview: controller.view];
   controller.event = event;
   controller.view.frame =  CGRectMake(0, 480, controller.view.frame.size.width, controller.view.frame.size.height);
   
   [UIView beginAnimations: @"Sheet slide in" context: nil];
   controller.view.frame = CGRectMake(0, 0, controller.view.frame.size.width, controller.view.frame.size.height);
   [UIView commitAnimations];
}

@end










