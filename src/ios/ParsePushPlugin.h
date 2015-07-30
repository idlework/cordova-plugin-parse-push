#import <Cordova/CDV.h>
#import "AppDelegate.h"

@interface ParsePushPlugin: CDVPlugin

@property NSString *callbackId;

- (void)initialize:(CDVInvokedUrlCommand*)command;
- (void)subscribe:(CDVInvokedUrlCommand *)command;
- (void)unsubscribe:(CDVInvokedUrlCommand *)command;

@end

@interface AppDelegate (ParsePushPlugin)
@end
