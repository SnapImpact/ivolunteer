//
//  iPhoneAppDelegate.m
//  iPhone
/*
 *  Copyright (c) 2008 Boulder Community Foundation - iVolunteer
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
//
//  Author Aubrey Francois on 10/18/08.
//

#import "iPhoneAppDelegate.h"
#import "ProjectViewController.h"
#import "iVolunteerData.h"

@implementation iPhoneAppDelegate

@synthesize window;
@synthesize navigationController;


- (void)applicationDidFinishLaunching:(UIApplication *)application {
	//check the user defaults for firstRun
	NSUserDefaults *def = [NSUserDefaults standardUserDefaults];
	bool hasBeenRun = [def boolForKey:@"ivHasBeenRun"];
	
	[def setBool:true forKey:@"ivHasBeenRun"];
	hasBeenRun = NO;
   
	if(!hasBeenRun){
		//load the splash screen
		splashvc = [[SplashViewController alloc] initWithNibName:@"SplashView" bundle:[NSBundle mainBundle]];
		splashvc.delegate = self;
		[window addSubview:[splashvc view]];
	} else {
		[self loadNavigationView];
	}
		
   [window makeKeyAndVisible];
   [iVolunteerData restore];
}


- (void)applicationWillTerminate:(UIApplication *)application {
	// Save data if appropriate
   [ iVolunteerData archive ];
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
