//
//  RestClientDelegate.h
//  iPhone
//
//  Created by Ryan Schneider on 2/12/09.
//
// Modified version of WrapperDelegate originally posted here: http://kosmaczewski.net/projects/objective-c-rest-client/
// Original license: License: None, public domain, whatever you want :)
// Original code license (boilerplate from xCode):
//  Created by Adrian on 10/18/08.
//  Copyright 2008 Adrian Kosmaczewski. All rights reserved.

// Changes: Renamed Wrapper to RestClient, added delegate for didReceiveData and shouldRetainData

@class RestClient;

@protocol RestClientDelegate

@required
- (BOOL)restClientShouldRetainData:(RestClient *)ri;

@optional
- (void)restClient:(RestClient *)ri didRetrieveData:(NSData *)data;
- (void)restClient:(RestClient *)ri didReceiveData:(NSData*)data;
- (void)restClientHasBadCredentials:(RestClient *)wrapper;
- (void)restClient:(RestClient *)ri didCreateResourceAtURL:(NSString *)url;
- (void)restClient:(RestClient *)ri didFailWithError:(NSError *)error;
- (void)restClient:(RestClient *)ri didReceiveStatusCode:(int)statusCode;

@end
