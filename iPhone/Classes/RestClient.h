//
//  RestClient.h
//  iPhone
//
//  Created by Ryan Schneider on 2/12/09.
//
// Modified version of Wrapper originally posted here: http://kosmaczewski.net/projects/objective-c-rest-client/
// Original license: License: None, public domain, whatever you want :)
// Original code license (boilerplate from xCode):
//  Created by Adrian on 10/18/08.
//  Copyright 2008 Adrian Kosmaczewski. All rights reserved.

// Changes: Renamed Wrapper to RestClient, added delegate for didReceiveData and shouldRetainData

#import <Foundation/Foundation.h> 
#import "RestClientDelegate.h"

@interface RestClient : NSObject 
{
@private
   BOOL retainData;
   NSMutableData *receivedData;
   NSString *mimeType;
   NSURLConnection *conn;
   BOOL asynchronous;
   NSObject<RestClientDelegate> *delegate;
   NSString *username;
   NSString *password;
   
   NSMutableSet* delegateMethods;
}

@property (assign) BOOL retainData;
@property (readonly, retain) NSData *receivedData;
@property (assign) BOOL asynchronous;
@property (copy) NSString *mimeType;
@property (copy) NSString *username;
@property (copy) NSString *password;
@property (nonatomic, assign) NSObject<RestClientDelegate> *delegate; // Do not retain delegates!
@property (retain) NSMutableSet* delegateMethods;

- (void)sendRequestTo:(NSURL *)url usingVerb:(NSString *)verb withParameters:(NSDictionary *)parameters;
- (void)uploadData:(NSData *)data toURL:(NSURL *)url;
- (void)cancelConnection;
- (NSDictionary *)responseAsPropertyList;
- (NSString *)responseAsText;

@end

