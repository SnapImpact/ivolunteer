//
//  RestClient.m
//  iPhone
//
//  Created by Ryan Schneider on 2/12/09.
// Modified version of Wrapper originally posted here: http://kosmaczewski.net/projects/objective-c-rest-client/
// Original license: License: None, public domain, whatever you want :)
// Original code license (boilerplate from xCode):
//  Created by Adrian on 10/18/08.
//  Copyright 2008 Adrian Kosmaczewski. All rights reserved.

// Changes: Renamed Wrapper to RestClient, added delegate for didReceiveData and shouldRetainData

#import "RestClient.h"

@interface RestClient (Private) 
- (void)startConnection:(NSURLRequest *)request;
@end

@implementation RestClient

@synthesize receivedData;
@synthesize retainData;
@synthesize asynchronous;
@synthesize mimeType;
@synthesize username;
@synthesize password;
@synthesize delegate;
@synthesize delegateMethods;

#pragma mark -
#pragma mark Constructor and destructor

- (id)init
{
   if(self = [super init])
   {
      receivedData = [[NSMutableData alloc] init];
      retainData = YES;
      conn = nil;
      
      asynchronous = YES;
      mimeType = @"application/json";
      delegate = nil;
      username = @"";
      password = @"";
      self.delegateMethods = nil;
   }
   
   return self;
}

- (void)dealloc
{
   [receivedData release];
   receivedData = nil;
   self.mimeType = nil;
   self.username = nil;
   self.password = nil;
   self.delegateMethods = nil;
   [super dealloc];
}

#pragma mark -
#pragma mark Public methods

- (void)sendRequestTo:(NSURL *)url usingVerb:(NSString *)verb withParameters:(NSDictionary *)parameters
{
   NSData *body = nil;
   NSMutableString *params = nil;
   NSString *contentType = @"text/html; charset=utf-8";
   NSURL *finalURL = url;
   if (parameters != nil)
   {
      params = [[NSMutableString alloc] init];
      for (id key in parameters)
      {
         NSString *encodedKey = [key stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
         CFStringRef value = (CFStringRef)[[parameters objectForKey:key] copy];
         // Escape even the "reserved" characters for URLs 
         // as defined in http://www.ietf.org/rfc/rfc2396.txt
         CFStringRef encodedValue = CFURLCreateStringByAddingPercentEscapes(kCFAllocatorDefault, 
                                                                            value,
                                                                            NULL, 
                                                                            (CFStringRef)@";/?:@&=+$,", 
                                                                            kCFStringEncodingUTF8);
         [params appendFormat:@"%@=%@&", encodedKey, encodedValue];
         CFRelease(value);
         CFRelease(encodedValue);
      }
      [params deleteCharactersInRange:NSMakeRange([params length] - 1, 1)];
   }
   
   if ([verb isEqualToString:@"POST"] || [verb isEqualToString:@"PUT"])
   {
      contentType = @"application/x-www-form-urlencoded; charset=utf-8";
      body = [params dataUsingEncoding:NSUTF8StringEncoding];
   }
   else
   {
      if (parameters != nil)
      {
         NSString *urlWithParams = [[url absoluteString] stringByAppendingFormat:@"?%@", params];
         finalURL = [NSURL URLWithString:urlWithParams];
      }
   }
   
   NSMutableDictionary* headers = [[[NSMutableDictionary alloc] init] autorelease];
   [headers setValue:contentType forKey:@"Content-Type"];
   [headers setValue:mimeType forKey:@"Accept"];
   [headers setValue:@"no-cache" forKey:@"Cache-Control"];
   [headers setValue:@"no-cache" forKey:@"Pragma"];
   [headers setValue:@"close" forKey:@"Connection"]; // Avoid HTTP 1.1 "keep alive" for the connection
   
   NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:finalURL
                                                          cachePolicy:NSURLRequestUseProtocolCachePolicy
                                                      timeoutInterval:60.0];
   [request setHTTPMethod:verb];
   [request setAllHTTPHeaderFields:headers];
   if (parameters != nil)
   {
      [request setHTTPBody:body];
   }
   [params release];
   
   if ([delegateMethods containsObject: @"restClientShouldRetainData:"])
   {
      retainData = [delegate restClientShouldRetainData:self];
   }
   [self startConnection:request];
}

- (void)uploadData:(NSData *)data toURL:(NSURL *)url
{
   // File upload code adapted from http://www.cocoadev.com/index.pl?HTTPFileUpload
   // and http://www.cocoadev.com/index.pl?HTTPFileUploadSample
   
   NSString* stringBoundary = [NSString stringWithString:@"0xKhTmLbOuNdArY"];
   
   NSMutableDictionary* headers = [[[NSMutableDictionary alloc] init] autorelease];
   [headers setValue:@"no-cache" forKey:@"Cache-Control"];
   [headers setValue:@"no-cache" forKey:@"Pragma"];
   [headers setValue:[NSString stringWithFormat:@"multipart/form-data; boundary=%@", stringBoundary] forKey:@"Content-Type"];
   
   NSMutableURLRequest* request = [NSMutableURLRequest requestWithURL:url
                                                          cachePolicy:NSURLRequestUseProtocolCachePolicy
                                                      timeoutInterval:60.0];
   [request setHTTPMethod:@"POST"];
   [request setAllHTTPHeaderFields:headers];
   
   NSMutableData* postData = [NSMutableData dataWithCapacity:[data length] + 512];
   [postData appendData:[[NSString stringWithFormat:@"--%@\r\n", stringBoundary] dataUsingEncoding:NSUTF8StringEncoding]];
   [postData appendData:[@"Content-Disposition: form-data; name=\"image\"; filename=\"test.bin\"\r\n\r\n" 
                         dataUsingEncoding:NSUTF8StringEncoding]];
   [postData appendData:data];
   [postData appendData:[[NSString stringWithFormat:@"\r\n--%@--\r\n", stringBoundary] dataUsingEncoding:NSUTF8StringEncoding]];
   [request setHTTPBody:postData];
   
   [self startConnection:request];
}

- (void)cancelConnection
{
   [conn cancel];
   [conn release];
   conn = nil;
}

- (NSDictionary *)responseAsPropertyList
{
   NSString *errorStr = nil;
   NSPropertyListFormat format;
   NSDictionary *propertyList = [NSPropertyListSerialization propertyListFromData:receivedData
                                                                 mutabilityOption:NSPropertyListImmutable
                                                                           format:&format
                                                                 errorDescription:&errorStr];
   [errorStr release];
   return propertyList;
}

- (NSString *)responseAsText
{
   return [[[NSString alloc] initWithData:receivedData 
                                 encoding:NSUTF8StringEncoding] autorelease];
}

- (void) setDelegate: (NSObject<RestClientDelegate>*) d {
   if( self.delegateMethods == nil ) {
      self.delegateMethods = [NSMutableSet set];  
   }
   else {
      [ delegateMethods removeAllObjects ];
   }
   
   if ([d respondsToSelector:@selector(restClientShouldRetainData:)]) {
      [delegateMethods addObject:@"restClientShouldRetainData:"];
   }
   if( [d respondsToSelector:@selector(restClient:didFailWithError:)] ) {
      [delegateMethods addObject: @"restClient:didFailWithError:" ];
   }
   if ([d respondsToSelector:@selector(restClientHasBadCredentials:)]) {
      [delegateMethods addObject:@"restClientHasBadCredentials:" ];
   }
   if ([d respondsToSelector:@selector(restClient:didCreateResourceAtURL:)]) {
      [delegateMethods addObject:@"restClient:didCreateResourceAtURL:" ];
   }
   if ([d respondsToSelector:@selector(restClient:didReceiveStatusCode:)]) {
      [delegateMethods addObject:@"restClient:didReceiveStatusCode:" ];
   }
   if ([d respondsToSelector:@selector(restClient:didReceiveData:)]) {
      [delegateMethods addObject:@"restClient:didReceiveData:" ];
   }
   if ([d respondsToSelector:@selector(restClient:didFailWithError:)]) {
      [delegateMethods addObject:@"restClient:didFailWithError:" ];
   }
   if ([d respondsToSelector:@selector(restClient:didRetrieveData:)]) {
      [delegateMethods addObject:@"restClient:didRetrieveData:" ];
   }
   
   delegate = d;
}

#pragma mark -
#pragma mark Private methods

- (void)startConnection:(NSURLRequest *)request
{
   if (asynchronous)
   {
      [self cancelConnection];
      conn = [[NSURLConnection alloc] initWithRequest:request
                                             delegate:self
                                     startImmediately:YES];
      
      if (!conn)
      {
         if( [delegateMethods containsObject: @"restClient:didFailWithError:" ] )
         {
            NSMutableDictionary* info = [NSMutableDictionary dictionaryWithObject:[request URL] forKey:NSErrorFailingURLStringKey];
            [info setObject:@"Could not open connection" forKey:NSLocalizedDescriptionKey];
            NSError* error = [NSError errorWithDomain:@"RestClient" code:1 userInfo:info];
            [delegate restClient:self didFailWithError:error];
         }
      }
   }
   else
   {
      NSURLResponse* response;
      NSError* error;
      NSData* data = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];
      [receivedData setData:data];
   }
}

#pragma mark -
#pragma mark NSURLConnection delegate methods

- (void)connection:(NSURLConnection *)connection didReceiveAuthenticationChallenge:(NSURLAuthenticationChallenge *)challenge
{
   NSInteger count = [challenge previousFailureCount];
   if (count == 0)
   {
      NSURLCredential* credential = [[NSURLCredential credentialWithUser:username
                                                                password:password
                                                             persistence:NSURLCredentialPersistenceNone] autorelease];
      [[challenge sender] useCredential:credential 
             forAuthenticationChallenge:challenge];
   }
   else
   {
      [[challenge sender] cancelAuthenticationChallenge:challenge];
      if ([delegateMethods containsObject: @"restClientHasBadCredentials:"])
      {
         [delegate restClientHasBadCredentials:self];
      }
   }
}

- (void)connection:(NSURLConnection *)connection didReceiveResponse:(NSURLResponse *)response
{
   NSHTTPURLResponse* httpResponse = (NSHTTPURLResponse*)response;
   int statusCode = [httpResponse statusCode];
   switch (statusCode)
   {
      case 200:
         break;
         
      case 201:
      {
         NSString* url = [[httpResponse allHeaderFields] objectForKey:@"Location"];
         if ([delegateMethods containsObject: @"restClient:didCreateResourceAtURL:"])
         {
            [delegate restClient:self didCreateResourceAtURL:url];
         }
         break;
      }
         
         // Here you could add more status code handling... for example 404 (not found),
         // 204 (after a PUT or a DELETE), 500 (server error), etc... with the
         // corresponding delegate methods called as required.
         
      default:
      {
         if ([delegateMethods containsObject: @"restClient:didReceiveStatusCode:"])
         {
            [delegate restClient:self didReceiveStatusCode:statusCode];
         }
         break;
      }
   }
   [receivedData setLength:0];
}

- (void)connection:(NSURLConnection *)connection didReceiveData:(NSData *)data
{
   if(retainData) {
      [receivedData appendData:data];
   }
   //TODO: cache this selector as it'll be called often
   if ([delegateMethods containsObject: @"restClient:didReceiveData:"])
   {
      [delegate restClient:self didReceiveData:data];
   }
}

- (void)connection:(NSURLConnection *)connection didFailWithError:(NSError *)error
{
   [self cancelConnection];
   if ([delegateMethods containsObject: @"restClient:didFailWithError:"])
   {
      [delegate restClient:self didFailWithError:error];
   }
}

- (void)connectionDidFinishLoading:(NSURLConnection *)connection
{
   [self cancelConnection];
   if ([delegateMethods containsObject: @"restClient:didRetrieveData:"])
   {
      [delegate restClient:self didRetrieveData:receivedData];
   }
}

@end

