//
//  RestController.h
//  iPhone
//
//  Created by Ryan Schneider on 2/15/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "iVolunteerData.h"
#import "RestClient.h"
#import "RestControllerDelegate.h"

@interface RestController : NSObject <RestClientDelegate> {
    iVolunteerData* iVD;
    RestClient* consolidatedClient;
    RestClient* filterDataClient;
    NSObject<RestControllerDelegate>* delegate;
}

@property (nonatomic, retain) RestClient *filterDataClient;
@property (nonatomic, retain) RestClient *consolidatedClient;
@property (nonatomic, retain) NSObject<RestControllerDelegate> *delegate;
@property (readwrite, retain) iVolunteerData* iVD ;

- (RestController*) initWithVolunteerData:(iVolunteerData*)ivd ;
- (void) beginGetEventsFrom: (NSDate*)dateFrom until: (NSDate*)dateUntil ;
- (void) beginGetFilterData ;

#pragma mark RestClient deletgate methods
- (BOOL)restClientShouldRetainData:(RestClient *)ri;
- (void)restClient:(RestClient *)ri didRetrieveData:(NSData *)data;
- (void)restClient:(RestClient *)ri didReceiveData:(NSData*)data;
- (void)restClientHasBadCredentials:(RestClient *)wrapper;
- (void)restClient:(RestClient *)ri didCreateResourceAtURL:(NSString *)url;
- (void)restClient:(RestClient *)ri didFailWithError:(NSError *)error;
- (void)restClient:(RestClient *)ri didReceiveStatusCode:(int)statusCode;

@end


