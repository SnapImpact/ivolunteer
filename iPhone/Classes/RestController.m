//
//  RestController.m
//  iPhone
//
//  Created by Ryan Schneider on 2/15/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "RestController.h"
#import "SettingsViewController.h"
#import "iPhoneAppDelegate.h"

@implementation RestController

@synthesize iVD;

- (RestController*) initWithVolunteerData: (iVolunteerData*) ivd {
    [super init];
    [self setIVD: ivd];
    consolidatedClient = [[RestClient alloc] init ];
    filterDataClient = [[RestClient alloc] init ];
    [consolidatedClient setDelegate: self];
    [filterDataClient setDelegate: self];
    return self;
}

- beginGetFilterData {
    [[iPhoneAppDelegate BusyIndicator] startAnimatingWithMessage: @"Getting Filters..." atBottom: YES];
    NSURL* url= [NSURL URLWithString: @"http://actionfeed.org/server/resources/filterData"];
    [filterDataClient sendRequestTo: url usingVerb: @"GET" withParameters: nil ];
}

- (void) beginGetEventsFrom:(NSDate*) dateFrom until: (NSDate*) dateUntil {
	
    [[iPhoneAppDelegate BusyIndicator] startAnimatingWithMessage: @"Getting Events..." atBottom: YES];
	NSString *urlStr = nil;
	CLLocation *currentLocation = iVD.myLocation;
	NSDictionary* settings = (NSDictionary*) CFPreferencesCopyAppValue((CFStringRef) kSettingsKey, 
                                                                       kCFPreferencesCurrentApplication);
	BOOL useZipCodeOverride = NO;
	NSString *zipcode = nil;
	if (settings)
	{
		zipcode = [settings objectForKey:kSettingsKeyZipcode];
		NSNumber *useZipCodeNum = [settings objectForKey:kSettingsKeyUseZipcode];
		useZipCodeOverride = [useZipCodeNum boolValue];
	}
    
	//TODO: Need to verify feed format for zipcodes and long/lat params
	
	if (useZipCodeOverride && zipcode)
	{
		urlStr = [[[NSString alloc] initWithFormat:@"http://actionfeed.org/server/resources/events/consolidated?zip=%@", zipcode] autorelease];
	}
	else if (currentLocation)
	{
		// use iVolunteerData long/lat to pull feed
		urlStr = [[[NSString alloc] initWithFormat:@"http://actionfeed.org/server/resources/events/consolidated?lat=%lf&long=%lf", 
				   currentLocation.coordinate.latitude, 
				   currentLocation.coordinate.longitude] autorelease];
	}
	else if (zipcode && [zipcode length])
	{
		urlStr = [[[NSString alloc] initWithFormat:@"http://actionfeed.org/server/resources/events/consolidated?zip=%@", zipcode] autorelease];
	}
	else
	{
		// cannot determine location--display popup to user
        
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle: NSLocalizedString(@"Could Not Determine Location", @"Could not determine location")
                                                        message:NSLocalizedString(@"Cannot determine your current location. Please enter your home zip code from the Settings tab.", @"How will we internationalize zipcodes?")
                                                       delegate:nil 
                                              cancelButtonTitle:NSLocalizedString(@"Ok", @"Ok")
                                              otherButtonTitles:nil];
        [alert show];
        [alert release];
        urlStr = @"http://actionfeed.org/server/resources/events/consolidated";
	}
    NSURL* url = [NSURL URLWithString: urlStr ];
    NSLog( @"Beginning request to: %@ ", url );
    [consolidatedClient sendRequestTo: url usingVerb: @"GET" withParameters: nil ];
}

#pragma mark RestClient deletgate methods
- (BOOL)restClientShouldRetainData:(RestClient *)ri {
    //we'll parse it as we get it
    return YES;
}

- (void)restClient:(RestClient *)ri didRetrieveData:(NSData *)data {
    //tell parser we are done
    if(ri == consolidatedClient) {
        NSLog( @"Retrieved all data." );
        [[iVolunteerData sharedVolunteerData] parseConsolidatedJson: data ];
    }
    else if( ri == filterDataClient) {
        [[iVolunteerData sharedVolunteerData] parseFilterDataJson: data ];
    }        
    [[iPhoneAppDelegate BusyIndicator] stopAnimating];
}

- (void)restClient:(RestClient *)ri didReceiveData:(NSData*)data {
    //pass the snipper to the parser
}

- (void)restClientHasBadCredentials:(RestClient *)wrapper {
    //display an error (should never happen as our REST server is open
    NSAssert( NO, @"Credentials needed, but this should never happen!" );
}

- (void)restClient:(RestClient *)ri didCreateResourceAtURL:(NSString *)url {
    //should never happen, as we don't PUT resources
    NSAssert( NO, @"Why did we create a resource?" );
}

- (void)restClient:(RestClient *)ri didFailWithError:(NSError *)error {
    //Handle error, for now assert
    NSString* err = [ NSString stringWithFormat: @"Download failed with error: %@", error ];
    NSAssert( NO, err );
    [err release];
}

- (void)restClient:(RestClient *)ri didReceiveStatusCode:(int)statusCode {
    NSLog( @"Received status code: %d", statusCode );
}

@end
