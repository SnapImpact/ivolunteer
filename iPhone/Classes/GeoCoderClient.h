//
//  GeoCoderClient.h
//  geocoder
//
//  Created by Hassan Abdel-Rahman on 12/20/08.
//  Copyright 2008 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface GeoCoderClient : NSObject
{

}

+ (NSDictionary *)getCoordForAddress:(NSString *)address;
+ (NSDictionary *)getCoordForAddress:(NSString *)address 
								city:(NSString *)city 
							   state:(NSString *)state 
								 zip:(NSString *)zip;
+ (NSDictionary *)getAddressForLatitude:(double)lat longitude:(double)lng;

@end
