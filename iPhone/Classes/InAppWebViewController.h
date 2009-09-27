//
//  InAppWebViewController.h
//  insideredboxlite
//
//  Created by Hassan Abdel-Rahman on 7/11/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "BusyIndicatorDelegate.h"

@interface InAppWebViewController : UIViewController <UIWebViewDelegate>{
	UIWebView * webView_;
	id<BusyIndicatorDelegate> busyIndicatorDelegate;
	NSString *url;
	NSThread *loadThread;
}
@property (nonatomic, retain) NSString *url;
@property (nonatomic, retain) IBOutlet UIWebView * webView_;
@property (nonatomic, retain) IBOutlet id<BusyIndicatorDelegate> busyIndicatorDelegate;
@property (nonatomic, retain) NSThread *loadThread;

- (IBAction) back;
- (IBAction) forward; 
- (IBAction) reload; 
- (IBAction) stop;

@end
