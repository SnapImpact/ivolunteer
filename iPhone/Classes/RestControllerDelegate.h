@class RestController;
@class RestClient;

@protocol  RestControllerDelegate

- (void) restController: (RestController*) controller
        didRetrieveData: (NSData*) data
          forRestClient: (RestClient*) client;

- (void) restController: (RestController*) controller
       didFailWithError: (NSError*) error
          forRestClient: (RestClient*) client;
@end