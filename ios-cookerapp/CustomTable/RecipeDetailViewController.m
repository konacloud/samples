//
//  RecipeDetailViewController.m
//  RecipeApp
//
//  Created by Diego Cibils on 23/12/13.
//  Copyright (c) 2013 KONA Cloud. All rights reserved.
//

#import "RecipeDetailViewController.h"
#import "UIImageView+AFNetworking.h"

@interface RecipeDetailViewController ()

@end

@implementation RecipeDetailViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
  
    UIFont* fontBold =  [UIFont fontWithName:@"Montserrat-Bold" size:18.0];
    UIFont* fontRegular =  [UIFont fontWithName:@"Montserrat-Regular" size:14.0];
    
    
	self.title = self.recipe.name;
    self.prepTimeLabel.text = self.recipe.preptime;
    self.prepTimeLabel.font = fontRegular;

    [self.recipeImageView setImageWithURL:[NSURL URLWithString:self.recipe.image]];
    
    NSMutableString *ingredientsText = [NSMutableString string];
    for (NSString* ingredient in self.recipe.ingredients) {
        [ingredientsText appendFormat:@"%@\n", ingredient];
    }
    self.ingredientsTextView.text = ingredientsText;
    self.ingredientsTextView.font = fontRegular;
    
    self.ingredientsLabel.font = fontBold;
    
    
    UIBarButtonItem *shareItem = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemAction target:self action:nil];
    UIBarButtonItem *cameraItem = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemCamera target:self action:nil];
    
    NSArray *actionButtonItems = @[shareItem];
   // self.navigationItem.rightBarButtonItems = actionButtonItems;
    
}

-(void)viewDidAppear:(BOOL)animated {

    [super viewDidAppear:animated];
    self.navigationItem.backBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"" style:UIBarButtonItemStylePlain target:nil action:nil];

}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
