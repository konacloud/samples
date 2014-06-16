//
//  RecipeManager.h
//  RecipeApp
//
//  Created by Diego Cibils on 2/14/14.
//  Copyright (c) 2014 KONA Cloud. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreLocation/CoreLocation.h>
#import "Recipe.h"
#import "KNResponseObject.h"

@protocol CommunicatorDelegate;

@interface RecipeManager : NSObject

+(void)fetchAllRecipesWithDelegate:(id)delegate;
+(void)uploadFileWithData:(NSData*)data withName:(NSString*)fileName withMimeType:(NSString*)mimeType withDelegate:(id<CommunicatorDelegate>) delegate;
+(void)saveRecipeWithRecipe:(Recipe*)recipe withDelegate:(id<CommunicatorDelegate>) delegate;
+(void)deleteRecipeWithId:(NSNumber*)uid withDelegate:(id<CommunicatorDelegate>) delegate;

@end

@protocol CommunicatorDelegate

-(void)fetchingRecipesFailedWithError:(NSError*)error;
-(void)didReceiveRecipies:(NSArray*)recipes;
-(void)didFinishServiceUploadFileWithResult:(id)response;
-(void)didFinishServiceSaveRecipeWithResult:(id)response;
-(void)didFinishServiceDeleteRecipeWithResult:(KNResponseObject*)response;

@end