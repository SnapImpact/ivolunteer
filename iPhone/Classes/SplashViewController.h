//
//  SplashViewController.h
//  iPhone
//
//  Created by Aubrey Francois on 10/18/08.
//  Copyright 2008 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface SplashViewController : UIViewController {
	id delegate;
   IBOutlet UIButton* segmentBackground;
   IBOutlet UIImageView* hand;
   IBOutlet UIImageView* background;
   IBOutlet UIImageView* logo;
}

@property (nonatomic,retain) id delegate;
@property (retain) UIButton* IBOutlet segmentBackground;
@property (retain) IBOutlet UIImageView* hand;
@property (retain) IBOutlet UIImageView* background;
@property (retain) IBOutlet UIImageView* logo;

- (IBAction)splashOk:(id)sender forEvent:(UIEvent*)event;

@end
