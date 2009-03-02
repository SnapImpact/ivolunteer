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

#import "AttributeCell.h"

#import "ColorCache.h"

@interface AttributeCell()
@property (retain) UILabel* keyLabel;
@property (retain) UILabel* valueLabel;
@end


@implementation AttributeCell

@synthesize keyLabel;
@synthesize valueLabel;

- (void) dealloc {
    self.keyLabel = nil;
    self.valueLabel = nil;

    [super dealloc];
}

+ (UIFont*) keyFont {
    return [UIFont boldSystemFontOfSize:12.0];
}


- (id) initWithFrame:(CGRect) frame reuseIdentifier:(NSString*) reuseIdentifier {
    if (self = [super initWithFrame:frame reuseIdentifier:reuseIdentifier]) {
        self.keyLabel = [[[UILabel alloc] initWithFrame:frame] autorelease];
        self.valueLabel = [[[UILabel alloc] initWithFrame:frame] autorelease];

        keyLabel.textColor = [ColorCache commandColor];
        keyLabel.font = [AttributeCell keyFont];
        keyLabel.textAlignment = UITextAlignmentRight;

        valueLabel.font = [UIFont boldSystemFontOfSize:14.0];
        valueLabel.adjustsFontSizeToFitWidth = YES;
        valueLabel.minimumFontSize = 10.0;

        [self.contentView addSubview:keyLabel];
        [self.contentView addSubview:valueLabel];
    }

    return self;
}


- (void) layoutSubviews {
    [super layoutSubviews];

    {
        CGRect frame = keyLabel.frame;
        frame.origin.y = floor((self.contentView.frame.size.height - keyLabel.frame.size.height) / 2);
        keyLabel.frame = frame;
    }

    {
        CGRect frame = valueLabel.frame;
        frame.origin.y = floor((self.contentView.frame.size.height - valueLabel.frame.size.height) / 2);
        frame.size.width = self.contentView.frame.size.width - frame.origin.x;
        valueLabel.frame = frame;
    }
}


- (void) setKey:(NSString*) key
          value:(NSString*) value
       keyWidth:(CGFloat) keyWidth {
    keyLabel.text = key;
    valueLabel.text = value;

    {
        [keyLabel sizeToFit];
        CGRect frame = keyLabel.frame;
        frame.origin.x = keyWidth - frame.size.width;
        keyLabel.frame = frame;
    }

    {
        [valueLabel sizeToFit];
        CGRect frame = valueLabel.frame;
        frame.origin.x = keyWidth + 10;
        valueLabel.frame = frame;
    }
}


- (void) setSelected:(BOOL) selected
            animated:(BOOL) animated {
    [super setSelected:selected animated:animated];
    if (selected) {
        keyLabel.textColor = [UIColor whiteColor];
        valueLabel.textColor = [UIColor whiteColor];
    } else {
        keyLabel.textColor = [ColorCache commandColor];
        valueLabel.textColor = [UIColor blackColor];
    }
}

@end