//
//  EventDetailsTableViewController.m
//  iPhone
//
//  Created by Ryan Schneider on 3/1/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "EventDetailsTableViewController.h"
#import "AttributeCell.h"


@implementation EventDetailsTableViewController

@synthesize event;
@synthesize headerCell;
@synthesize headerActions;
@synthesize descriptionCell;
@synthesize descriptionSize;
@synthesize smallFont;
@synthesize mediumFont;
@synthesize largeFont;

#pragma mark Constants
#define kSectionsCount 4

#define kSectionDetailsHeader 0
#define kSectionDetailsHeaderRowCount 1
#define kSectionDetailsHeaderRow 0

#define kSectionDescription 1
#define kSectionDescriptionRowCount 1
#define kSectionDescriptionRow 0

#define kSectionContactInfo 2
#define kSectionContactInfoRowCount 5
#define kSectionContactInfoRowName 0
#define kSectionContactInfoRowAddress 1
#define kSectionContactInfoRowPhone 2
#define kSectionContactInfoRowEmail 3
#define kSectionContactInfoRowSource 4

#define kSectionInterestAreas 3
#define kSectionInterestAreasRowCount #error not supported

#pragma mark Allocator

+ (id) viewWithEvent: (Event*) event
{
   EventDetailsTableViewController* view = [[[EventDetailsTableViewController alloc] initWithStyle: UITableViewStyleGrouped ] autorelease ];
   view.event = event;
   return view;
}

#pragma mark Property Override

- (void) setEvent: (Event*) event_ 
{
   if(event != nil)
      [event release];
   
   event = [event_ retain];
   self.headerCell.event = event;
   self.navigationItem.title = event.name;
   CGFloat width = [[UIScreen mainScreen] bounds].size.width;
   width -= (self.tableView.sectionHeaderHeight * 4);
   self.descriptionSize = CGSizeMake( width, self.tableView.rowHeight);
   [self.tableView reloadData];
}

#pragma mark UITableViewController methods

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
       
       self.smallFont = [UIFont systemFontOfSize: 14 ];
       self.mediumFont = [UIFont systemFontOfSize: 16 ];
       self.largeFont = [UIFont systemFontOfSize: 18 ];
       
       //let's go ahead and build descriptionCell, makes life easier
       self.descriptionCell = [[[UITableViewCell alloc] initWithFrame: CGRectZero reuseIdentifier: @"Description" ] autorelease];
       self.descriptionCell.font = self.smallFont;
       self.descriptionCell.lineBreakMode = UILineBreakModeWordWrap;
       UITextView* text = [[[UITextView alloc] init] autorelease ];
       text.tag = 303;
       text.font = self.smallFont;
       text.editable = NO;
       [[self.descriptionCell contentView] addSubview: text ];
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
      case kSectionDetailsHeader:
         return kSectionDetailsHeaderRowCount;
      case kSectionDescription:
         return kSectionDescriptionRowCount;
      case kSectionContactInfo:
         return kSectionContactInfoRowCount;
      case kSectionInterestAreas:
         return [self.event.interestAreas count];
   }
   
   NSAssert( NO, @"Invalid section. ");
   return 0;
}

- (UITableViewCell*) cellForDetailsHeader: (NSUInteger) row
{
   NSAssert( row == kSectionDetailsHeaderRow, @"Only coded to support one row in DetailsHeader section!");
   
   EventDetailsHeaderCell* header = (EventDetailsHeaderCell*) [self.tableView dequeueReusableCellWithIdentifier: [EventDetailsHeaderCell reuseIdentifier]];
   if( header == nil ) {
      NSArray *nib = [[NSBundle mainBundle] loadNibNamed: @"EventDetailsHeaderCell" owner: self options: nil ];
      header = [nib objectAtIndex:0];
   }
   
   header.event = self.event;
   return header;
}

- (UITableViewCell*) cellForDescription: (NSUInteger) row 
{
   return self.descriptionCell;
}

- (UITableViewCell*) cellForContactInfoRow:(NSInteger) row 
{
   AttributeCell* cell = [[[AttributeCell alloc] initWithFrame:[UIScreen mainScreen].applicationFrame
                                               reuseIdentifier:nil] autorelease];
   
   NSString* mapString = NSLocalizedString(@"Map", nil);
   NSString* callString = NSLocalizedString(@"Call", nil);
   NSString* mailString = NSLocalizedString(@"Mail", nil);
   NSString* contactString = NSLocalizedString(@"Contact", nil);
   NSString* sourceString = NSLocalizedString(@"Source", nil);
   //align it with interest areas section as well
   NSString* interestAreaString;
   if( [self.event.interestAreas count] > 1 ) {
      interestAreaString = NSLocalizedString( @"Interest Areas", @"plural of Interest Area" );
   }
   else {
      interestAreaString = NSLocalizedString( @"Interest Area", nil );
   }
      
   CGSize size1 = [mapString sizeWithFont:[AttributeCell keyFont]];
   CGSize size2 = [callString sizeWithFont:[AttributeCell keyFont]];
   CGSize size3 = [mailString sizeWithFont:[AttributeCell keyFont]];
   CGSize size4 = [contactString sizeWithFont:[AttributeCell keyFont]];
   CGSize size5 = [sourceString sizeWithFont:[AttributeCell keyFont]];
   CGSize size6 = [interestAreaString sizeWithFont:[AttributeCell keyFont]];
   
   NSInteger width = MAX(size1.width, size2.width);
   width = MAX(width, size3.width);
   width = MAX(width, size4.width);
   width = MAX(width, size5.width);
   width = MAX(width, size6.width);
   width += 10;
   
   switch(row) {
      case kSectionContactInfoRowName:
         [cell setKey: contactString value: self.event.contact.name keyWidth:width];
         break;
      case kSectionContactInfoRowAddress:
         [cell setKey:mapString value: self.event.location.street keyWidth:width];
         break;
      case kSectionContactInfoRowPhone:
         [cell setKey: callString value: self.event.contact.phone keyWidth: width ];
         break;
      case kSectionContactInfoRowEmail:
         [cell setKey: mailString value: self.event.contact.email keyWidth: width ];
         break;
      case kSectionContactInfoRowSource:
         [cell setKey: sourceString value: self.event.source.name keyWidth: width ];
         break;
   }
   
   return cell;
}

- (UITableViewCell*) cellForInterestArea:(NSInteger) row 
{
   NSString* interestAreaString;
   if( [self.event.interestAreas count] > 1 ) {
      interestAreaString = NSLocalizedString( @"Interest Areas", @"plural of Interest Area" );
   }
   else {
      interestAreaString = NSLocalizedString( @"Interest Area", nil );
   }
   
   AttributeCell* cell = [[[AttributeCell alloc] initWithFrame:[UIScreen mainScreen].applicationFrame
                                               reuseIdentifier: nil ] autorelease];

   CGSize size = [interestAreaString sizeWithFont:[AttributeCell keyFont]];
   size.width += 10;
   
   NSString* value = [[self.event.interestAreas objectAtIndex: row] name];
   
   if( row == 0 ) {
      [cell setKey: interestAreaString value: value keyWidth: size.width ];
   }
   else {
      [cell setKey: @"" value: value keyWidth: size.width ];
   }
   
   return cell;
}

// Customize the appearance of table view cells.
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
   
   NSUInteger row = indexPath.row;
   NSUInteger section = indexPath.section;
   
   switch(section) {
      case 0:
         return [self cellForDetailsHeader: row ];
      case 1:
         return [self cellForDescription: row ];
      case 2:
         return [self cellForContactInfoRow: row ];
      case 3:
         return [self cellForInterestArea: row ];
   }   
   
   NSString* err = [ NSString stringWithFormat: @"Unrecognized indexPath: section %d, row %d.", section, row]; 
   NSAssert( NO, err );
   return nil;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
   NSUInteger row = indexPath.row;
   NSUInteger section = indexPath.section;
   
   if( section == kSectionDetailsHeader) {
      NSAssert( row == kSectionDetailsHeaderRow, @"Unsupported Row Id for DetailsHeader" );
      return [EventDetailsHeaderCell height];
   }
   else if ( section == kSectionDescription ) {
      NSAssert( row == kSectionDescriptionRow, @"Unsupported Row Id for Description" );
      NSString* str = self.event.details;
      CGSize size = [ str sizeWithFont: self.smallFont 
                     constrainedToSize: CGSizeMake(self.descriptionSize.width - 20, 1000 ) 
                         lineBreakMode: UILineBreakModeWordWrap ];
      
      self.descriptionSize = CGSizeMake(self.descriptionSize.width, size.height);
      UITextView* text = (UITextView*)[self.descriptionCell viewWithTag: 303 ];
      text.frame = CGRectMake( 10, 0, self.descriptionSize.width, size.height + 10 );
      text.text = str;
      return text.frame.size.height + 11;
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
   if( section == kSectionDetailsHeader ) {
      CGFloat standardFooter = self.tableView.sectionFooterHeight;
      return [self.headerActions height] + standardFooter;
   }
   
   return 0;
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

