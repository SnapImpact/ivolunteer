//
//  GeoCoderClient.m
//  geocoder
//
//  Created by Hassan Abdel-Rahman on 12/20/08.
//  Copyright 2008 __MyCompanyName__. All rights reserved.
//

#import "GeoCoderClient.h"
#import "GeoCoderConstants.h"


@implementation GeoCoderClient

+ (NSString *)getContainedStringWithString:(NSString *)doc start:(NSString *)start end:(NSString *)end
{
	NSString * result = nil;
	NSRange startRange = [doc rangeOfString:start];
	NSRange endRange   = [doc rangeOfString:end];
	if (startRange.location == NSNotFound || endRange.location == NSNotFound)
	{
		return @"";
	}
	
	if (startRange.location >= 0 && endRange.location > startRange.location)
	{
		result = [[NSString alloc] initWithString:[doc 
												   substringWithRange:NSMakeRange(startRange.location + startRange.length, 
																				  endRange.location - startRange.location - startRange.length)]];
	}
	return result;
}

+ (NSDictionary *)getCoordForAddress: (NSString *)address
{
	if (!address)
	{
		return nil;
	}
	//NSLog(@"getCoordForAddress: %@ - START", address);
	NSDictionary * results = nil;
	
	// cleanup address--remove anything within parens
	NSMutableString * cleanAddress = [[NSMutableString alloc] initWithString:address];
	NSRange parenStartRange = [cleanAddress rangeOfString:@"("];
	NSRange parenEndRange = [cleanAddress rangeOfString:@")"];
	if (parenStartRange.location > 0 && parenEndRange.location > parenStartRange.location)
	{
		NSLog(@"Geocoder detected bad address value, stripping out parenthesis from address: %@", cleanAddress);
		[cleanAddress deleteCharactersInRange:NSMakeRange(parenStartRange.location, parenEndRange.location - parenStartRange.location + 1)];
		NSLog(@"Clean address value is: %@", cleanAddress);
	}
	
	NSString * coordStr;
	NSString * encodedAddress = [[NSString alloc] initWithString:[cleanAddress stringByAddingPercentEscapesUsingEncoding: NSUTF8StringEncoding]];
	[cleanAddress release];
	NSString * geoCoderUrlStr = [[NSString alloc] initWithFormat:YAHOO_RELAXED_GEOCODER_URL, encodedAddress];
	//NSLog(@"Using geocoder URL: %@", geoCoderUrl);
	NSURL * url = [[NSURL alloc] initWithString:geoCoderUrlStr];
	NSURLRequest *geocoderRequest=[[NSURLRequest alloc ] initWithURL:url
														 cachePolicy:NSURLRequestUseProtocolCachePolicy
													 timeoutInterval:30.0];

	NSData * data = [[NSData alloc] initWithData:[NSURLConnection sendSynchronousRequest:geocoderRequest returningResponse:nil error:nil]];
	
	if ([data length] > 0) 
	{
		coordStr = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
		//NSLog(@"Received coordinates from geocoder: %@ for address:%@", coordStr);
		//NSLog(@"Adding coordinates: %@ to the cache for key: %@", coordStr, address);
	} 
	else 
	{
		NSLog(@"Could not establish connection to geocoder");
		results = nil;
	}
	
	NSString * lat  = [self getContainedStringWithString:coordStr start:@"<Latitude>" end:@"</Latitude>"];
	NSString * lng  = [self getContainedStringWithString:coordStr start:@"<Longitude>" end:@"</Longitude>"];
	
	NSString *precision = ZIP;
	NSRange addressPrecision = [coordStr rangeOfString:@"precision=\"address\""];
	NSRange streetPrecision = [coordStr rangeOfString:@"precision=\"street\""];
	NSRange cityPrecision = [coordStr rangeOfString:@"precision=\"city\""];
	
	if (addressPrecision.location != NSNotFound)
	{
		precision = ADDRESS;
	}
	else if (streetPrecision.location != NSNotFound)
	{
		precision = STREET;
	}
	else if (cityPrecision.location != NSNotFound)
	{
		precision = CITY;
	}
	
	BOOL gotResults = NO;
	if (lat && lng)
	{
		if ([lat length] > 0 && [lng length] > 0)
		{
			gotResults = YES;
			results = [[NSDictionary alloc] initWithObjectsAndKeys: 
					   lat, LATITUDE, 
					   lng, LONGITUDE, 
					   precision, PRECISION,
					   nil];
		}
	}
	if (!gotResults)
	{
		results = [[NSDictionary alloc] initWithObjectsAndKeys: 
				   @"0", LATITUDE, 
				   @"0", LONGITUDE, 
				   ZIP, PRECISION,
				   nil];
	}

	[lat release];
	[lng release];
	[coordStr release];
	[data release];
	[url release];
	[geoCoderUrlStr release];
	[encodedAddress release];
	[geocoderRequest release];
	//NSLog(@"getCoordForAddress: %@ - END", address);
	return [results autorelease];
}

+ (NSDictionary *)getCoordForAddress:(NSString *)address city:(NSString *)city state:(NSString *)state zip:(NSString *)zip
{
	NSDictionary * results = nil;
	NSString * coordStr = nil;
	NSString * encodedAddress = [[NSString alloc] initWithString:[address stringByAddingPercentEscapesUsingEncoding: NSUTF8StringEncoding]];
	NSString * encodedCity = [[NSString alloc] initWithString:[city stringByAddingPercentEscapesUsingEncoding: NSUTF8StringEncoding]];
	NSString * encodedState = [[NSString alloc] initWithString:[state stringByAddingPercentEscapesUsingEncoding: NSUTF8StringEncoding]];
	NSString * encodedZip = [[NSString alloc] initWithString:[zip stringByAddingPercentEscapesUsingEncoding: NSUTF8StringEncoding]];
	NSString * geoCoderUrlStr = [[NSString alloc] initWithFormat:YAHOO_STRICT_GEOCODER_URL, encodedAddress, encodedCity, encodedState, encodedZip];

	NSURL * url = [[NSURL alloc] initWithString:geoCoderUrlStr];
	NSURLRequest *geocoderRequest=[[NSURLRequest alloc ] initWithURL:url
														 cachePolicy:NSURLRequestUseProtocolCachePolicy
													 timeoutInterval:30.0];
	
	NSData * data = [[NSData alloc] initWithData:[NSURLConnection sendSynchronousRequest:geocoderRequest returningResponse:nil error:nil]];
	
	if ([data length] > 0) 
	{
		coordStr = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
		//NSLog(@"Received coordinates from geocoder: %@ for address:%@", coordStr);
		//NSLog(@"Adding coordinates: %@ to the cache for key: %@", coordStr, address);
	} 
	else 
	{
		NSLog(@"Could not establish connection to geocoder");
		results = nil;
	}
	
	NSString * lat  = [self getContainedStringWithString:coordStr start:@"<Latitude>" end:@"</Latitude>"];
	NSString * lng  = [self getContainedStringWithString:coordStr start:@"<Longitude>" end:@"</Longitude>"];
	NSString *precision = ZIP;
	NSRange addressPrecision = [coordStr rangeOfString:@"precision=\"address\""];
	NSRange streetPrecision = [coordStr rangeOfString:@"precision=\"street\""];
	NSRange cityPrecision = [coordStr rangeOfString:@"precision=\"city\""];
	
	if (addressPrecision.location != NSNotFound)
	{
		precision = ADDRESS;
	}
	else if (streetPrecision.location != NSNotFound)
	{
		precision = STREET;
	}
	else if (cityPrecision.location != NSNotFound)
	{
		precision = CITY;
	}
	
	
	BOOL gotResults = NO;
	if (lat && lng)
	{
		if ([lat length] > 0 && [lng length] > 0)
		{
			gotResults = YES;
			results = [[NSDictionary alloc] initWithObjectsAndKeys: 
					   lat, LATITUDE, 
					   lng, LONGITUDE, 
					   precision, PRECISION,
					   nil];
		}
	}
	if (!gotResults)
	{
		results = [[NSDictionary alloc] initWithObjectsAndKeys: 
				   @"0", LATITUDE, 
				   @"0", LONGITUDE, 
				   ZIP, PRECISION,
				   nil];
	}
	
	[lat release];
	[lng release];
	[coordStr release];
	[data release];
	[url release];
	[geoCoderUrlStr release];
	[encodedAddress release];
	[geocoderRequest release];
	//NSLog(@"getCoordForAddress: %@ - END", address);
	return [results autorelease];
	
}



+ (NSDictionary *)getZonetagAddressForLatitude: (double)lat longitude: (double)lng existingData:(NSDictionary *)existingData
{
	//NSLog(@"getAddressForLatitude: %f longitude: %f - START", lat, lng);
	NSDictionary * results = nil;
	NSString * reverseGeocoderUrlStr = [[NSString alloc] initWithFormat:ZONETAG_REVERSE_GEOCODER_URL, lat, lng];
	//NSLog(@"Using reverse geocoder URL: %@", reverseGeocoderUrl);
	NSURL * url = [[NSURL alloc] initWithString:reverseGeocoderUrlStr];
	NSURLRequest *reverseGeocoderRequest=[[NSURLRequest alloc] initWithURL:url
															   cachePolicy:NSURLRequestUseProtocolCachePolicy
														   timeoutInterval:30.0];
	NSData * data = [[NSData alloc] initWithData:[NSURLConnection sendSynchronousRequest:reverseGeocoderRequest 
																	   returningResponse:nil
																				   error:nil]];
	
	if ([data length] > 0) 
	{
		NSString *locationXmlStr = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
	//	NSLog(@"Received location XML from zonetag reverse geocoder: %@", locationXmlStr);
		
		NSString * streetNumber = @"";
		NSString * street = @"";
		NSString * county = @"";
		
		if (existingData)
		{
			streetNumber = [existingData objectForKey:STREET_NUMBER];
			street = [existingData objectForKey:STREET];
			county = [existingData objectForKey:COUNTY];
		}
		
		NSString * city  = [self getContainedStringWithString:locationXmlStr start:@"<city>" end:@"</city>"];
		NSString * state  = [self getContainedStringWithString:locationXmlStr start:@"<state>" end:@"</state>"];
		NSString * zip = [self getContainedStringWithString:locationXmlStr start:@"<zipcode>" end:@"</zipcode>"];
		NSString * country = [self getContainedStringWithString:locationXmlStr start:@"<country>" end:@"</country>"];
		
		results = [[NSDictionary alloc] initWithObjectsAndKeys: 
				   streetNumber, STREET_NUMBER, 
				   street, STREET,
				   city, CITY,
				   state, STATE,
				   zip, ZIP,
				   county, COUNTY,
				   country, COUNTRY,
				   nil];
		
		[locationXmlStr release];
		[city release];
		[state release];
		[zip release];
		[country release];
	}
	else
	{
		NSLog(@"Could not establish connection to zonetag reverse geocoder");
	}
	[data release];
	[url release];
	[reverseGeocoderRequest release];
	[reverseGeocoderUrlStr release];
	//NSLog(@"getAddressForLatitude: %f longitude: %f - END", lat, lng);
	return [results autorelease];
	
}

+ (NSDictionary *)getZonetagAddressForLatitude:(double)lat longitude:(double)lng
{
	return [[GeoCoderClient getZonetagAddressForLatitude:lat longitude:lng existingData:nil] autorelease];
}

+ (NSDictionary *)getGeonamesAddressForLatitude: (double) lat longitude: (double) lng
{
	//NSLog(@"getAddressForLatitude: %f longitude: %f - START", lat, lng);
	NSDictionary * results = nil;
	NSString * reverseGeocoderUrlStr = [[NSString alloc] initWithFormat:GEONAMES_REVERSE_GEOCODER_URL, lat, lng];
	//NSLog(@"Using reverse geocoder URL: %@", reverseGeocoderUrl);
	NSURL * url = [[NSURL alloc] initWithString:reverseGeocoderUrlStr];
	NSURLRequest *reverseGeocoderRequest=[[NSURLRequest alloc] initWithURL:url
															   cachePolicy:NSURLRequestUseProtocolCachePolicy
														   timeoutInterval:3.0];
	NSData * data = [[NSData alloc] initWithData:[NSURLConnection sendSynchronousRequest:reverseGeocoderRequest 
																	   returningResponse:nil
																				   error:nil]];
	
	if ([data length] > 0) 
	{
		NSString *locationXmlStr = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
		//NSLog(@"Received location XML from geonames reverse geocoder: %@", locationXmlStr);
		NSRange noAddressRange = [locationXmlStr rangeOfString:@"<geonames/>"];
		if (noAddressRange.location == NSNotFound)
		{
			NSString * street		= [self getContainedStringWithString:locationXmlStr start:@"<street>" end:@"</street>"];
			NSString * streetNumber = [self getContainedStringWithString:locationXmlStr start:@"<streetNumber>" end:@"</streetNumber>"];
			NSString * city			= [self getContainedStringWithString:locationXmlStr start:@"<placename>" end:@"</placename>"];
			NSString * state		= [self getContainedStringWithString:locationXmlStr start:@"<adminCode1>" end:@"</adminCode1>"];
			NSString * zip			= [self getContainedStringWithString:locationXmlStr start:@"<postalcode>" end:@"</postalcode>"];
			NSString * county		= [self getContainedStringWithString:locationXmlStr start:@"<adminName2>" end:@"</adminName2>"];
			NSString * country		= [self getContainedStringWithString:locationXmlStr start:@"<countryCode>" end:@"</countryCode>"];
			
			results = [[NSDictionary alloc] initWithObjectsAndKeys: 
					   streetNumber, STREET_NUMBER, 
					   street, STREET,
					   city, CITY,
					   state, STATE,
					   zip, ZIP,
					   county, COUNTY,
					   country, COUNTRY,
					   nil];
			
			[streetNumber release];
			[street release];
			[city release];
			[state release];
			[zip release];
			[county release];
			[country release];
			 
		}
		else
		{
			NSLog(@"geonames returned empty element for reverse geocode");
		}
		[locationXmlStr release];
	}
	else
	{
		NSLog(@"Could not establish connection to geonames reverse geocoder");
	}
	
	[data release];
	[url release];
	[reverseGeocoderRequest release];
	[reverseGeocoderUrlStr release];
	//NSLog(@"getAddressForLatitude: %f longitude: %f - END", lat, lng);
	return [results autorelease];
	
}

+ (NSDictionary *)getAddressForLatitude: (double)lat longitude: (double)lng
{
	NSDictionary * results = nil;
	results = [GeoCoderClient getGeonamesAddressForLatitude:lat longitude:lng];
	if (!results)
	{
		NSLog(@"Cannot get geonames reverse geocoding data, trying zonetag");
		results = [GeoCoderClient getZonetagAddressForLatitude:lat longitude:lng];
		NSLog(@"Received zonetag reverse geocoding data: %@", results);
	}
	else
	{
		//NSLog(@"Received geonames reverse geocoding data: %@", results);
		
		NSString *city = [results objectForKey:CITY];
		NSString *zip  = [results objectForKey:ZIP];
		if ([city length] == 0 && [zip length] == 0)
		{
			NSLog(@"Geonames did not return enough data, using zone tag as well");
			results = [GeoCoderClient getZonetagAddressForLatitude:lat longitude:lng existingData:results];
		}
	}
	return [results autorelease];
}


@end
