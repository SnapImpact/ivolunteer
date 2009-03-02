//
//  EventDetailsViewController.m
//  iPhone
//
//  Created by Ryan Schneider on 2/26/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "EventDetailsViewController.h"


@implementation EventDetailsViewController

@synthesize event;

+ (id) viewWithEvent: (Event*) event
{
   EventDetailsViewController* view = [[[EventDetailsViewController alloc] initWithNibName: @"EventDetails" bundle: nil ] autorelease ];
   view.event = event;
   return view;
}

- (id) init
{
   if( self = [super init] ) {
      self.event = nil;
   }
   return self;
}


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

/*
// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
}
*/

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
   self.event = nil;
}


@end
