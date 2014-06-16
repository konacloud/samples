//
//  RecipeAppDelegate.m
//  RecipeAppDelegate
//
//  Created by Diego Cibils on 7/12/13.
//  Copyright (c) 2013 KONA Cloud. All rights reserved.
//

#import "RecipeAppDelegate.h"
#import "KNResponseObject.h"

@implementation RecipeAppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    // Override point for customization after application launch.
  //  UIColor *verde = [UIColor colorWithRed:0.4 green:0.529 blue:0.067 alpha:1.0];
    UIColor * color = [UIColor colorWithRed:236/255.0f green:52/255.0f blue:0/255.0f alpha:1.0f];    //UIColor *verde = [UIColor colorWithRed:0.761 green:0.827 blue:0.353 alpha:1.000];
    NSShadow *shadow = [NSShadow new];
    shadow.shadowColor = [UIColor colorWithRed:0.0 green:0.0 blue:0.0 alpha:0.8];
    shadow.shadowOffset = CGSizeMake(0, 0);
    [[UINavigationBar appearance] setTitleTextAttributes: [NSDictionary dictionaryWithObjectsAndKeys:
                                                           [UIColor colorWithRed:245.0/255.0 green:245.0/255.0 blue:245.0/255.0 alpha:1.0], NSForegroundColorAttributeName,
                                                           shadow, NSShadowAttributeName,
                                                           [UIFont fontWithName:@"Montserrat-Bold" size:22.0], NSFontAttributeName, nil]];
    [[UINavigationBar appearance] setBarTintColor:color];
    //[[UINavigationBar appearance] setTranslucent:YES];
    [[UIApplication sharedApplication] setStatusBarStyle:UIStatusBarStyleLightContent];

  //  [[UITabBar appearance] setBarTintColor:verde];
  //  [[UITabBar appearance] setBarStyle:UIBarStyleBlackTranslucent];
    UIColor *gray = [UIColor colorWithWhite:0.498 alpha:1.000];

    [[UITextField appearance] setTintColor:gray];
    [[UITextView appearance] setTintColor:gray];
    
    self.window.tintColor = [UIColor whiteColor];
    
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(receivedRequestErrorNotification:)
                                                 name:@"RequestErrorNotification"
                                               object:nil];
    
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(receivedServiceErrorNotification:)
                                                 name:@"ServiceErrorNotification"
                                               object:nil];
    
    return YES;
}

- (void) receivedServiceErrorNotification:(NSNotification *) notification
{
    NSLog(@"RequestErrorNotification %@", notification.object);
    KNResponseObject* error = (KNResponseObject*)notification.object;
    [[[UIAlertView alloc] initWithTitle:@"Service Error" message:error.message delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil] show];
    
}

- (void) receivedRequestErrorNotification:(NSNotification *) notification
{
    NSLog (@"ServiceErrorNotification %@", notification.object);
    NSError* error = (NSError*)notification.object;
    [[[UIAlertView alloc] initWithTitle:@"Network Error" message:error.localizedDescription delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil] show];
}


- (void)applicationWillResignActive:(UIApplication *)application
{
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}

@end
