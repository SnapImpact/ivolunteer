//
//  SettingsViewController.m
//  iPhone
//
//  Created by Ryan Schneider on 4/13/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "SettingsViewController.h"


@implementation SettingsViewController

@synthesize zipcodeField;
@synthesize nameField;
@synthesize emailField;
@synthesize scrollView;

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
   self.scrollView.contentSize = CGSizeMake(320, 550);
	self.scrollView.delaysContentTouches = NO;
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
   [super dealloc];
   self.zipcodeField = nil;
   self.nameField = nil;
   self.emailField = nil;
   self.scrollView = nil;
}

- (IBAction)zipcodeUpdated
{
	if ([self.zipcodeField.text length] >= 5)
	{
      [self dismissKeyboard];
	}
}

- (IBAction) dismissKeyboard
{
   [ self.zipcodeField resignFirstResponder ]; 
   [ self.nameField resignFirstResponder ]; 
   [ self.emailField resignFirstResponder ]; 
   [ self scrollUp ];
}

-(IBAction)scrollDown
{
	[self.scrollView setContentOffset:CGPointMake(0,100) animated:YES];
}

-(IBAction)scrollUp
{
	[self.scrollView setContentOffset:CGPointMake(0,0) animated:YES];
}


@end
