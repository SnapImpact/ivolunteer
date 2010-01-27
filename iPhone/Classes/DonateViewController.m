//
//  DonateViewController.m
//  iPhone
//
//  Created by Hassan Abdel-Rahman on 1/19/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import "DonateViewController.h"
#import "iPhoneAppDelegate.h"

@implementation DonateViewController

@synthesize donateTable;
@synthesize donationProductList;
@synthesize productNames;

#define kTier1ProductId @"org.snapimpact.donations.tier1Donation"
#define kTier3ProductId @"org.snapimpact.donations.tier3Donation"
#define kTier8ProductId @"org.snapimpact.donations.tier8Donation"


// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
	
	// TODO: disable payment + display message if SKPaymentQueue.canMakePayments  = NO
	
	productNames = [[NSDictionary alloc] initWithObjectsAndKeys:
					@"SnapImpact $0.99 Donation", kTier1ProductId,
					@"SnapImpact $2.99 Donation", kTier3ProductId,
					@"SnapImpact $7.99 Donation", kTier8ProductId,
					nil];
	SKPaymentQueue *queue = [SKPaymentQueue defaultQueue];
	[queue addTransactionObserver:self];
	
	if (![SKPaymentQueue canMakePayments])
	{
		inappDisabled = YES;
	}
	
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
	if (inappDisabled)
	{
		UIAlertView *alert = [[UIAlertView alloc] initWithTitle: @"In-App Purchase Disabled"
														message: @"Your current restrictions prevent you from making an In-App purchase."
													   delegate:nil 
											  cancelButtonTitle:NSLocalizedString(@"Ok", @"Ok")
											  otherButtonTitles:nil];
		[alert show];
		[alert release];
	}
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
	cell.textLabel.text =[productNames objectForKey:product.productIdentifier];
    return cell;
}


- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
	if (inappDisabled)
	{
		[self.donateTable deselectRowAtIndexPath:indexPath animated:NO];
		return;
	}
	NSInteger row = indexPath.row;
	SKProduct *product = [donationProductList objectAtIndex:row];
	SKPayment *payment = [SKPayment paymentWithProductIdentifier:product.productIdentifier];
	SKPaymentQueue *queue = [SKPaymentQueue defaultQueue];
	[queue addPayment:payment];

	selectedRow = indexPath;
	
	iPhoneAppDelegate *app = (iPhoneAppDelegate *)[UIApplication sharedApplication].delegate;
	[app startAnimatingWithMessage:@"Please wait..." atBottom:YES];
}

- (void)paymentQueue:(SKPaymentQueue *)queue updatedTransactions:(NSArray *)transactions
{
	iPhoneAppDelegate *app = (iPhoneAppDelegate *)[UIApplication sharedApplication].delegate;
	[app stopAnimating];
	[self.donateTable deselectRowAtIndexPath:selectedRow animated:YES];
	for (SKPaymentTransaction *transaction in transactions)
	{
		if (transaction.transactionState == SKPaymentTransactionStatePurchased)
		{
			[queue finishTransaction:transaction];
		}
	}
}



- (void)dealloc {
	if (selectedRow)
	{
		[selectedRow release];
	}
	[productNames release];
	[donationProductList release];
	[donateTable release];
    [super dealloc];
}


@end
