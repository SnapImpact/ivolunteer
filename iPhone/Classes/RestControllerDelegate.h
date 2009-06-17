@class RestController;

@protocol  RestControllerDelegate

- (void) restController: (RestController*) controller
        didRetrieveData: (NSData*) data;

@end