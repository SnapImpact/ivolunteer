//
//  EventsViewController.m
//  iPhone
//
//  Created by Dave Angulo on 10/18/08.
//  Copyright __MyCompanyName__ 2008. All rights reserved.
//

#import "EventsViewController.h"
#import "iPhoneAppDelegate.h"

#import "RootViewDataSourceProtocol.h"
#import "EventTableCell.h"


@implementation EventsViewController

@synthesize refreshButton;
@synthesize detailsController;
@synthesize dataSource;
@synthesize busyIndicatorDelegate;

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
   return 1;
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
   return 1;
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
   
   static NSString *CellIdentifier = @"Cell";
   
   UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
   if (cell == nil) {
      cell = [[[UITableViewCell alloc] initWithFrame:CGRectZero reuseIdentifier:CellIdentifier] autorelease];
   }
   
   // Set up the cell
   cell.text = @"Loading.."; //[[NSString alloc] initWithFormat:@"%i",[indexPath indexAtPosition:1]];
   return cell;
}


- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
   //forward to the accessory button
   [self tableView: tableView accessoryButtonTappedForRowWithIndexPath: indexPath ];
   [self.tableView deselectRowAtIndexPath:indexPath animated:YES];
}

- (void) tableView: (UITableView*) tableView accessoryButtonTappedForRowWithIndexPath: (NSIndexPath*) indexPath
{      
    Event *event = (Event *)[self.dataSource objectForIndexPath: indexPath ];
    if(!event) {
        return;
    }
    if( self.detailsController == nil ) {
        self.detailsController = [EventDetailsTableViewController viewWithEvent: event ];
    }
    else {
        self.detailsController.event = event;
    }
	self.detailsController.busyIndicatorDelegate = self.busyIndicatorDelegate;
	
    [self.navigationController pushViewController:self.detailsController animated: YES];
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
   return [ EventTableCell height];
}

- (void)viewDidLoad {
   self.detailsController = nil;
   [[self tableView] setDataSource: self.dataSource ];
   [super viewDidLoad];
}


/*
 // Override to support editing the list
 - (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath {
 
 if (editingStyle == UITableViewCellEditingStyleDelete) {
 // Delete the row from the data source
 [tableView deleteRowsAtIndexPaths:[NSArray arrayWithObject:indexPath] withRowAnimation:YES];
 }   
 if (editingStyle == UITableViewCellEditingStyleInsert) {
 // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
 }   
 }
 */


/*
 // Override to support conditional editing of the list
 - (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath {
 // Return NO if you do not want the specified item to be editable.
 return YES;
 }
 */


/*
 // Override to support rearranging the list
 - (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath {
 }
 */


/*
 // Override to support conditional rearranging of the list
 - (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath {
 // Return NO if you do not want the item to be re-orderable.
 return YES;
 }
 */

/*
 - (void)viewWillAppear:(BOOL)animated {
 [super viewWillAppear:animated];
 }
 */
/*
 - (void)viewDidAppear:(BOOL)animated {
 [super viewDidAppear:animated];
 }
 */
/*
 - (void)viewWillDisappear:(BOOL)animated {
 }
 */
/*
 - (void)viewDidDisappear:(BOOL)animated {
 }
 */

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
   // Return YES for supported orientations
   return (interfaceOrientation == UIInterfaceOrientationPortrait);
}


- (void)didReceiveMemoryWarning {
   [super didReceiveMemoryWarning]; // Releases the view if it doesn't have a superview
   // Release anything that's not essential, such as cached data
}


- (void)dealloc {
	[refreshButton release];
	[detailsController release];
	[dataSource release];
	[(NSObject *) busyIndicatorDelegate release];
	[dataSource release];	
   [super dealloc];
}

#pragma mark -
#pragma mark LocationAvailabilityDelegate methods

- (void)locationIsAvailable:(CLLocation *)location
{
}


- (void) settings 
{
}

- (void) sort 
{
}

@end

