//
//  SettingsViewController.m
//  iPhone
//
//  Created by Ryan Schneider on 4/13/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "SettingsViewController.h"
#import "InterestAreaFilterController.h"
#import "iPhoneAppDelegate.h"


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

+ (void) forceZipcodeSettings: (NSString*) zip {
    NSMutableDictionary* settings_ = (NSMutableDictionary*) CFPreferencesCopyAppValue((CFStringRef) kSettingsKey, 
                                                                                      kCFPreferencesCurrentApplication);
    if(!settings_) {
        settings_ = [NSMutableDictionary dictionary];
    }
    [settings_ setObject: zip forKey: kSettingsKeyZipcode];
    [settings_ setObject: [NSNumber numberWithBool: YES] forKey: kSettingsKeyUseZipcode];
    
    CFPreferencesSetAppValue((CFStringRef) kSettingsKey, settings_, kCFPreferencesCurrentApplication);
	CFPreferencesAppSynchronize(kCFPreferencesCurrentApplication);
}

- (void) loadSettings {
    if(!self.settings) {
        self.settings = [NSMutableDictionary dictionary];
    }
    else {
        self.settings = [NSMutableDictionary dictionaryWithDictionary: self.settings];
    }
        
    @try {
        [self.settings setObject: self.nameField.text forKey: kSettingsKeyName ];
        [self.settings setObject: self.emailField.text forKey: kSettingsKeyEmail ];
        [self.settings setObject: self.zipcodeField.text forKey: kSettingsKeyZipcode ];
        [self.settings setObject: [NSNumber numberWithBool: self.useZipCode.on] forKey: kSettingsKeyUseZipcode ];   
    }
    @catch (NSException* exception) {
        NSLog(@"Settings dictionary became immutable again...");
    }
}

// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewWillAppear:(BOOL)animated {
    self.scrollView.contentSize = CGSizeMake(320, 550);
	self.scrollView.delaysContentTouches = NO;
    NSMutableDictionary* settings_ = (NSMutableDictionary*) CFPreferencesCopyAppValue((CFStringRef) kSettingsKey, 
                                                                                      kCFPreferencesCurrentApplication);
    if (settings_) {
        self.settings = settings_;
    }
    else {
        [self loadSettings];
    }
    
    [settings_ release];
    [self updateSettings];
    [super viewWillAppear: animated];
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
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle: @"Debugging"
                                                    message: @"Deallocing..."
                                                   delegate:nil 
                                          cancelButtonTitle:NSLocalizedString(@"Ok", @"Ok")
                                          otherButtonTitles:nil];
    [alert show];
    [alert release];
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
    [ self saveSettings];
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
    self.settings = [NSMutableDictionary dictionaryWithDictionary: self.settings];
    /*
	UIAlertView* alert = [[UIAlertView alloc] initWithTitle: @"Settings Saved" message: @"Your settings have been saved." delegate: nil cancelButtonTitle: @"Ok" otherButtonTitles: nil];
	[alert show];
	[alert release];
     */
}

-(IBAction)resetSettings
{
    UIActionSheet* action = [[UIActionSheet alloc] initWithTitle: @"Reset All Settings?" delegate: self cancelButtonTitle: @"Cancel" destructiveButtonTitle: @"Reset" otherButtonTitles: nil ];
    [iPhoneAppDelegate showActionSheet: action];
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
        self.settings = [NSMutableDictionary dictionaryWithDictionary: self.settings];
        [self.settings setObject: @"" forKey: kSettingsKeyName ];
        [self.settings setObject: @"" forKey: kSettingsKeyEmail ];
        [self.settings setObject: @"" forKey: kSettingsKeyZipcode ];
        [self.settings setObject: [NSNumber numberWithBool: NO] forKey: kSettingsKeyUseZipcode ];   
        [InterestArea saveInterestAreasToPreferences: [[iVolunteerData sharedVolunteerData] interestAreasByName]];
        [self updateSettings];
        [self saveSettings];
    }
}

@end

