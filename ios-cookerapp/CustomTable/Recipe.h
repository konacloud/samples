//
//  Recipe.h
//  RecipeApp
//
//  Created by Diego Cibils on 25/12/13.
//  Copyright (c) 2013 KONA Cloud. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Recipe : NSObject

@property (nonatomic, strong) NSNumber *uid; // id of recipe
@property (nonatomic, strong) NSString *name; // name of recipe
@property (nonatomic, strong) NSString *preptime; // preparation time
@property (nonatomic, strong) NSString *image; // image filename of recipe
@property (nonatomic, strong) NSArray *ingredients; // ingredients of recipe

@end
