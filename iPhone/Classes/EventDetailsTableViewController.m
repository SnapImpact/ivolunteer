//
//  EventDetailsTableViewController.m
//  iPhone
//
//  Created by Ryan Schneider on 3/1/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "EventDetailsTableViewController.h"
#import "AttributeCell.h"
#import "iVolunteerData.h"

#import "MapViewController.h"

#import "StringUtilities.h"

@implementation EventDetailsTableViewController

@synthesize floatingView;
@synthesize event;
@synthesize headerCell;
@synthesize headerActions;
@synthesize smallFont;
@synthesize mediumFont;
@synthesize largeFont;
@synthesize signedUpString;
@synthesize signUpString;
@synthesize busyIndicatorDelegate;

#pragma mark Constants
#define kSectionsCount 4

#define kSectionDetailsHeader 0
#define kSectionDetailsHeaderRowCount 1
#define kSectionDetailsHeaderRow 0

#define kSectionDescription 1
#define kSectionDescriptionRowCount 1
#define kSectionDescriptionRow 0

#define kSectionContactInfo 2
#define kSectionContactInfoRowCount 4
#define kSectionContactInfoRowName 0
#define kSectionContactInfoRowPhone 1
#define kSectionContactInfoRowEmail 2
#define kSectionContactInfoRowSource 3

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
    
    if([event.signedUp boolValue]) {
        [ self.headerActions setTitle: signedUpString forButtonAtIndex: 0 selected: YES animate: NO ];
    }
    else {
        [ self.headerActions setTitle: signUpString forButtonAtIndex:0 selected:NO animate:NO ];
    }
    [self.tableView reloadData];
}

#pragma mark UITableViewController methods

- (void)viewDidLoad {
    
	self.signedUpString = NSLocalizedString( @"Registered!", @"Should be positive, yay you signed up!" );
	self.signUpString = NSLocalizedString( @"Register", @"Indicates clicking this button will sign you up" );
    
	NSArray* selectors = [NSArray arrayWithObjects:
						  [NSValue valueWithPointer: @selector(signUp)],
						  nil
						  ];
	
	NSArray* titles = [NSArray arrayWithObjects: 
                       self.signUpString,
					   nil
					   ];
	
	NSArray* arguments = [NSArray arrayWithObjects: [NSNull null], nil ];
	UIImage* buttonImage = [[UIImage imageNamed:@"whiteButton.png"] stretchableImageWithLeftCapWidth: 12.0 topCapHeight: 0 ];
	UIImage* buttonImagePressed = [[UIImage imageNamed:@"blueButton.png"] stretchableImageWithLeftCapWidth: 12.0 topCapHeight: 0 ];
	UIImage* buttonImageSelected = [[UIImage imageNamed:@"greenButton.png"] stretchableImageWithLeftCapWidth: 12.0 topCapHeight: 0 ];
	self.headerActions = [ActionsView viewWithTarget: self 
										   selectors: selectors 
											  titles: titles 
										   arguments: arguments 
											   image: buttonImage
										imagePressed: buttonImagePressed
									   imageSelected: buttonImageSelected
										   textColor: [UIColor darkTextColor]
								   textColorSelected: [UIColor whiteColor]];
    
    if([event.signedUp boolValue]) {
        [ self.headerActions setTitle: signedUpString forButtonAtIndex: 0 selected: YES animate: NO ];
    }
    else {
        [ self.headerActions setTitle: signUpString forButtonAtIndex:0 selected:NO animate:NO ];
    }
	
	self.smallFont = [UIFont systemFontOfSize: 14 ];
	self.mediumFont = [UIFont systemFontOfSize: 16 ];
	self.largeFont = [UIFont systemFontOfSize: 18 ];
	
	[super viewDidLoad];
}



- (void)viewWillAppear:(BOOL)animated {
	//let's go ahead and build descriptionCell, makes life easier
    
    [super viewWillAppear:animated];
}

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
    
    if( self.headerCell == nil ) {
        NSArray *nib = [[NSBundle mainBundle] loadNibNamed: @"EventDetailsHeaderCell" owner: self options: nil ];
        self.headerCell = [nib objectAtIndex:0];
    }
    
    self.headerCell.event = self.event;
    return self.headerCell;
}

- (UITableViewCell*) cellForDescription: (NSUInteger) row 
{
	int operatingWidth = [[UIScreen mainScreen] bounds].size.width - (self.tableView.sectionHeaderHeight * 4);
	UITableViewCell *descriptionCell_ = [[[UITableViewCell alloc] initWithFrame: CGRectZero reuseIdentifier: @"Description" ] autorelease];
	descriptionCell_.font = self.smallFont;
	descriptionCell_.lineBreakMode = UILineBreakModeTailTruncation;
	[descriptionCell_ setSelectionStyle: UITableViewCellSelectionStyleNone ];
	
	CGSize size = [self.event.details sizeWithFont: self.smallFont 
				   constrainedToSize: CGSizeMake(operatingWidth - 20, 1000 ) 
				   lineBreakMode: UILineBreakModeWordWrap ];
	UILabel *text = [[[UILabel alloc] init] autorelease];
	text.font = self.smallFont;
	text.text = self.event.details;
	text.frame = CGRectMake(20, 10, operatingWidth, size.height);
	text.numberOfLines = 0;
	[descriptionCell_ addSubview:text];
	
    return descriptionCell_;
}

- (UITableViewCell*) cellForContactInfoRow:(NSInteger) row 
{
    AttributeCell* cell = [[[AttributeCell alloc] initWithFrame:[UIScreen mainScreen].applicationFrame
                                                reuseIdentifier:nil] autorelease];
    
    //NSString* mapString = NSLocalizedString(@"Map", nil);
    NSString* callString = NSLocalizedString(@"Call", nil);
    NSString* mailString = NSLocalizedString(@"Email", nil);
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
    
    NSString* notAvailableString = @"Not Available";
    
    CGSize size1 = [callString sizeWithFont:[AttributeCell keyFont]];
    CGSize size2 = [mailString sizeWithFont:[AttributeCell keyFont]];
    CGSize size3 = [contactString sizeWithFont:[AttributeCell keyFont]];
    CGSize size4 = [sourceString sizeWithFont:[AttributeCell keyFont]];
    CGSize size5 = [interestAreaString sizeWithFont:[AttributeCell keyFont]];
    CGSize size6 = [notAvailableString sizeWithFont: [AttributeCell keyFont]];
    
    NSInteger width = MAX(size1.width, size2.width);
    width = MAX(width, size3.width);
    width = MAX(width, size4.width);
    width = MAX(width, size5.width);
    width = MAX(width, size6.width);
    width += 10;
    
    switch(row) {
        case kSectionContactInfoRowName:
            if([self.event.contact.name length]) {
                [cell setKey: contactString value: self.event.contact.name keyWidth:width];
            }
            else {
                [cell setKey: contactString value: notAvailableString keyWidth: width];
            }
            break;
        case kSectionContactInfoRowPhone:
            if([self.event.contact.phone length]) {
                [cell setKey: callString value: self.event.contact.phone keyWidth: width ];
            }
            else {
                [cell setKey: callString value: notAvailableString keyWidth: width ];
            }
            break;
        case kSectionContactInfoRowEmail:
            if([self.event.contact.email length]) {
                [cell setKey: mailString value: self.event.contact.email keyWidth: width ];
            }
            else {
                [cell setKey: mailString value: notAvailableString keyWidth: width ];
            }
            break;
        case kSectionContactInfoRowSource:
            if([self.event.source.name length]) {
                [cell setKey: sourceString value: self.event.source.name keyWidth: width ];
            }
            else {
                [cell setKey: sourceString value: notAvailableString keyWidth: width ];
            }
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
        int operatingWidth = [[UIScreen mainScreen] bounds].size.width - (self.tableView.sectionHeaderHeight * 4);
        CGSize size = [self.event.details sizeWithFont: self.smallFont 
                       constrainedToSize: CGSizeMake(operatingWidth - 20, 1000 ) 
                       lineBreakMode: UILineBreakModeWordWrap ];
        return size.height + 20;
    }
    /*
    else if (section == kSectionContactInfo) {
        switch(row) {
            case kSectionContactInfoRowName:
                if(![self.event.contact.name length]) {
                    return 0;
                }
                break;
            case kSectionContactInfoRowPhone:
                if(![self.event.contact.phone length]) {
                    return 0;
                }
                break;
            case kSectionContactInfoRowEmail:
                if(![self.event.contact.email length]) {
                    return 0;
                }
                break;
            case kSectionContactInfoRowSource:
                if(![self.event.source.name length]) {
                    return 0;
                }
                break;
        }
    }
    */
    
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

- (void) makeCall:(NSString*) phoneNumber {
    if (![[[UIDevice currentDevice] model] isEqual:@"iPhone"]) {
        return;
    }
    
    if((phoneNumber == nil) || ([phoneNumber length] == 0)) {
        return;
    }
    
    NSRange xRange = [phoneNumber rangeOfString:@"x"];
    if (xRange.length > 0 && xRange.location >= 12) {
        // 222-222-2222 x222
        // remove extension
        phoneNumber = [phoneNumber substringToIndex:xRange.location];
    }
    
    NSString* urlString = [NSString stringWithFormat:@"tel:%@", [StringUtilities stringByAddingPercentEscapes:phoneNumber]];
    
    [[UIApplication sharedApplication] openURL: [ NSURL URLWithString: urlString]];
}


- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    // Navigation logic may go here. Create and push another view controller.
	// AnotherViewController *anotherViewController = [[AnotherViewController alloc] initWithNibName:@"AnotherView" bundle:nil];
	// [self.navigationController pushViewController:anotherViewController];
	// [anotherViewController release];
    NSUInteger section = indexPath.section;
    NSUInteger row = indexPath.row;
    
    UIViewController* subView = nil;
    
    switch(section) {
        case kSectionDetailsHeader:
            break;
        case kSectionDescription:
            break;
        case kSectionContactInfo:
            switch(row) {
                case kSectionContactInfoRowName:
                    //add new contact?
                    break;
                case kSectionContactInfoRowPhone:
                    //call
                    @try {
                        [self makeCall: self.event.contact.phone];
                    }
                    @catch(...) {
                        /*
                        UIAlertView* alert = [[UIAlertView alloc ] initWithTitle:@"Simulator?" 
                                                                         message:@"Calling unsupported, are you on the simulator?" 
                                                                        delegate:nil
                                                               cancelButtonTitle:@"Yes" 
                                                               otherButtonTitles:@"No", nil  ];
                        [alert show];
                        [alert release];
                        */
                    }
                    break;
                case kSectionContactInfoRowEmail:
                    //email
                    @try {
                        [[UIApplication sharedApplication] openURL: [NSURL URLWithString: [NSString stringWithFormat: @"mailto:%@", self.event.contact.email ]]];
                    }
                    @catch(...) {
                        /*
                        UIAlertView* alert = [[UIAlertView alloc ] initWithTitle:@"Simulator?" 
                                                                         message:@"Mailto unsupported, are you on the simulator?" 
                                                                        delegate:nil
                                                               cancelButtonTitle:@"Yes" 
                                                               otherButtonTitles:@"No", nil  ];
                        [alert show];
                        [alert release];
                        */
                    }
                    break;
                case kSectionContactInfoRowSource:
                {
                    NSURL* url = nil;
                    if(self.event.url) {
                        url = self.event.url;
                    }
                    else {
                        url = self.event.source.url; 
                    }
                    if(url) {
                        [[UIApplication sharedApplication] openURL: url ];
                    }
                }   
                    break;
            }
            break;
        case kSectionInterestAreas:
            break;
    }
    
    if(subView) {
        [self.navigationController pushViewController:subView animated: YES];
        [subView release];
    }
    
    [self.tableView deselectRowAtIndexPath:indexPath animated:YES];
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
	[(NSObject *) busyIndicatorDelegate release];
	[event release];
	[headerCell release];
	[headerActions release];
	[smallFont release];
	[mediumFont release];
	[largeFont release];
	[signUpString release];
	[signedUpString release];
    self.floatingView = nil;
    [super dealloc];
}

#pragma mark Action Callbacks
- (void) signUp {
    if (![self.event.signedUp boolValue]) {
        UIWindow* window = [[UIApplication sharedApplication].windows objectAtIndex: 0];
        [RegisterConfirmationViewController displaySheetForEvent: self.event inWindow: window delegate: self];
    }
}

- (void) didConfirmRegistration {
    [self.headerActions setTitle:  self.signedUpString forButtonAtIndex: 0 selected: YES animate: YES ];
    self.event.signedUp = [NSNumber numberWithBool: YES];
    [[iVolunteerData sharedVolunteerData] updateMyEventsDataSource: self.event];
}

- (void) didCancelRegistration {
}

@end


