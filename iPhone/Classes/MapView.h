

#import <UIKit/UIKit.h>

#import "MapWebView.h"

@interface MapView : UIView {
@protected
    MapWebView *mMapWebView;
    int         mTouchMovedEventCounter;
    CGPoint     mLastTouchLocation;
	CGPoint		mBeginTouchLocation;
	CGPoint		panCenter;
	int			centerDx;
	int			centerDy;
    CGFloat     mLastTouchSpacing;
	CGFloat		mBeginTouchSpacing;
    SEL         mOnClickHandler;
	BOOL		isFirstMove;
	id <MapWebViewDelegate> mapDelegate;
	BOOL useUPAddress;
	int			mapViewId;
	BOOL fuckedMode;
}
@property (retain, nonatomic, getter = map) MapWebView* mMapWebView;
@property (readwrite, getter = onClickHandler, setter = onClickHandler:) SEL mOnClickHandler;
@property (retain, nonatomic) id <MapWebViewDelegate> mapDelegate;
@property (nonatomic) BOOL isFirstMove;
@property (nonatomic) BOOL useUPAddress;
@property (nonatomic) int mapViewId;
@property (nonatomic) BOOL fuckedMode;

- (id) initWithFrame:(CGRect)frame delegate:(id <MapWebViewDelegate>) aMapDelegate;
- (void)panForBrowserWindowAtPoint:(CGPoint)point;
- (void)setCenterAfterPan;
@end
