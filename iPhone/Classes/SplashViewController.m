//
//  SplashViewController.m
//  iPhone
//
//  Created by Aubrey Francois on 10/18/08.
//  Copyright 2008 __MyCompanyName__. All rights reserved.
//

#import "SplashViewController.h"


@implementation SplashViewController
@synthesize delegate;
@synthesize segmentBackground;
@synthesize hand;
@synthesize logo;
@synthesize background;

/*
// Override initWithNibName:bundle: to load the view using a nib file then perform additional customization that is not appropriate for viewDidLoad.
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        // Custom initialization
    }
    return self;
}
*/

/*
// Implement loadView to create a view hierarchy programmatically.
- (void)loadView {
}
*/

// Implement viewDidLoad to do additional setup after loading the view.
- (void)viewDidLoad {
    [super viewDidLoad];
   //set the image to the rounded rect
   //UIImage* image = [[UIImage imageNamed:@"whiteButton.png"] stretchableImageWithLeftCapWidth: 12.0 topCapHeight: 0 ];
   //[segmentBackground setBackgroundImage: image forState: UIControlStateNormal ];
   
   [UIView beginAnimations:@"relabel buttons" context:nil];
   [UIView setAnimationDuration: 0.75 ];
   [background setFrame: CGRectMake(20, 15, 280, 128)];
   [hand setFrame: CGRectMake(8, 0, 95, 138)];
   [logo setFrame: CGRectMake(76, 44, 226, 128)];
   [UIView commitAnimations];
   
   
}


- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning]; // Releases the view if it doesn't have a superview
    // Release anything that's not essential, such as cached data
}


- (void)dealloc {
    [super dealloc];
}

- (IBAction)splashOk:(id)sender forEvent:(UIEvent*)event
{
	if([delegate respondsToSelector:@selector(splashDidDoOk)])
	{
		[delegate performSelector:@selector(splashDidDoOk) withObject:nil afterDelay:0];
	}
}

@end
