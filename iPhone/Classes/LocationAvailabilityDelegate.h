#import <CoreLocation/CoreLocation.h>

@protocol LocationAvailabilityDelegate

@required
- (void)locationIsAvailable:(CLLocation *)location;

@end