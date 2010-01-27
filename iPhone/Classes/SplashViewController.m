//
//  SplashViewController.m
//  iPhone
//
//  Created by Aubrey Francois on 10/18/08.
//  Copyright 2008 __MyCompanyName__. All rights reserved.
//

#import "SplashViewController.h"
#import "iVolunteerData.h"
#import "DateUtilities.h"
#import "SettingsViewController.h"
#import "iPhoneAppDelegate.h"

@implementation SplashViewController

@synthesize versionLabel;
@synthesize dismissalDelegate;
@synthesize busyIndicatorDelegate;
@synthesize zipcodeField;
@synthesize scrollView;
@synthesize continueButton;
@synthesize hand;
@synthesize logo;
@synthesize background;

/*
// Override initWithNibName:bundle: to load the view using a nib file then perform additional customization that is not appropriate for viewDidLoad.
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        // Custom initialization
    }
    return self;
}
*/

/*
// Implement loadView to create a view hierarchy programmatically.
- (void)loadView {
}
*/

- (void)openingAnimationComplete
{
	if ([busyIndicatorDelegate isBusy] && [[iVolunteerData sharedVolunteerData] reachable])
	{
        NSString* msg = NSLocalizedString(@"Determining location...", @"Tell user we are determining the location of their iPhone");
		[busyIndicatorDelegate startAnimatingWithMessage: msg atBottom: YES];
	}
}

- (void)viewDidLoad {
	self.zipcodeField.hidden = YES;
	scrollView.contentSize = CGSizeMake(320, 550);
	scrollView.delaysContentTouches = NO;
    
    NSString*	version = [[[NSBundle mainBundle] infoDictionary] objectForKey:@"CFBundleVersion"];
    self.versionLabel.text = [NSString stringWithFormat: @"Version: %@", version];
   
   UIImage* buttonImage = [[UIImage imageNamed:@"greenButton.png"] stretchableImageWithLeftCapWidth: 12.0 topCapHeight: 0 ];
   [continueButton setBackgroundImage: buttonImage forState: UIControlStateNormal];
   [continueButton setTitleColor: [UIColor whiteColor] forState: UIControlStateNormal ];
   [continueButton setHidden:YES];
   
   [super viewDidLoad];
   
   [UIView beginAnimations:@"animate logo" context:nil];
   [UIView setAnimationDuration: 0.75 ];
   [UIView setAnimationDelegate:self];
   [UIView setAnimationDidStopSelector:@selector(openingAnimationComplete)];
   [background setFrame: CGRectMake(20, 15, 280, 128)];
   [hand setFrame: CGRectMake(8, 0, 95, 138)];
   [logo setFrame: CGRectMake(76, 44, 226, 128)];
   [UIView commitAnimations];
   
}


- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning]; // Releases the view if it doesn't have a superview
    // Release anything that's not essential, such as cached data
}


- (void)dealloc {
	[(NSObject *) self.dismissalDelegate release];
	[(NSObject *) self.busyIndicatorDelegate release];
	[self.zipcodeField release];
	[self.scrollView release];
	[continueButton release];
	[hand release];
	[background release];
	[logo release];
    [versionLabel release];
    [super dealloc];
}

- (IBAction)splashOk:(id)sender forEvent:(UIEvent*)event
{
    NSLog(@"Hit Continue...");
	[dismissalDelegate dismissScreen];
}

- (void)loadDataFeed
{
	NSDate *start2 = [NSDate date];
    completedRestCount = 0;
	RestController *restController = [iPhoneAppDelegate RestController];
    [restController beginGetFilterData ];
	[restController beginGetEventsFrom: [DateUtilities today] until: [DateUtilities daysFromNow: 14]];
    restController.delegate = self;
	NSDate *end2 = [NSDate date];
	NSLog(@"rest contoller init: %g sec", [end2 timeIntervalSinceDate:start2]);	
}

- (IBAction)dismissKeyboard
{
	[self.zipcodeField resignFirstResponder];
	if ([self.zipcodeField.text length] == 5)
	{
        [SettingsViewController forceZipcodeSettings: self.zipcodeField.text];
		iVolunteerData *data = [iVolunteerData sharedVolunteerData];
		data.homeZip = self.zipcodeField.text;
		[self loadDataFeed];

	}
	[self scrollUp];
	
}

- (IBAction)zipcodeUpdated
{
	if ([self.zipcodeField.text length] >= 5)
	{
		[self dismissKeyboard];
	}
}

-(IBAction)scrollDown
{
	[self.scrollView setContentOffset:CGPointMake(0,100) animated:YES];
}

-(IBAction)scrollUp
{
	[self.scrollView setContentOffset:CGPointMake(0,0) animated:YES];
}

#pragma mark -
#pragma mark LocationAvailabilityDelegate methods

- (void)locationIsAvailable:(CLLocation *)location
{
	if (!location)
	{
		
		UIAlertView *alert = [[UIAlertView alloc] initWithTitle: NSLocalizedString(@"Could Not Determine Location", @"Could not determine location")
                                                      message:NSLocalizedString(@"Enter the zip code from which you would like to use to find volunteer opportunities.", @"How will we internationalize zipcodes?")
                                                     delegate:nil 
                                            cancelButtonTitle:NSLocalizedString(@"Ok", @"Ok")
                                            otherButtonTitles:nil];
		[alert show];
		[alert release];
		
		self.zipcodeField.hidden = NO;
	}
	else
	{
        if([[iVolunteerData sharedVolunteerData] reachable]) {
            [self loadDataFeed];
        }
        else {
        }
	}
	//[continueButton setHidden:NO];
    //[dismissalDelegate dismissScreen];
}

- (void) restController: (RestController*) controller
        didRetrieveData: (NSData*) data
          forRestClient: (RestClient*) client {
    completedRestCount++;
    if(completedRestCount == 2) {
        //Done!
        NSLog(@"Completed all requests!");
        [((NSObject*)dismissalDelegate) performSelector: @selector(dismissScreen) withObject: nil afterDelay: 0.3
        ];
        //[dismissalDelegate dismissScreen];
    }
}

- (void) restController: (RestController*) controller
       didFailWithError: (NSError*) error 
          forRestClient: (RestClient*) client {
    completedRestCount++;
    if(completedRestCount == 2) {
        //Done!
        NSLog(@"Completed all requests!");
        [((NSObject*)dismissalDelegate) performSelector: @selector(dismissScreen) withObject: nil afterDelay: 0.3
         ];
        //[dismissalDelegate dismissScreen];
    }
    if(client == controller.consolidatedClient) {
        [iPhoneAppDelegate displayConnectionError: error ];
    }
}

@end