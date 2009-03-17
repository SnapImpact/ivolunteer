// Copyright 2008 Cyrus Najmabadi
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

#import "ActionsView.h"

@interface ActionsView()
@property (assign) id target;
@property (retain) NSArray* selectors;
@property (retain) NSArray* titles;
@property (retain) NSArray* buttons;
@property (retain) NSArray* arguments;
@end


@implementation ActionsView

@synthesize target;
@synthesize selectors;
@synthesize titles;
@synthesize buttons;
@synthesize arguments;

- (void) dealloc {
    self.target = nil;
    self.selectors = nil;
    self.titles = nil;
    self.buttons = nil;
    self.arguments = nil;

    [super dealloc];
}


- (id) initWithTarget:(id) target_
            selectors:(NSArray*) selectors_
               titles:(NSArray*) titles_
            arguments:(NSArray*) arguments_
                image:(UIImage*) image
         imagePressed:(UIImage*) imagePressed
        imageSelected:(UIImage*) imageSelected
            textColor:(UIColor*) textColor 
    textColorSelected:textColorSelected {

    if (self = [super initWithFrame:CGRectZero]) {
        self.target = target_;
        self.selectors = selectors_;
        self.titles = titles_;
        self.arguments = arguments_;
        self.backgroundColor = [UIColor groupTableViewBackgroundColor];

        NSMutableArray* array = [NSMutableArray array];
        for (NSString* title in titles) {
            UIButton* button = [UIButton buttonWithType:UIButtonTypeRoundedRect];
            [button setTitle:title forState:UIControlStateNormal];
            [button sizeToFit];

            [button addTarget:self action:@selector(onButtonTapped:) forControlEvents:UIControlEventTouchUpInside];
           if( image != nil ) {
              [button setBackgroundImage: image forState: UIControlStateNormal ];
           }
           if( imagePressed != nil ) {
              [button setBackgroundImage: imagePressed forState: UIControlStateHighlighted ];
           }
           if( imageSelected != nil ) {
              [button setBackgroundImage: imageSelected forState: UIControlStateSelected ];
           }
           if( textColor != nil ) {
              [button setTitleColor: textColor forState: UIControlStateNormal ];
           }
           if( textColorSelected != nil ) {
              [button setTitleColor: textColorSelected forState: UIControlStateSelected ];
           }

            button.tag = [ array count ] + 100;
            [array addObject:button];
            [self addSubview:button];
        }

        self.buttons = array;

        {
            int lastRow = (buttons.count - 1) / 2;

            UIButton* button = [buttons lastObject];
            CGRect frame = button.frame;
            height = (8 + frame.size.height) * (lastRow + 1);
        }
    }

    return self;
}


+ (ActionsView*) viewWithTarget:(id) target
                      selectors:(NSArray*) selectors
                         titles:(NSArray*) titles
                      arguments:(NSArray*) arguments {
    return [[[ActionsView alloc] initWithTarget:(id) target
                                      selectors:selectors
                                         titles:titles
                                      arguments:arguments
                                          image:nil
                                   imagePressed:nil
                                  imageSelected:nil
                                      textColor:nil
                              textColorSelected:nil] autorelease];
}

+ (ActionsView*) viewWithTarget:(id) target
                      selectors:(NSArray*) selectors
                         titles:(NSArray*) titles
                      arguments:(NSArray*) arguments
                          image:(UIImage*) image
                   imagePressed:(UIImage*) imagePressed
                  imageSelected:(UIImage*) imageSelected
                      textColor:(UIColor*) textColor 
              textColorSelected:(UIColor*) textColorSelected {
   return [[[ActionsView alloc] initWithTarget:(id) target
                                     selectors:selectors
                                        titles:titles
                                     arguments:arguments
                                         image:image
                                  imagePressed:imagePressed
                                 imageSelected:imageSelected   
                                     textColor:textColor
                             textColorSelected:textColorSelected] autorelease];
}


- (void) onButtonTapped:(UIButton*) button {
    NSInteger index = [buttons indexOfObject:button];

    SEL selector = [[selectors objectAtIndex:index] pointerValue];
    if ([target respondsToSelector:selector]) {
        id argument = [arguments objectAtIndex:index];

        if (argument == [NSNull null]) {
            [target performSelector:selector];
        } else {
            [target performSelector:selector withObject:argument];
        }
    }
}


- (CGSize) sizeThatFits:(CGSize) size {
    if (buttons.count == 0) {
        return CGSizeZero;
    }

    double width;
    if (NO &&  //HACK: need rotation supported delegate
        UIDeviceOrientationIsLandscape([UIDevice currentDevice].orientation)) {
        width = [UIScreen mainScreen].bounds.size.height;
    } else {
        width = [UIScreen mainScreen].bounds.size.width;
    }

    return CGSizeMake(width, height);
}


- (CGFloat) height {
    return height;
}

- (void) setTitle:(NSString*) title forButtonAtIndex:(NSUInteger) index selected:(BOOL)selected animate:(BOOL) animate {
   UIButton* button = (UIButton*) [self viewWithTag: index+100 ];
   if(animate) {
      [UIView beginAnimations:@"relabel buttons" context:nil];
      [UIView setAnimationDuration: 0.75 ];
      if(selected)
         button.alpha = 0.5;
   }
   if(button == nil ) {
      [NSException raise: @"OutOfBoundsException" format: @"Index out of bounds: %d ", index ];
   }
   
   if(selected) {
      [button setTitle: title forState: UIControlStateSelected ];
   }
   else {
      [button setTitle: title forState: UIControlStateNormal ];
   }
   [self layoutSubviews ];
   button.selected = selected;
   if(animate) {
      if(selected)
         button.alpha = 1.0;
      [UIView commitAnimations];
   }
}


- (void) layoutSubviews {
    [super layoutSubviews];

    BOOL oddNumberOfButtons = ((buttons.count % 2) == 1);

    for (int i = 0; i < buttons.count; i++) {
        UIButton* button = [buttons objectAtIndex:i];

        NSInteger column;
        NSInteger row;
        if (oddNumberOfButtons && i != 0) {
            column = (i + 1) % 2;
            row = (i + 1) / 2;
        } else {
            column = i % 2;
            row = i / 2;
        }

        CGRect frame = button.frame;
        frame.origin.x = (column == 0 ? 10 : (self.frame.size.width / 2) + 4);
        frame.origin.y = (8 + frame.size.height) * row + 8;

        if (i == 0 && oddNumberOfButtons) {
            frame.size.width = (self.frame.size.width - 2 * frame.origin.x);
        } else {
            frame.size.width = (self.frame.size.width / 2) - 14;
        }

        button.frame = frame;
    }
}

@end