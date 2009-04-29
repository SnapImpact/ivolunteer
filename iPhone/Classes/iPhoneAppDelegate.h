//
//  iPhoneAppDelegate.h
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

#import <UIKit/UIKit.h>
#import "SplashViewController.h"
#import "LocationAvailabilityDelegate.h"
#import "BusyIndicatorDelegate.h"
#import "ScreenDismissalDelegate.h"
#import "MemoryTabViewController.h"
#import "RestController.h"
@interface iPhoneAppDelegate : NSObject <UIApplicationDelegate, BusyIndicatorDelegate, ScreenDismissalDelegate, CLLocationManagerDelegate> {
    
	id <LocationAvailabilityDelegate> locationDelegate;
   UIWindow *window;
   UINavigationController *navigationController;
   MemoryTabViewController* tabBarController;
	SplashViewController *splashvc;
	UIView *floatingView;
	UIView *busyIndicatorView;
	UILabel *busyIndicatorLabel;
	NSDate *now;
   RestController *restController;
	BOOL isBusy;
}

@property (nonatomic, retain) id <LocationAvailabilityDelegate> locationDelegate;
@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet UINavigationController *navigationController;
@property (nonatomic, retain) IBOutlet MemoryTabViewController *tabBarController;
@property (nonatomic, retain) IBOutlet UIView *floatingView;
@property (nonatomic, retain) IBOutlet UIView *busyIndicatorView;
@property (nonatomic, retain) IBOutlet UILabel *busyIndicatorLabel;
@property (retain) RestController* restController;


-(void) loadNavigationView;
@end

