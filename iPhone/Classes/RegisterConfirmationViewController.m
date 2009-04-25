//
//  RegisterConfirmationViewController.m
//  iPhone
//
//  Created by Ryan Schneider on 4/21/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "RegisterConfirmationViewController.h"


@implementation RegisterConfirmationViewController

@synthesize eventTime;
@synthesize eventDate;
@synthesize eventOrganization;
@synthesize eventName;
@synthesize emailField;
@synthesize nameField;

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

/*
// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
}
*/

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
   [super dealloc];
   self.eventTime = nil;
   self.eventDate = nil;
   self.eventOrganization = nil;
   self.eventName = nil;
   self.emailField = nil;
   self.nameField = nil;
}

- (IBAction) confirmClicked {
}

- (IBAction) cancelClicked {
}

- (IBAction) dismissKeyboard {
   [ self.emailField resignFirstResponder ];
   [ self.nameField resignFirstResponder ];
}


@end






