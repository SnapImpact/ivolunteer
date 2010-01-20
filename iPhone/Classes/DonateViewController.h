//
//  DonateViewController.h
//  iPhone
//
//  Created by Hassan Abdel-Rahman on 1/19/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <StoreKit/StoreKit.h>

@interface DonateViewController : UIViewController <UITableViewDelegate, UITableViewDataSource, SKProductsRequestDelegate> {

	UITableView *donateTable;
	NSArray *donationProductList;
}

@property(nonatomic, retain) IBOutlet UITableView *donateTable;
@property(nonatomic, retain) NSArray *donationProductList;
@end
