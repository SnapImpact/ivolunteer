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

#import "StringUtilities.h"

@implementation StringUtilities

+ (NSString*) nonNilString:(NSString*) string {
    if (string == nil) {
        return @"";
    }

    return string;
}


+ (NSString*)                      string:(NSString*) string
      byAddingPercentEscapesUsingEncoding:(NSStringEncoding) encoding {
    string = [string stringByAddingPercentEscapesUsingEncoding:encoding];
    string = [string stringByReplacingOccurrencesOfString:@"?" withString:@"%3F"];
    string = [string stringByReplacingOccurrencesOfString:@"&" withString:@"%26"];

    return string;
}


+ (NSString*) stringByAddingPercentEscapes:(NSString*) string {
    return [self string:string byAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
}


+ (NSString*) stripHtmlCodes:(NSString*) string {
    if (string == nil) {
        return @"";
    }

    NSArray* htmlCodes = [NSArray arrayWithObjects:@"a", @"em", @"p", @"b", @"i", @"br", nil];

    for (NSString* code in htmlCodes) {
        string = [string stringByReplacingOccurrencesOfString:[NSString stringWithFormat:@"<%@>", code] withString:@""];
        string = [string stringByReplacingOccurrencesOfString:[NSString stringWithFormat:@"</%@>", code] withString:@""];
    }

    return string;
}


+ (NSString*) asciiString:(NSString*) string {
    NSString* asciiString = [[[NSString alloc] initWithData:[string dataUsingEncoding:NSASCIIStringEncoding allowLossyConversion:YES]
                                                   encoding:NSASCIIStringEncoding] autorelease];
    return asciiString;
}


+ (NSString*) stringFromUnichar:(unichar) c {
    return [NSString stringWithCharacters:&c length:1];
}


+ (NSString*) stripHtmlLinks:(NSString*) string {
    NSInteger index = 0;
    NSRange range;
    while ((range = [string rangeOfString:@"<a href"
                                    options:0
                                      range:NSMakeRange(index, string.length - index)]).length > 0) {
        index = range.location + 1;
        NSRange closeAngleRange = [string rangeOfString:@">"
                                                  options:0
                                                    range:NSMakeRange(range.location, string.length - range.location)];
        if (closeAngleRange.length > 0) {
            string = [NSString stringWithFormat:@"%@%@", [string substringToIndex:range.location], [string substringFromIndex:closeAngleRange.location + 1]];
        }
    }

    return string;
}


+ (NSString*) convertHtmlEncodings:(NSString*) string {
    NSInteger index = 0;
    NSRange range;
    while ((range = [string rangeOfString:@"&#x"
                                    options:0
                                      range:NSMakeRange(index, string.length - index)]).length > 0) {
        NSRange semiColonRange = [string rangeOfString:@";" options:0 range:NSMakeRange(range.location, string.length - range.location)];

        index = range.location + 1;
        if (semiColonRange.length > 0) {
            NSScanner* scanner = [NSScanner scannerWithString:string];
            [scanner setScanLocation:range.location + 3];
            unsigned hex;
            if ([scanner scanHexInt:&hex] && hex > 0) {
                string = [NSString stringWithFormat:@"%@%@%@",
                            [string substringToIndex:range.location],
                            [StringUtilities stringFromUnichar:(unichar) hex],
                            [string substringFromIndex:semiColonRange.location + 1]];
            }
        }
    }

    string = [StringUtilities stripHtmlCodes:string];
    string = [string stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]];

    return string;
}

@end