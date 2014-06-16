//
//  RecipeDetailViewController.h
//  RecipeApp
//
//  Created by Diego Cibils on 23/12/13.
//  Copyright (c) 2013 KONA Cloud. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Recipe.h"

@interface RecipeDetailViewController : UIViewController

@property (weak, nonatomic) IBOutlet UIImageView *recipeImageView;
@property (weak, nonatomic) IBOutlet UILabel *prepTimeLabel;
@property (weak, nonatomic) IBOutlet UITextView *ingredientsTextView;
@property (weak, nonatomic) IBOutlet UILabel *ingredientsLabel;

@property (nonatomic, strong) Recipe *recipe;

@end
