//
//  EventDetailsTableViewController.m
//  iPhone
//
//  Created by Ryan Schneider on 3/1/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "EventDetailsTableViewController.h"


@implementation EventDetailsTableViewController

@synthesize event;
@synthesize headerCell;
@synthesize headerActions;

+ (id) viewWithEvent: (Event*) event
{
   EventDetailsTableViewController* view = [[[EventDetailsTableViewController alloc] initWithStyle: UITableViewStyleGrouped ] autorelease ];
   view.event = event;
   return view;
}

- (void) setEvent: (Event*) event_ 
{
   if(event != nil)
      [event release];
   
   event = [event_ retain];
   self.headerCell.event = event;
   [self.tableView reloadData];
}

- (id)initWithStyle:(UITableViewStyle)style {
    // Override initWithStyle: if you create the controller programmatically and want to perform customization that is not appropriate for viewDidLoad.
    if (self = [super initWithStyle:style]) {
       NSArray* selectors = [NSArray arrayWithObjects:
                              [NSValue valueWithPointer: @selector(signUp)],
                              [NSValue valueWithPointer: @selector(share)],
                              nil
                             ];
       
       NSArray* titles = [NSArray arrayWithObjects: 
                              NSLocalizedString( @"Sign Up", nil),
                              NSLocalizedString( @"Share", nil),
                              nil
                          ];
       
       NSArray* arguments = [NSArray arrayWithObjects: [NSNull null], [NSNull null], nil ];
       self.headerActions = [ActionsView viewWithTarget: self selectors: selectors titles: titles arguments: arguments ];       
    }
    return self;
}

/*
- (void)viewDidLoad {
    [super viewDidLoad];

    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    // self.navigationItem.rightBarButtonItem = self.editButtonItem;
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
	[super viewWillDisappear:animated];
}
*/
/*
- (void)viewDidDisappear:(BOOL)animated {
	[super viewDidDisappear:animated];
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

#pragma mark Table view methods

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 4;
}


// Customize the number of rows in the table view.
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
   switch(section) {
      case 0: //header
         return 1;
      case 1: //description
         return 1;
      case 2: //contact details: location, contact, call, mail, source
         return 5;
      case 3: //interestAreas
      {
         NSUInteger areas = [self.event.interestAreas count];
         if( areas > 1 ) {
            return areas + 1;
         }
         //if only 1 area, we'll put it on the same row as header
         return 1;
      }
   }
   
   return 0;
}


// Customize the appearance of table view cells.
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
   
   NSUInteger row = indexPath.row;
   NSUInteger section = indexPath.section;
   
   //header cell
   if( section == 0 && row == 0 ) { 
      EventDetailsHeaderCell* header = (EventDetailsHeaderCell*) [tableView dequeueReusableCellWithIdentifier: [EventDetailsHeaderCell reuseIdentifier]];
      if( header == nil ) {
         NSArray *nib = [[NSBundle mainBundle] loadNibNamed: @"EventDetailsHeaderCell" owner: self options: nil ];
         header = [nib objectAtIndex:0];
      }
      
      header.event = self.event;
      return header;
   }
   
   static NSString *CellIdentifier = @"Cell";
   
   UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
   if (cell == nil) {
      cell = [[[UITableViewCell alloc] initWithFrame:CGRectZero reuseIdentifier:CellIdentifier] autorelease];
   }
   
   switch(section) {
      case 1:
         cell.text = self.event.details;
         break;
      case 2:
         switch(row) {
            case 0:
               cell.text = self.event.location.street;
               break;
            case 1:
               cell.text = self.event.contact.name;
               break;
            case 2:
               cell.text = self.event.contact.phone;
               break;
            case 3:
               cell.text = self.event.contact.email;
               break;
            case 4:
               cell.text = self.event.source.name;
               break;
         }
         break;
      case 3:
         if( [self.event.interestAreas count] > 1 ) {
            if( row == 0) 
               cell.text = @"Interest Areas";
            else
               cell.text = [[self.event.interestAreas objectAtIndex: (row - 1)] name];
         }
         else {
            cell.text = [[self.event.interestAreas objectAtIndex: row ] name];
         }
         break;
   }
   
   // Set up the cell...
   return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
   NSUInteger row = indexPath.row;
   NSUInteger section = indexPath.section;
   
   if( section == 0 && row == 0 ) {
      return [EventDetailsHeaderCell height];
   }
   
   return self.tableView.rowHeight;
}

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section
{
   if( section == 0)
      return self.headerActions;
   
   return nil;
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
{
   return [self.headerActions height] + self.tableView.sectionFooterHeight;
}


- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    // Navigation logic may go here. Create and push another view controller.
	// AnotherViewController *anotherViewController = [[AnotherViewController alloc] initWithNibName:@"AnotherView" bundle:nil];
	// [self.navigationController pushViewController:anotherViewController];
	// [anotherViewController release];
}


/*
// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath {
    // Return NO if you do not want the specified item to be editable.
    return YES;
}
*/


/*
// Override to support editing the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath {
    
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        // Delete the row from the data source
        [tableView deleteRowsAtIndexPaths:[NSArray arrayWithObject:indexPath] withRowAnimation:YES];
    }   
    else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
    }   
}
*/


/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath {
}
*/


/*
// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath {
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}
*/


- (void)dealloc {
    [super dealloc];
}


@end

