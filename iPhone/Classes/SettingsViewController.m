//
//  SettingsViewController.m
//  iPhone
//
//  Created by Ryan Schneider on 4/13/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "SettingsViewController.h"
#import "InterestAreaFilterController.h"


@implementation SettingsViewController

@synthesize backgroundButton;
@synthesize zipcodeField;
@synthesize nameField;
@synthesize emailField;
@synthesize useZipCode;
@synthesize scrollView;
@synthesize floatingView;
@synthesize settings;

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

- (void) updateSettings {
   self.nameField.text = [self.settings objectForKey: kSettingsKeyName];
   self.emailField.text = [self.settings objectForKey: kSettingsKeyEmail];
   self.zipcodeField.text = [self.settings objectForKey: kSettingsKeyZipcode];
   self.useZipCode.on = [[self.settings objectForKey: kSettingsKeyUseZipcode] boolValue];
}

- (void) loadSettings {
   [self.settings setObject: self.nameField.text forKey: kSettingsKeyName ];
   [self.settings setObject: self.emailField.text forKey: kSettingsKeyEmail ];
   [self.settings setObject: self.zipcodeField.text forKey: kSettingsKeyZipcode ];
   [self.settings setObject: [NSNumber numberWithBool: self.useZipCode.on] forKey: kSettingsKeyUseZipcode ];   
}

// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
   [super viewDidLoad];
   self.scrollView.contentSize = CGSizeMake(320, 550);
	self.scrollView.delaysContentTouches = NO;
   NSMutableDictionary* settings_ = (NSMutableDictionary*) CFPreferencesCopyAppValue((CFStringRef) kSettingsKey, 
                                                          kCFPreferencesCurrentApplication);
   if (settings_) {
      self.settings = settings_;
   }
   else {
      self.settings = [NSMutableDictionary dictionary];
      [self loadSettings];
   }

   [settings_ release];
   [self updateSettings];
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
   self.settings = nil;
   self.useZipCode = nil;
   self.floatingView = nil;
   self.backgroundButton = nil;
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

-(IBAction)saveSettings
{
	[self loadSettings];
	CFPreferencesSetAppValue((CFStringRef) kSettingsKey, self.settings, kCFPreferencesCurrentApplication);
	CFPreferencesAppSynchronize(kCFPreferencesCurrentApplication);
	UIAlertView* alert = [[UIAlertView alloc] initWithTitle: @"Settings Saved" message: @"Your settings have been saved." delegate: nil cancelButtonTitle: @"Ok" otherButtonTitles: nil];
	[alert show];
	[alert release];
}

-(IBAction)resetSettings
{
   UIActionSheet* action = [[UIActionSheet alloc] initWithTitle: @"Reset All Settings?" delegate: self cancelButtonTitle: @"Cancel" destructiveButtonTitle: @"Reset" otherButtonTitles: nil ];
   [action showInView: self.floatingView];
}

-(IBAction)filterInterestAreas {
    //TODO: navigation
    InterestAreaFilterController *controller = [[[ InterestAreaFilterController alloc] initWithNibName: @"InterestAreaFilterController" bundle: nil] autorelease];
    UINavigationController *nav = self.navigationController;
    [nav pushViewController: controller animated: YES];
}

- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex
{
   if(buttonIndex == 0) {
      [self.settings setObject: @"" forKey: kSettingsKeyName ];
      [self.settings setObject: @"" forKey: kSettingsKeyEmail ];
      [self.settings setObject: @"" forKey: kSettingsKeyZipcode ];
      [self.settings setObject: [NSNumber numberWithBool: NO] forKey: kSettingsKeyUseZipcode ];   
      [self updateSettings];
   }
}

@end

