//
//  MemoryTabViewController.m
//  InfraRedbox
//
//  Created by Hassan Abdel-Rahman on 1/28/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "MemoryTabViewController.h"


@implementation MemoryTabViewController

- (void) awakeFromNib
{
	[self setDelegate: self];
	[self.moreNavigationController setDelegate:self];
	
	// set more view controller to use custom color (crimson) nav bar
	self.moreNavigationController.navigationBar.barStyle = UIBarStyleBlackOpaque;
	UIColor * barColor = [[UIColor alloc] initWithRed:0.584 green:0.039 blue:0.035 alpha:1.0];
	self.moreNavigationController.navigationBar.tintColor = barColor;
	[barColor release];
}


- (void)tabBarController:(UITabBarController *)aTabBarController didSelectViewController:(UIViewController *)viewController
{
	for (UIViewController *aViewController in aTabBarController.viewControllers)
	{
		if (![viewController isEqual:aViewController])
		{
			if ([aViewController isKindOfClass:[UINavigationController class]])
			{
				[(UINavigationController *)aViewController popToRootViewControllerAnimated:NO];
			}
		}
	}
	
	[aTabBarController.moreNavigationController popToRootViewControllerAnimated:NO];
}

- (void) viewDidLoad
{
	NSMutableArray	 *controllers = [NSMutableArray array];
	NSMutableDictionary	*keys = [NSMutableDictionary dictionary];
	UIViewController	*view;
	NSString	 *title;
	NSArray	 *order;
	
	order = (id) CFPreferencesCopyAppValue((CFStringRef) @"MemoryTabControllerViews", 
										   kCFPreferencesCurrentApplication);
	if (order) {
		for (view in self.viewControllers)
			if (view.tabBarItem.title)
				[keys setObject: view forKey: view.tabBarItem.title];
		for (title in order)
			[controllers addObject: [keys objectForKey: title]];
		for (view in self.viewControllers)
			if (! [controllers containsObject: view])
				[controllers addObject: view];
		[self setViewControllers: controllers];
		[order release];
	}
}

- (void) tabBarController: (UITabBarController *) tabBarController
didEndCustomizingViewControllers: (NSArray *) viewControllers changed: (BOOL) changed
{
	NSMutableArray	 *array = [NSMutableArray array];
	UIViewController	*viewController;
	
	for (viewController in viewControllers)
	{
		if ([viewController.tabBarItem.title length])
			[array addObject: viewController.tabBarItem.title];
		else {
			NSLog(@"TabBarController cannot save customization unless every item has a title.");
			return;
		}
	}
		
	CFPreferencesSetAppValue((CFStringRef) @"MemoryTabControllerViews", array, 
							 kCFPreferencesCurrentApplication);
	CFPreferencesAppSynchronize(kCFPreferencesCurrentApplication);
}

- (void)tabBar:(UITabBar *)tabBar willBeginCustomizingItems:(NSArray *)items
{
	UINavigationBar *nav = (UINavigationBar *) [[(UIView *) [self.view.subviews objectAtIndex:2] subviews] objectAtIndex:0];
	nav.barStyle = UIBarStyleBlackOpaque;
}


@end
