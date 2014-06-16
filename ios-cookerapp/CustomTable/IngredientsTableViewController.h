//
//  IngredientsTableViewController.h
//  RecipeApp
//
//  Created by Diego Cibils on 4/11/14.
//  Copyright (c) 2014 KONA Cloud. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "RecipeViewController.h"

@interface IngredientsTableViewController : UITableViewController

@property (strong) NSMutableArray *tableData;
@property (weak) RecipeViewController *recipeViewController;
@end
