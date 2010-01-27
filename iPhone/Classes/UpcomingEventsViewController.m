//
//  UpcomingEventsViewController.m
//  iPhone
//
//  Created by Ryan Schneider on 4/28/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "UpcomingEventsViewController.h"
#import "UpcomingEventsSortedByTimeDataSource.h"
#import "RestController.h"
#import "iPhoneAppDelegate.h"
#import "DateUtilities.h"

@implementation UpcomingEventsViewController

/*
 // The designated initializer.  Override if you create the controller programmatically and want to perform customization that is not appropriate for viewDidLoad.
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
   self.dataSource = [UpcomingEventsSortedByTimeDataSource dataSource];
   [super viewDidLoad];
}

/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
*/

- (void)didReceiveMemoryWarning {
	// Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
	
	// Release any cached data, images, etc that aren't in use.
}

- (void)viewDidUnload {
	// Release any retained subviews of the main view.
	// e.g. self.myOutlet = nil;
}


- (void)dealloc {
    [super dealloc];
}

- (IBAction) refresh {
    RestController *restController = [iPhoneAppDelegate RestController];
	[restController beginGetEventsFrom: [DateUtilities today] until: [DateUtilities daysFromNow: 14]];
    restController.delegate = self;
}

- (void) restController: (RestController*) controller
        didRetrieveData: (NSData*) data 
          forRestClient: (RestClient*) client {  
    [self.dataSource refresh];
    [self.tableView reloadData];
}

- (void) restController: (RestController*) controller
       didFailWithError: (NSError*) error
          forRestClient: (RestClient*) client {
    if(client == controller.consolidatedClient) {
        [iPhoneAppDelegate displayConnectionError: error ];
    }
}

@end
