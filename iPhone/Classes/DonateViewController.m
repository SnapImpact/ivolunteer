//
//  DonateViewController.m
//  iPhone
//
//  Created by Hassan Abdel-Rahman on 1/19/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import "DonateViewController.h"


@implementation DonateViewController

@synthesize donateTable;
@synthesize donationProductList;

#define kTier1ProductId @"org.snapimpact.donations.tier1Donation"
#define kTier3ProductId @"org.snapimpact.donations.tier3Donation"
#define kTier8ProductId @"org.snapimpact.donations.tier8Donation"


// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
	NSSet *donationProductIdList = [NSSet setWithObjects:kTier1ProductId, kTier3ProductId, kTier8ProductId, nil];
	SKProductsRequest *prodRequest = [[SKProductsRequest alloc] initWithProductIdentifiers:donationProductIdList];
	prodRequest.delegate = self;
	[prodRequest start];
	 
    [super viewDidLoad];
}


- (void)didReceiveMemoryWarning {
	// Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
	
	// Release any cached data, images, etc that aren't in use.
}

- (void)viewDidUnload {
	// Release any retained subviews of the main view.
	// e.g. self.myOutlet = nil;
}

- (void) viewDidAppear:(BOOL)animated
{

	[super viewDidAppear:animated];
}

#pragma mark storekit methods

- (void)productsRequest:(SKProductsRequest *)request didReceiveResponse:(SKProductsResponse *)response
{
	donationProductList = [[NSArray alloc] initWithArray:response.products];
	
	//TODO: refresh table
}

#pragma mark Table view methods

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}


// Customize the number of rows in the table view.
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    if (donationProductList)
	{
		return [donationProductList count];
	}
	else
	{
		return 0; // product list is aynchronously obtained--no guarantee that it is available when page is rendered
	}

}


// Customize the appearance of table view cells.
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    static NSString *CellIdentifier = @"Cell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier] autorelease];
    }
    
    // Set up the cell...
	NSInteger row = indexPath.row;
	SKProduct *product = [donationProductList objectAtIndex:row];
	cell.textLabel.text = product.localizedTitle;
    return cell;
}


- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    // Navigation logic may go here. Create and push another view controller.
	// AnotherViewController *anotherViewController = [[AnotherViewController alloc] initWithNibName:@"AnotherView" bundle:nil];
	// [self.navigationController pushViewController:anotherViewController];
	// [anotherViewController release];
}




- (void)dealloc {
	[donationProductList release];
	[donateTable release];
    [super dealloc];
}


@end
