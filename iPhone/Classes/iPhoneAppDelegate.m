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
#import "DateUtilities.h"

@implementation iPhoneAppDelegate

@synthesize window;
@synthesize navigationController;
@synthesize tabBarController;
@synthesize floatingView;
@synthesize busyIndicatorView;
@synthesize busyIndicatorLabel;
@synthesize locationDelegate;
@synthesize restController;

- (void)getLocation
{
	// initialize timestamp to use to compare results from location services.
	now = [[NSDate alloc] init];
	
	CLLocationManager * locationMgr = [[CLLocationManager alloc] init];
	if (locationMgr)
	{
		isBusy = YES;
		locationMgr.delegate = self;
		locationMgr.desiredAccuracy = kCLLocationAccuracyKilometer;
		[locationMgr startUpdatingLocation];
	}
}

- (void)locationManager:(CLLocationManager *)manager 
	   didFailWithError:(NSError *)error
{
	NSLog(@"Getting fix on current position, no position yet...");
	if (error.code == kCLErrorDenied)
	{
		// user denied request to determine location
		[manager stopUpdatingLocation];
		if (locationDelegate)
		{
			[locationDelegate locationIsAvailable:nil];
		}
	}
}
- (void)locationManager:(CLLocationManager *)manager 
	didUpdateToLocation:(CLLocation *)newLocation 
		   fromLocation:(CLLocation *)oldLocation
{
	NSLog(@"Getting fix on current position...");
	if ([newLocation.timestamp compare:now] == NSOrderedDescending)
	{
		[manager stopUpdatingLocation];
		if (locationDelegate)
		{
			[locationDelegate locationIsAvailable:newLocation];
		}
		iVolunteerData *data = [iVolunteerData sharedVolunteerData];
		data.myLocation = newLocation;
	}
}

- (void)applicationDidFinishLaunching:(UIApplication *)application {
	
	//TODO: check for internet connectivity!
	
	NSDate *start1 = [NSDate date];
	[iVolunteerData restore];
	NSDate *end1 = [NSDate date];
	NSLog(@"[iVolunteerData restore]: %g sec", [end1 timeIntervalSinceDate:start1]);
	
	NSDate *start2 = [NSDate date];
	self.restController = [[RestController alloc] initWithVolunteerData: [iVolunteerData sharedVolunteerData]];
	[ self.restController beginGetEventsFrom: [DateUtilities today] until: [DateUtilities daysFromNow: 14]];
	NSDate *end2 = [NSDate date];
	NSLog(@"rest contoller init: %g sec", [end2 timeIntervalSinceDate:start2]);
	
	[self getLocation];
	
	splashvc = [[SplashViewController alloc] initWithNibName:@"SplashView" bundle:[NSBundle mainBundle]];
	splashvc.dismissalDelegate = self;
	splashvc.busyIndicatorDelegate = self;
	
   self.locationDelegate = splashvc;
   [window addSubview:[splashvc view]];
	[window makeKeyAndVisible];	
}


- (void)applicationWillTerminate:(UIApplication *)application {
	// Save data if appropriate
   [ iVolunteerData archive ];
}

-(void) loadNavigationView
{   
	[[splashvc view] removeFromSuperview];
	// Configure and show the window
#define USE_TABS
#ifdef USE_TABS
   [window addSubview:[tabBarController view]];
   [window setNeedsDisplay];
   UIViewController* viewController = [tabBarController selectedViewController];
#else
   [window addSubview:[navigationController view]];
	[window setNeedsDisplay];	
	UIViewController *viewController = [navigationController topViewController];	
#endif
   
	if ([viewController respondsToSelector:@selector(setBusyIndicatorDelegate:)])
	{
		[viewController performSelector:@selector(setBusyIndicatorDelegate:) withObject:self];
	}
	
	if ([viewController conformsToProtocol:@protocol(LocationAvailabilityDelegate)])
	{
		id <LocationAvailabilityDelegate> locationAvailabilityDelegate = (id <LocationAvailabilityDelegate>)viewController;
		self.locationDelegate = locationAvailabilityDelegate;
	}
	
}

- (void)dealloc {
	[(NSObject *) locationDelegate release];
	[floatingView release];
	[busyIndicatorView release];
	[busyIndicatorLabel release];
	[navigationController release];
   [tabBarController release];
	[now release];
	[restController release];
	[window release];
	[super dealloc];
}

#pragma mark -
#pragma mark ScreenDismissalDelegate methods

- (void)dismissScreen
{
	[self loadNavigationView];
}


#pragma mark -
#pragma mark BusyIndicatorDelegate methods
	
- (BOOL)isBusy
{
	return isBusy;
}

- (void)stopAnimating
{
	isBusy = NO;
	[self.floatingView removeFromSuperview];
}
- (void)startAnimatingWithMessage:(NSString *)message
{
	[self startAnimatingWithMessage:message atBottom:NO];
}

- (void)startAnimatingWithMessage:(NSString *)message atBottom:(BOOL)atBottom
{
	isBusy = YES;
	self.busyIndicatorView.hidden = NO;
	if (message)
	{
		self.busyIndicatorLabel.text = message;
	}
	else
	{
		self.busyIndicatorLabel.text = NSLocalizedString(@"Please wait...", @"Tell user to wait/that application is busy.");
	}
	
	if (atBottom)
	{
		self.busyIndicatorView.frame = CGRectMake(self.busyIndicatorView.frame.origin.x, 
												  400, 
												  self.busyIndicatorView.frame.size.width, 
												  self.busyIndicatorView.frame.size.height);
	}
	else
	{
		self.busyIndicatorView.frame = CGRectMake(self.busyIndicatorView.frame.origin.x, 
												  210, 
												  self.busyIndicatorView.frame.size.width, 
												  self.busyIndicatorView.frame.size.height);
	}
	
	[window addSubview:self.floatingView];
	[window bringSubviewToFront:self.floatingView];
}
									   
@end
