//
//  iPhoneAppDelegate.m
//  iPhone
//
//  Created by Aubrey Francois on 10/18/08.
//  Copyright __MyCompanyName__ 2008. All rights reserved.
//

#import "iPhoneAppDelegate.h"
#import "ProjectViewController.h"


@implementation iPhoneAppDelegate

@synthesize window;
@synthesize navigationController;


- (void)applicationDidFinishLaunching:(UIApplication *)application {
	//check the user defaults for firstRun
	NSUserDefaults *def = [NSUserDefaults standardUserDefaults];
	bool hasBeenRun = [def boolForKey:@"ivHasBeenRun"];
	
	[def setBool:true forKey:@"ivHasBeenRun"];
	
	if(!hasBeenRun){
		//load the splash screen
		splashvc = [[SplashViewController alloc] initWithNibName:@"SplashView" bundle:[NSBundle mainBundle]];
		splashvc.delegate = self;
		[window addSubview:[splashvc view]];
	} else {
		[self loadNavigationView];
	}
		[window makeKeyAndVisible];
	
}


- (void)applicationWillTerminate:(UIApplication *)application {
	// Save data if appropriate
}

-(void) loadNavigationView
{
	[[splashvc view] removeFromSuperview];
	// Configure and show the window
	[window addSubview:[navigationController view]];
	[window setNeedsDisplay];	
}

- (void)dealloc {
	[navigationController release];
	[window release];
	[super dealloc];
}

-(void) splashDidDoOk
{
	[self loadNavigationView];
}
	
		
									   
@end
