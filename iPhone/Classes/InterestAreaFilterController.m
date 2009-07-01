//
//  InterestAreaFilterController.m
//  iPhone
//
//  Created by Ryan Schneider on 5/12/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "InterestAreaFilterController.h"
#import "iVolunteerData.h"

@implementation InterestAreaFilterController

@synthesize allInterestAreas;
@synthesize tableView;
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
    self.selectedInterestAreas = [[[NSMutableArray alloc] initWithArray:[InterestArea loadInterestAreasFromPreferences]] autorelease];
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

- (void) toggleInterestArea:(InterestArea*)interestArea {
    if( [self.selectedInterestAreas containsObject: interestArea] ) {
        [self.selectedInterestAreas removeObject: interestArea ];
    }
    else {
        [self.selectedInterestAreas addObject: interestArea ];
    }
}


- (void)dealloc {
    self.selectedInterestAreas = nil;
    self.allInterestAreas = nil;
    self.tableView = nil;
    [super dealloc];
}

- (IBAction) save {
}

- (InterestArea*) interestAreaForIndexPath: (NSIndexPath*) indexPath {
    NSUInteger row = indexPath.row;
    NSUInteger section = indexPath.section;
    
    NSLog(@"Getting interestArea(%d:%d)", section, row);
    
    if(section != 0) 
        return nil;
    
    return (InterestArea*)[self.allInterestAreas objectAtIndex: row];
}

- (UITableViewCell *)tableView:(UITableView *)tableView_ cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    static NSString *CellIdentifier = @"Cell";
    UITableViewCell *cell = [tableView_ dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[[UITableViewCell alloc] initWithFrame:CGRectZero reuseIdentifier:CellIdentifier] autorelease];
    }
    
    // Set up the cell...	
    InterestArea* interestArea = [self interestAreaForIndexPath: indexPath];
    cell.text = interestArea.name;
    if( [self.selectedInterestAreas containsObject: interestArea] ) {
        cell.accessoryType = UITableViewCellAccessoryCheckmark;
    }
    else {
        cell.accessoryType = UITableViewCellAccessoryNone;
    }
    
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 45;
}

- (void)tableView:(UITableView *)tableView_ didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell* cell = (UITableViewCell*) [self tableView: tableView_ cellForRowAtIndexPath: indexPath];
    InterestArea* interestArea = [self interestAreaForIndexPath: indexPath];
	BOOL didChange = YES;
    if( [self.selectedInterestAreas containsObject: interestArea] ) {
		if ([self.selectedInterestAreas count] > 1)
		{
			cell.accessoryType = UITableViewCellAccessoryNone;
			[self.selectedInterestAreas removeObject: interestArea];
		}
		else
		{
			didChange = NO;
			UIAlertView* alert = [[UIAlertView alloc] initWithTitle: @"Cannot Remove All Interests" 
															message: @"You must have at least one interest selected." 
														   delegate: nil 
												  cancelButtonTitle: @"Ok" 
												  otherButtonTitles: nil];
			[alert show];
			[alert release];
		}
        
    }
    else {
        cell.accessoryType = UITableViewCellAccessoryCheckmark;
        [self.selectedInterestAreas addObject: interestArea];
    }   
	
	if (self.selectedInterestAreas && didChange)
	{
		[InterestArea saveInterestAreasToPreferences:self.selectedInterestAreas];
		[tableView_ reloadData];
	}
	
	[tableView_ deselectRowAtIndexPath: indexPath animated: NO];
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    if (self.allInterestAreas) {
        NSUInteger len = [self.allInterestAreas count];
        NSLog(@"Rows: %d", len);
        return len;
    }
    return 0;
}

@end



