//
//  InterestAreaFilterController.m
//  iPhone
//
//  Created by Ryan Schneider on 5/12/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "InterestAreaFilterController.h"
#import "InterestAreaFilterTableViewCell.h"
#import "iVolunteerData.h"

@implementation InterestAreaFilterController

@synthesize allInterestAreas;
@synthesize view;
@synthesize selectedInterestAreas;

/*
 // The designated initializer.  Override if you create the controller programmatically and want to perform customization that is not appropriate for viewDidLoad.
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        // Custom initialization
    }
    return self;
}
*/


// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
    self.allInterestAreas = [[iVolunteerData sharedVolunteerData] interestAreasByName];
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

- (void) toggleInterestArea:(InterestArea *)interestArea
{
}


- (void)dealloc {
    self.selectedInterestAreas = nil;
    self.allInterestAreas = nil;
    self.view = nil;
    [super dealloc];
}

- (IBAction) save {
}

- (InterestArea*) interestAreaForIndexPath: (NSIndexPath*) indexPath {
    NSUInteger row = indexPath.row;
    NSUInteger section = indexPath.section;
    
    if(section != 0) 
        return nil;
    
    return (InterestArea*)[self.allInterestAreas objectAtIndex: row];
}

- (UITableViewCell *)tableView:(UITableView *)tableView_ cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    static NSString* reuseIdentifier = @"InterestAreaFilters";
    InterestAreaFilterTableViewCell *cell = (InterestAreaFilterTableViewCell*)[tableView_ dequeueReusableCellWithIdentifier: reuseIdentifier ];
    if (cell == nil) {
        cell = [[[InterestAreaFilterTableViewCell alloc] initWithFrame:CGRectZero reuseIdentifier:reuseIdentifier] autorelease];
    }
    
    cell.interestArea = [self interestAreaForIndexPath: indexPath];
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 44;
}

- (void)tableView:(UITableView *)tableView_ didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    InterestAreaFilterTableViewCell* cell = (InterestAreaFilterTableViewCell*) [self tableView: tableView_ cellForRowAtIndexPath: indexPath];
    [cell toggle];
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    if (self.allInterestAreas) {
        return [self.allInterestAreas count];
    }
    return 0;
}

@end



