//
//  SettingsViewController.m
//  iPhone
//
//  Created by Ryan Schneider on 4/13/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "SettingsViewController.h"


@implementation SettingsViewController

@synthesize radiusPicker;
@synthesize radiusLabel;
@synthesize radiusPickerData;
@synthesize zipcodeField;
@synthesize nameField;
@synthesize emailField;
@synthesize zipOrLocationSegment;

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
   self.radiusPickerData = [NSArray arrayWithObjects: @" 5", @"10", @"25", @"50", nil ];
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
}

#pragma mark Picker Data Source
- (NSInteger) numberOfComponentsInPickerView: (UIPickerView*) pickerView
{
   return 1;
}

- (NSInteger) pickerView: (UIPickerView*) pickerView
 numberOfRowsInComponent: (NSInteger) component 
{
   return [self.radiusPickerData count];
}

- (NSString*) pickerView: (UIPickerView*) pickerView
             titleForRow: (NSInteger) row
            forComponent: (NSInteger) component
{
   return [self.radiusPickerData objectAtIndex:row];
}

- (IBAction)zipcodeUpdated
{
	if ([self.zipcodeField.text length] >= 5)
	{
      [self.zipcodeField resignFirstResponder];
	}
}

- (IBAction) dismissKeyboard
{
   [ self.zipcodeField resignFirstResponder ]; 
   [ self.nameField resignFirstResponder ]; 
   [ self.emailField resignFirstResponder ]; 
}

- (IBAction) zipOrLocationSegmentChanged
{
   [ self.zipcodeField resignFirstResponder ]; 
   [ self.nameField resignFirstResponder ]; 
   [ self.emailField resignFirstResponder ]; 
   
   if ([self.zipOrLocationSegment selectedSegmentIndex] == 0 ) {
      [ self.zipcodeField setHidden: YES];
      [ self.radiusPicker setHidden: NO];
      [ self.radiusLabel setHidden: NO];
   }
   else {
      [ self.zipcodeField setHidden: NO];
      [ self.radiusPicker setHidden: YES];
      [ self.radiusLabel setHidden: YES];
   }
}

@end
