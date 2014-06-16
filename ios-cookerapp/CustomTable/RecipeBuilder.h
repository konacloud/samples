//
//  RecipeBuilder.h
//  RecipeApp
//
//  Created by Diego Cibils on 2/14/14.
//  Copyright (c) 2014 KONA Cloud. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface RecipeBuilder : NSObject
+ (NSArray *)recipesFromJSON:(NSData *)objectNotation error:(NSError **)error;
@end
