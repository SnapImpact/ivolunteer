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
}

@property (nonatomic,retain) id delegate;
@property (retain) UIButton* IBOutlet segmentBackground;

- (IBAction)splashOk:(id)sender forEvent:(UIEvent*)event;

@end
