//
//  RecipeBuilder.m
//  RecipeApp
//
//  Created by Diego Cibils on 2/14/14.
//  Copyright (c) 2014 KONA Cloud. All rights reserved.
//

#import "RecipeBuilder.h"
#import "Recipe.h"

@implementation RecipeBuilder

+ (NSArray *)recipesFromJSON:(NSDictionary *)recipes error:(NSError **)error
{
    NSError *localError = nil;
    //NSDictionary *parsedObject = [NSJSONSerialization JSONObjectWithData:objectNotation options:0 error:&localError];
    
    if (localError != nil) {
        *error = localError;
        return nil;
    }
    
    NSMutableArray *recipesArray = [NSMutableArray new];
    
  //  NSArray *results = [parsedObject valueForKey:@"data"];
    NSLog(@"Count %lu", (unsigned long)recipes.count);
    
    for (NSDictionary *recipeDic in recipes) {
        Recipe *recipe = [Recipe new];
        
        [recipe setUid:[recipeDic valueForKeyPath:@"_id"]];
        for (NSString *key in recipeDic) {
            if ([recipe respondsToSelector:NSSelectorFromString(key)]) {
                [recipe setValue:[recipeDic valueForKey:key] forKey:key];
            }
        }
        
        [recipesArray addObject:recipe];
    }
    
    return recipesArray;
}

@end
