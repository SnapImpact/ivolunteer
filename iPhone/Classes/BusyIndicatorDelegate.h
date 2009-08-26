@protocol BusyIndicatorDelegate

@required
- (void)stopAnimating;
- (void)startAnimatingWithMessage:(NSString *)message;
- (void)startAnimatingWithMessage:(NSString *)message atBottom:(BOOL)atBottom;
- (BOOL)isBusy;
- (void)showFloatingView;
- (void)hideFloatingView;

@end