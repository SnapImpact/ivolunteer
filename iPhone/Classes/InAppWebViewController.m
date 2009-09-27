//
//  InAppWebViewController.m
//  insideredboxlite
//
//  Created by Hassan Abdel-Rahman on 7/11/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "InAppWebViewController.h"


@implementation InAppWebViewController


@synthesize webView_;
@synthesize busyIndicatorDelegate;
@synthesize url;
@synthesize loadThread;

- (void)displayLoadingMessage
{
	NSAutoreleasePool * pool = [[NSAutoreleasePool alloc] init];
	[busyIndicatorDelegate startAnimatingWithButton:NO label:@"Loading..."];
	[pool drain];
}

- (void)webViewDidStartLoad:(UIWebView *)webView
{
	self.loadThread = [[NSThread alloc] initWithTarget:self 
											  selector:@selector(displayLoadingMessage) 
												object:nil];
	[self.loadThread start];
}

- (void)webViewDidFinishLoad:(UIWebView *)webView
{
	[busyIndicatorDelegate stopAnimating];
}

- (void)viewDidLoad 
{
	if (self.url)
	{
		NSURL *url_ = [[NSURL alloc] initWithString:self.url];
		NSURLRequest *request = [[NSURLRequest alloc] initWithURL:url_
													  cachePolicy:NSURLRequestUseProtocolCachePolicy
												  timeoutInterval:60.0];
		[self.webView_ loadRequest:request];
		[url_ release];
		[request release];
	}
	
    [super viewDidLoad];
}

- (IBAction) back
{
	[self.webView_ goBack];
}

- (IBAction) forward
{
	[self.webView_ goForward];
}

- (IBAction) reload
{
	[self.webView_ reload];
}

- (IBAction) stop
{
	[self.webView_ stopLoading];
	[busyIndicatorDelegate stopAnimating];
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


- (void)dealloc {
	[url release];
	[webView_ release];
	[(NSObject *)busyIndicatorDelegate release];
	[loadThread release];
	
    [super dealloc];
}


@end
