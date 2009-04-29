

#import <UIKit/UIKit.h>

typedef struct {
    double minLat;
    double minLng;
    double maxLat;
    double maxLng;
} GLatLngBounds;

typedef struct {
    long x;
    long y;
} GPoint;

typedef struct {
    double lat;
    double lng;
} GLatLng;

#define GLatLngBoundsMake(minLat, minLng, maxLat, maxLng) \
                                (GLatLngBounds){(double)(minLat), (double)(minLng), \
                                                (double)(maxLat), (double)(maxLng)}
#define GPointMake(x, y)        (GPoint){(long)(x), (long)(y)}
#define GLatLngMake(lat, lng)   (GLatLng){(double)(lat), (double)(lng)}
#define GPoint2CGPoint(p)       CGPointMake((p).x, (p).y)

#define G_NORMAL_MAP            @"G_NORMAL_MAP"
#define G_SATELLITE_MAP         @"G_SATELLITE_MAP"
#define G_HYBRID_MAP            @"G_HYBRID_MAP"
#define G_PHYSICAL_MAP          @"G_PHYSICAL_MAP"
#define G_MOON_ELEVATION_MAP    @"G_MOON_ELEVATION_MAP"
#define G_MOON_VISIBLE_MAP      @"G_MOON_VISIBLE_MAP"
#define G_MARS_ELEVATION_MAP    @"G_MARS_ELEVATION_MAP"
#define G_MARS_VISIBLE_MAP      @"G_MARS_VISIBLE_MAP"
#define G_MARS_INFRARED_MAP     @"G_MARS_INFRARED_MAP"
#define G_SKY_VISIBLE_MAP       @"G_SKY_VISIBLE_MAP"


@protocol MapWebViewDelegate <UIWebViewDelegate>
- (void) mapZoomUpdatedTo:(int)zoomLevel;
- (void) mapCenterUpdatedToLatLng:(GLatLng)latlng;
- (void) mapCenterUpdatedToPixel:(GPoint)pixel;
- (void) mapCenterPannedToPixel:(GPoint)pixel;
- (GLatLng) provideStartingLocation;
- (int) provideStartingZoom;

@end

@interface MapWebView : UIWebView {
@private
    id <MapWebViewDelegate> mapDelegate;
}
@property (retain, nonatomic) IBOutlet id <MapWebViewDelegate> mapDelegate;

- (void)      didMoveToSuperview;
- (NSString*) evalJS:(NSString*)script;
- (void)      moveByDx:(int)dX dY:(int)dY;
- (id)		  initWithFrame:(CGRect)frame 
			   delegate:(id <UIWebViewDelegate>)delegate 
		 delegateForMap:(id <MapWebViewDelegate>)mDelegate;

//-- Methods corresponding to Google Maps Javascript API methods ---------------
- (int)       getZoom;
- (void)      setZoom:(int)zoomLevel;
- (void)      zoomIn;
- (void)      zoomOut;
- (GLatLng)   getCenterLatLng;
- (GPoint)    getCenterPixel;
- (void)      setCenterWithPixel:(GPoint)pixel;
- (void)      setCenterWithLatLng:(GLatLng)latlng;
- (void)      panToCenterWithPixel:(GPoint)pixel;
- (void)      panToBrowserTipWithPixel:(GPoint)pixel;
- (GLatLng)   fromContainerPixelToLatLng:(GPoint)pixel;
- (GPoint)    fromLatLngToContainerPixel:(GLatLng)latlng;
- (int)       getBoundsZoomLevel:(GLatLngBounds)bounds;
- (void)      setMapType:(NSString*)mapType;
@end
