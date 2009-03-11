//
//  EventDetailsHeaderViewController.m
//  iPhone
//
//  Created by Ryan Schneider on 2/28/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "EventDetailsHeaderCell.h"
#import "DateUtilities.h"

@implementation EventDetailsHeaderCell

@synthesize event;
@synthesize name;
@synthesize organization;
@synthesize date;
@synthesize time;

+ (NSString*) reuseIdentifier
{
   return @"EventDetailsHeaderCell";
}

+ (CGFloat) height
{
   return 100;
}

- (id)initWithFrame:(CGRect)frame reuseIdentifier:(NSString *)reuseIdentifier {
   if (self = [super initWithFrame:frame reuseIdentifier:reuseIdentifier]) {
      // Initialization code
      if( self.event != nil ) {
         self.name.text = self.event.name;
         self.organization.text = self.event.organization.name;
         self.date.text = self.event.date.description;
         self.time.text = self.event.date.description;
      }
   }
   return self;
}

- (void) setEvent: (Event*) event_ 
{
   if(event)
      [event release];
   
   event = [event_ retain];
   self.name.text = event_.name;
   self.organization.text = event_.organization.name;
   self.date.text = [DateUtilities formatMediumDate: event_.date ];
   self.time.text = [DateUtilities formatShortTime: event_.date ];
}

- (void)dealloc {
   self.name = nil;
   self.organization = nil;
   self.date =  nil;
   self.time = nil;
   self.event = nil;
   [super dealloc];
}


@end
