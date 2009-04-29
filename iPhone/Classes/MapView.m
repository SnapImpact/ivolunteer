

#import "MapView.h"
#import "MapWebView.h"
#import "MapViewConstants.h"

#define DROPPED_TOUCH_MOVED_EVENTS_RATIO  (0.8)
#define ZOOM_OUT_TOUCH_SPACING_RATIO_LIMIT (0.5)
#define ZOOM_IN_TOUCH_SPACING_RATIO_LIMIT (2.0)
#define ZOOM_SPACING_THRESHOLD (0.4)

@interface MapView (Private)
- (void)	resetTouches;
- (CGFloat)	eucledianDistanceFromPoint:(CGPoint)from toPoint:(CGPoint)to;
- (void)	setPanningModeWithLocation:(CGPoint)location;
- (void)	setZoomingModeWithSpacing:(CGFloat)spacing;
- (BOOL)	isPanning;
- (BOOL)	isZooming;
@end

@implementation MapView

//-- Public Methods ------------------------------------------------------------
@synthesize mMapWebView;
@synthesize mOnClickHandler;
@synthesize mapDelegate;
@synthesize isFirstMove;
@synthesize useUPAddress;
@synthesize mapViewId;
@synthesize fuckedMode;
//------------------------------------------------------------------------------

- (id) initWithFrame:(CGRect)frame delegate:(id <MapWebViewDelegate>) aMapDelegate
{
	if (! (self = [super initWithFrame:frame]))
        return nil;
    self.mapDelegate = aMapDelegate;
    self.onClickHandler = nil;
    self.autoresizesSubviews = YES;
    self.multipleTouchEnabled = YES;
    isFirstMove = YES;
   mMapWebView = [[[MapWebView alloc] initWithFrame:CGRectMake(-150,-150,620,666) 
											delegate:aMapDelegate 
									  delegateForMap:aMapDelegate] autorelease];
    [self addSubview:mMapWebView];
    
    [self resetTouches]; 
    
    return self;
	
	
}

//------------------------------------------------------------------------------


//-- Touch Events Handling Methods ---------------------------------------------
- (void) touchesCanceled {
    [self resetTouches];
}
//------------------------------------------------------------------------------
- (void) touchesBegan:(NSSet*)touches withEvent:(UIEvent*)event {
    mTouchMovedEventCounter = 0;
    
    NSSet *allTouches = [event allTouches];
    
    switch ([allTouches count]) {
        case 1: {
            // potential pan gesture
            UITouch *touch = [[allTouches allObjects] objectAtIndex:0];
			mBeginTouchLocation = [touch locationInView:self];
            [self setPanningModeWithLocation:[touch locationInView:self]];
        } break;
            
        case 2: {
            // potential zoom gesture
            UITouch *touch0 = [[allTouches allObjects] objectAtIndex:0];
            UITouch *touch1 = [[allTouches allObjects] objectAtIndex:1];
			
            CGFloat spacing = 
            [self eucledianDistanceFromPoint:[touch0 locationInView:self] 
                                     toPoint:[touch1 locationInView:self]];
			mBeginTouchSpacing = spacing;
            [self setZoomingModeWithSpacing:spacing];
        } break;
            
        default:
            [self resetTouches];
            break;
    }
}
//------------------------------------------------------------------------------
- (void) touchesMoved:(NSSet*)touches withEvent:(UIEvent*)event {
    
    NSSet *allTouches = [event allTouches];
    
    switch ([allTouches count]) {
        case 1: {
            // potential pan gesture
            if (! [self isPanning]) {
                [self resetTouches];
                break;
            }
            UITouch *touch = [[allTouches allObjects] objectAtIndex:0];
            CGPoint currentLocation = [touch locationInView:self];
            int dX = (int)(currentLocation.x - mLastTouchLocation.x);
            int dY = (int)(currentLocation.y - mLastTouchLocation.y);
            [self setPanningModeWithLocation:[touch locationInView:self]];
			
			mMapWebView.center = CGPointMake(mMapWebView.center.x + dX, 
											 mMapWebView.center.y+ dY);
			
			
        } break;
            
        case 2: {
            // potential zoom gesture
            if (! [self isZooming]) {
                [self resetTouches];
                break;
            }
            UITouch *touch0 = [[allTouches allObjects] objectAtIndex:0];
            UITouch *touch1 = [[allTouches allObjects] objectAtIndex:1];
            CGFloat spacing = 
            [self eucledianDistanceFromPoint:[touch0 locationInView:self] 
                                     toPoint:[touch1 locationInView:self]];
            CGFloat spacingRatio = spacing / mBeginTouchSpacing;
			
			if (spacingRatio <= ZOOM_IN_TOUCH_SPACING_RATIO_LIMIT 
				&& spacingRatio >= ZOOM_OUT_TOUCH_SPACING_RATIO_LIMIT)
			{
				[self setZoomingModeWithSpacing:spacing];
				
				CGAffineTransform transform = CGAffineTransformMakeScale(spacingRatio, spacingRatio);
				mMapWebView.transform = transform;
			}
        } break;
            
        default:
            [self resetTouches];
            break;
    }
}

//------------------------------------------------------------------------------
- (void) touchesEnded:(NSSet*)touches withEvent:(UIEvent*)event {
    
    NSSet *allTouches = [event allTouches];
    
    switch ([allTouches count]) {
        case 1: {
			int dX = (int)(mLastTouchLocation.x - mBeginTouchLocation.x);
            int dY = (int)(mLastTouchLocation.y - mBeginTouchLocation.y);
			if([self isPanning])
			{
				if (fuckedMode)
				{
					[mMapWebView moveByDx:dX dY:dY];
				}
				else
				{
					[mMapWebView moveByDx:dX + 150 dY:dY + 150];
				}
				
				mMapWebView.center = CGPointMake(160,183);
				mLastTouchLocation = CGPointMake(-1, -1);
			}
        } break;
			
		case 2: {
			if([self isZooming])
			{
				GLatLng center = [mMapWebView getCenterLatLng];
				CGFloat spacingRatio = mLastTouchSpacing / mBeginTouchSpacing;
				if (spacingRatio  <= 1 - ZOOM_SPACING_THRESHOLD)
				{
					[mMapWebView zoomOut];
					[mMapWebView setCenterWithLatLng:center];
					
					if (fuckedMode)
					{
						[mMapWebView moveByDx:0 dY:0];
					}
					else
					{
						[mMapWebView moveByDx:225 dY:225];
					}
				}
				
				else if (spacingRatio >= 1 + ZOOM_SPACING_THRESHOLD) {
					
					[mMapWebView zoomIn];
					[mMapWebView setCenterWithLatLng:center];
					[mMapWebView moveByDx:0 dY:0];
					
				}
				
				mLastTouchSpacing = -1;
				mMapWebView.transform = CGAffineTransformIdentity;
			}
		} break;
    }
    isFirstMove = NO;
    [self resetTouches];
}

- (void)panForBrowserWindowAtPoint:(CGPoint)point
{
	GPoint mapCenter = [mMapWebView getCenterPixel];
	if (fuckedMode)
	{
		centerDx = mapCenter.x - point.x;
		centerDy =  mapCenter.y - point.y;
	}
	else
	{
		centerDx = mapCenter.x - point.x + 150;
		centerDy =  mapCenter.y - point.y + 150;
	}

	if (isFirstMove)
	{
		NSLog(@"Compensating browser pan for inital position");
		centerDx += -1 * MARKER_ICON_X_OFFSET;
		centerDy += -1 * MARKER_ICON_Y_OFFSET;
	}
	panCenter = CGPointMake(mMapWebView.center.x + centerDx - 75, 
							  mMapWebView.center.y + centerDy + 55);
	NSLog(@"Pan center dx=%i, dy=%i", centerDx, centerDy);
	[UIView beginAnimations:@"pan animation" context:nil];
	[UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];
	[UIView setAnimationDuration:0.5];
	[UIView setAnimationDelegate:self];
	[UIView setAnimationDidStopSelector:@selector(setCenterAfterPan)];
	mMapWebView.center = panCenter;
	[UIView commitAnimations];
	isFirstMove = NO;
}

//-- Private Methods -----------------------------------------------------------
- (void)setCenterAfterPan
{
	if (fuckedMode)
	{
		[mMapWebView moveByDx:centerDx - 75 dY:centerDy + 55];
	}
	else
	{
		[mMapWebView moveByDx:centerDx + 150 - 75 dY:centerDy + 150 + 55];
	}
	mMapWebView.center = CGPointMake(160,183);
}

- (void) resetTouches {
    mLastTouchLocation = CGPointMake(-1, -1);
    mLastTouchSpacing = -1;
}
//------------------------------------------------------------------------------
- (CGFloat) eucledianDistanceFromPoint:(CGPoint)from toPoint:(CGPoint)to {
    float dX = to.x - from.x;
    float dY = to.y - from.y;
    
    return sqrt(dX * dX + dY * dY);
}
//------------------------------------------------------------------------------
- (void) setPanningModeWithLocation:(CGPoint)location {
    mLastTouchLocation = location;
    mLastTouchSpacing = -1;
}
//------------------------------------------------------------------------------
- (void) setZoomingModeWithSpacing:(CGFloat)spacing {
    mLastTouchLocation = CGPointMake(-1, -1);
    mLastTouchSpacing = spacing;
}
//------------------------------------------------------------------------------
- (BOOL) isPanning {
    return mLastTouchLocation.x > 0 ? YES : NO;
}
//------------------------------------------------------------------------------
- (BOOL) isZooming {
    return mLastTouchSpacing > 0 ? YES : NO;
}
//------------------------------------------------------------------------------


- (void) dealloc {
	self.mMapWebView = nil;
    [mapDelegate release];
	
	
	[super dealloc];
}


@end
