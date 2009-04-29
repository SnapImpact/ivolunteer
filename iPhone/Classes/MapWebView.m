
#import "MapWebView.h"

#define DEFAULT_ZOOM_LEVEL	12

@interface MapWebView (Private)
- (void) loadMap;
@end

@implementation MapWebView

//-- Public Methods ------------------------------------------------------------
@synthesize mapDelegate;
//------------------------------------------------------------------------------

- (id) initWithFrame:(CGRect)frame 
			delegate:(id <UIWebViewDelegate>)delegate 
	  delegateForMap:(id <MapWebViewDelegate>)mDelegate
{
	[super initWithFrame:frame];
	self.delegate = delegate;
	self.mapDelegate = mDelegate;
	return self;
}

- (void) didMoveToSuperview {
    // this hook method is used to initialize the view; we don't want 
    // any user input to be delivered to the UIWebView, instead, the 
    // MapView overlay will receive all input and convert it to commands 
    // that can be performed by the Google Maps Javascript API directly
    
    self.userInteractionEnabled = NO;
    self.scalesPageToFit = NO;
    self.autoresizingMask = 
    	UIViewAutoresizingFlexibleHeight|UIViewAutoresizingFlexibleWidth;
    
    [self loadMap];
}
//------------------------------------------------------------------------------
- (NSString*) evalJS:(NSString*)script {
    return [self stringByEvaluatingJavaScriptFromString:script];
}
//------------------------------------------------------------------------------
- (void) moveByDx:(int)dX dY:(int)dY {
    int centerX = ((int)[self bounds].size.width) >> 1;
    int centerY = ((int)[self bounds].size.height) >> 1;
    [self setCenterWithPixel:GPointMake(centerX - dX, centerY - dY)];
}

//-- Methods corresponding to Google Maps Javascript API methods ---------------
- (int) getZoom {
    return [[self evalJS:@"map.getZoom();"] intValue];
}
//------------------------------------------------------------------------------
- (void) setZoom:(int)level {
    [self evalJS:[NSString stringWithFormat:@"map.setZoom(%d);", level]];
    
    if (self.mapDelegate)
        [self.mapDelegate mapZoomUpdatedTo:level];
}
//------------------------------------------------------------------------------
- (void) zoomIn {
    [self evalJS:@"map.zoomIn();"];
    
    if (self.mapDelegate)
        [self.mapDelegate mapZoomUpdatedTo:[self getZoom]];
}
//------------------------------------------------------------------------------
- (void) zoomOut {
    [self evalJS:@"map.zoomOut();"];
    
    if (self.mapDelegate)
        [self.mapDelegate mapZoomUpdatedTo:[self getZoom]];
}
//------------------------------------------------------------------------------
- (void) setCenterWithPixel:(GPoint)pixel {
    NSString *script = 
        [NSString stringWithFormat:
         @"var newCenterPixel = new GPoint(%ld, %ld);"
          "var newCenterLatLng = map.fromContainerPixelToLatLng(newCenterPixel);"
          "map.setCenter(newCenterLatLng);", 
         pixel.x, pixel.y];
    
    [self evalJS:script];
    
    if (self.mapDelegate)
        [self.mapDelegate mapCenterUpdatedToPixel:pixel];
}
//------------------------------------------------------------------------------
- (void) setCenterWithLatLng:(GLatLng)latlng {
    NSString *script = 
        [NSString stringWithFormat:
         @"var newCenterLatLng = map.fromContainerPixelToLatLng(newCenterPixel);"
          "map.setCenter(new GLatLng(%lf, %lf));", 
         latlng.lat, latlng.lng];
    
    [self evalJS:script];
    
    if (self.mapDelegate)
        [self.mapDelegate mapCenterUpdatedToLatLng:latlng];
}
//------------------------------------------------------------------------------
- (GLatLng) getCenterLatLng {
    // the result should be in the form "(<latitude>, <longitude>)"
    NSString *centerStr = [self evalJS:@"map.getCenter().toString();"];
    
    GLatLng latlng;
    sscanf([centerStr UTF8String], "(%lf, %lf)", &latlng.lat, &latlng.lng);
    
    return latlng;
}
//------------------------------------------------------------------------------
- (GPoint) getCenterPixel {
    // the result should be in the form "(<x>, <y>)"
    NSString *centerStr = 
    	[self evalJS:@"map.fromLatLngToContainerPixel(map.getCenter()).toString();"];
    
    GPoint pixel;
    sscanf([centerStr UTF8String], "(%ld, %ld)", &pixel.x, &pixel.y);
    
    return pixel;
}
//------------------------------------------------------------------------------
- (void) panToCenterWithPixel:(GPoint)pixel {
    NSString *script = 
        [NSString stringWithFormat:
         @"var newCenterPixel = new GPoint(%ld, %ld);"
          "var newCenterLatLng = map.fromContainerPixelToLatLng(newCenterPixel);"
          "map.panTo(newCenterLatLng);", 
         pixel.x, pixel.y];
    
    [self evalJS:script];
    
    if (self.mapDelegate) {
       // [self.mapDelegate mapCenterUpdatedToPixel:[self getCenterPixel]];
		[self.mapDelegate mapCenterPannedToPixel:pixel];
    }
}

//------------------------------------------------------------------------------
- (void) panToBrowserTipWithPixel:(GPoint)pixel {
	GPoint newPixelWithOffset = GPointMake(pixel.x + 75, pixel.y - 55);
    NSString *script = 
	[NSString stringWithFormat:
	 @"var newCenterPixel = new GPoint(%ld, %ld);"
	 "var newCenterLatLng = map.fromContainerPixelToLatLng(newCenterPixel);"
	 "map.panTo(newCenterLatLng);", 
	 newPixelWithOffset.x, newPixelWithOffset.y];
    
    [self evalJS:script];
    
    if (self.mapDelegate) {
		// [self.mapDelegate mapCenterUpdatedToPixel:[self getCenterPixel]];
		[self.mapDelegate mapCenterPannedToPixel:newPixelWithOffset];
    }
}
//------------------------------------------------------------------------------
- (GLatLng) fromContainerPixelToLatLng:(GPoint)pixel {
    NSString *script = 
    	[NSString stringWithFormat:
	 	 @"map.fromContainerPixelToLatLng(new GPoint(%ld, %ld)).toString();", 
     	 pixel.x, pixel.y];
    
    NSString *latlngStr = [self evalJS:script];
    
    GLatLng latlng;
    sscanf([latlngStr UTF8String], "(%lf, %lf)", &latlng.lat, &latlng.lng);
    
    return latlng;
}
//------------------------------------------------------------------------------
- (GPoint) fromLatLngToContainerPixel:(GLatLng)latlng {
    NSString *script = 
        [NSString stringWithFormat:
         @"map.fromLatLngToContainerPixel(new GLatLng(%lf, %lf)).toString();", 
         latlng.lat, latlng.lng];
    
    NSString *pixelStr = [self evalJS:script];
    
    GPoint pixel;
    sscanf([pixelStr UTF8String], "(%ld, %ld)", &pixel.x, &pixel.y);
    
    return pixel;
}
//------------------------------------------------------------------------------
- (int) getBoundsZoomLevel:(GLatLngBounds)bounds {
    NSString *script;
    
    script = 
        [NSString stringWithFormat:
         @"map.getBoundsZoomLevel(new GLatLngBounds(new GLatLng(%lf, %lf), new GLatLng(%lf, %lf))).toString();", 
         bounds.minLat, bounds.minLng, bounds.maxLat, bounds.maxLng];
    
    NSString *zoomLevelStr = [self evalJS:script];
    
    int zoomLevel;
    sscanf([zoomLevelStr UTF8String], "%d", &zoomLevel);
    
    return zoomLevel;
}
//------------------------------------------------------------------------------
- (void) setMapType:(NSString*)mapType {
    [self evalJS:[NSString stringWithFormat:@"map.setMapType(%@);", mapType]];
}


//-- Private Methods -----------------------------------------------------------
- (void) loadMap {
    int width = (int) self.frame.size.width;
    int height = (int) self.frame.size.height;
	
	GLatLng startLocation;
	if (self.mapDelegate)
	{
		startLocation = [self.mapDelegate provideStartingLocation];
	}
	int zoom = DEFAULT_ZOOM_LEVEL;
	if (self.mapDelegate)
	{
		zoom = [self.mapDelegate provideStartingZoom];
	}
    NSString *urlStr = 
	[NSString stringWithFormat:
	 @"http://actionfeed.org/iPhone/mapview.html?width=%d&height=%d&zoom=%d&latitude=%lf&longitude=%lf", 
	 width, height, zoom, startLocation.lat, startLocation.lng];
    
    [self loadRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:urlStr]]];
}//------------------------------------------------------------------------------

- (void)dealloc
{
	[(NSObject *)mapDelegate release];
	
	[super dealloc];
}

@end
