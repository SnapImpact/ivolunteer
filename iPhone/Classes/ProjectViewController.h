//
//  RootViewController.h
//  iPhone
//
//  Created by Dave Angulo on 10/18/08.
//  Copyright __MyCompanyName__ 2008. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ProjectViewController : UITableViewController {
   IBOutlet UIButton* refreshButton;
}

@property (nonatomic, retain) UIButton* refreshButton;

@end
