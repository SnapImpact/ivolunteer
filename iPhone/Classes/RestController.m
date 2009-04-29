//
//  RestController.m
//  iPhone
//
//  Created by Ryan Schneider on 2/15/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "RestController.h"


@implementation RestController

@synthesize iVD;

- (RestController*) initWithVolunteerData: (iVolunteerData*) ivd {
   [super init];
   [self setIVD: ivd];
   server = [[RestClient alloc] init ];
   [server setDelegate: self];
   return self;
}

- (void) beginGetEventsFrom:(NSDate*) dateFrom until: (NSDate*) dateUntil {
   NSURL* url = [NSURL URLWithString: @"http://actionfeed.org/server/resources/events/consolidated" ];
   NSLog( @"Beginning request to: %@ ", url );
   [server sendRequestTo: url usingVerb: @"GET" withParameters: nil ];
}

#pragma mark RestClient deletgate methods
- (BOOL)restClientShouldRetainData:(RestClient *)ri {
   //we'll parse it as we get it
   return YES;
}

- (void)restClient:(RestClient *)ri didRetrieveData:(NSData *)data {
   //tell parser we are done
   NSLog( @"Retrieved all data." );
   [[iVolunteerData sharedVolunteerData] parseJson: data ];
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
