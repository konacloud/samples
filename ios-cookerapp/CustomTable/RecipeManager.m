//
//  RecipeManager.m
//  RecipeApp
//
//  Created by Diego Cibils on 2/14/14.
//  Copyright (c) 2014 KONA Cloud. All rights reserved.
//

#import "RecipeManager.h"
#import "RecipeBuilder.h"
#import "AFNetworking.h"
#import "Recipe.h"
#import "KNResponseObject.h"

#define K_SERVICE_API_BASE_URL @"http://app.konacloud.io/api/diego/CookerApp/"
#define K_SERVICE_API_TOKEN @"89e36f59-2cf0-479d-a332-355393a1ff28"

@implementation RecipeManager


+ (void)fetchAllRecipesWithDelegate:(id<CommunicatorDelegate>)delegate {
    NSString *url = [NSString stringWithFormat:@"%@mr_Recipie", K_SERVICE_API_BASE_URL];

    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    [manager.requestSerializer setValue:K_SERVICE_API_TOKEN forHTTPHeaderField:@"X-AUTH-TOKEN"];
    
    [manager GET:url parameters:nil success:^(AFHTTPRequestOperation *operation, id responseObject) {
        NSLog(@"JSON: %@", responseObject);
        KNResponseObject *response = [[KNResponseObject alloc] initWithDict:responseObject];
        if ([response didFinishWithSuccess]) {
            [delegate didReceiveRecipies:[RecipeBuilder recipesFromJSON:response.data error:nil]];
        } else {
            [[NSNotificationCenter defaultCenter] postNotificationName:@"ServiceErrorNotification" object:response];
            [delegate fetchingRecipesFailedWithError:nil];
        }
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        NSLog(@"Error: %@", error);
        [[NSNotificationCenter defaultCenter] postNotificationName:@"RequestErrorNotification" object:error];
        [delegate fetchingRecipesFailedWithError:error];
    }];
    
}

+(void)uploadFileWithData:(NSData*)data withName:(NSString*)fileName withMimeType:(NSString*)mimeType withDelegate:(id<CommunicatorDelegate>) delegate {
    NSString *url = [NSString stringWithFormat:@"http://bucket.konacloud.io/external/api/bucket/diego/CookerApp/images"];

    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    manager.securityPolicy.allowInvalidCertificates = YES;
    manager.requestSerializer = [AFJSONRequestSerializer serializer];
    
    [manager
     POST: url
     parameters:nil
     constructingBodyWithBlock:^(id<AFMultipartFormData> formData) {
         [formData appendPartWithFileData:data
                                     name:fileName
                                 fileName:fileName
                                 mimeType:mimeType];
     }
     success:^(AFHTTPRequestOperation *operation, id responseObject){
         NSLog(@"Submit response data: %@", responseObject);
         
         [delegate didFinishServiceUploadFileWithResult:responseObject];
     }
     failure:^(AFHTTPRequestOperation *operation, NSError *error){
         NSLog(@"Error: %@", error);
         [[NSNotificationCenter defaultCenter] postNotificationName:@"ServiceErrorNotification" object:error];
     }
     ];
}

+(void)saveRecipeWithRecipe:(Recipe*)recipe withDelegate:(id<CommunicatorDelegate>) delegate {

    NSString *url = [NSString stringWithFormat:@"%@mr_Recipie", K_SERVICE_API_BASE_URL];
    
    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    manager.securityPolicy.allowInvalidCertificates = YES;
    manager.requestSerializer = [AFJSONRequestSerializer serializer];
    [manager.requestSerializer setValue:K_SERVICE_API_TOKEN forHTTPHeaderField:@"X-AUTH-TOKEN"];

    NSDictionary *parameters = @{@"name": recipe.name,
                                 @"preptime": recipe.preptime,
                                 @"image": recipe.image,
                                 @"ingredients": recipe.ingredients};
    [manager
     POST: url
     parameters:parameters
     success:^(AFHTTPRequestOperation *operation, id responseObject){
         NSLog(@"JSON: %@", responseObject);
         KNResponseObject *response = [[KNResponseObject alloc] initWithDict:responseObject];
         if ([response didFinishWithSuccess]) {
             [delegate didFinishServiceSaveRecipeWithResult:responseObject];
         } else {
             [[NSNotificationCenter defaultCenter] postNotificationName:@"ServiceErrorNotification" object:response];
         }
     }
     failure:^(AFHTTPRequestOperation *operation, NSError *error){
         NSLog(@"Error: %@", error);
         [[NSNotificationCenter defaultCenter] postNotificationName:@"RequestErrorNotification" object:error];
     }
     ];
}

+(void)deleteRecipeWithId:(NSNumber*)uid withDelegate:(id<CommunicatorDelegate>) delegate {
    
    NSString *url = [NSString stringWithFormat:@"%@r_deleteRecipe", K_SERVICE_API_BASE_URL];
    
    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    manager.securityPolicy.allowInvalidCertificates = YES;
    manager.requestSerializer = [AFJSONRequestSerializer serializer];
    [manager.requestSerializer setValue:K_SERVICE_API_TOKEN forHTTPHeaderField:@"X-AUTH-TOKEN"];

    NSDictionary *parameters = @{@"uid": uid};
    
    [manager
     POST: url
     parameters:parameters
     success:^(AFHTTPRequestOperation *operation, id responseObject){
         NSLog(@"JSON: %@", responseObject);
         KNResponseObject *response = [[KNResponseObject alloc] initWithDict:responseObject];
         if ([response didFinishWithSuccess]) {
             [delegate didFinishServiceDeleteRecipeWithResult:response];
         } else {
             [[NSNotificationCenter defaultCenter] postNotificationName:@"ServiceErrorNotification" object:response];
         }
     }
     failure:^(AFHTTPRequestOperation *operation, NSError *error){
         NSLog(@"Error: %@", error);
         [[NSNotificationCenter defaultCenter] postNotificationName:@"RequestErrorNotification" object:error];
     }
     ];
}



@end
