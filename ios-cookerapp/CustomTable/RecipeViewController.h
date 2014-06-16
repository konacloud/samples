//
//  RecipeViewController.h
//  RecipeViewController
//
//  Created by Diego Cibils on 4/10/14.
//  Copyright (c) 2014 KONA Cloud. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "RecipeManager.h"

@interface RecipeViewController : UITableViewController<UITableViewDataSource, UITableViewDelegate, UIActionSheetDelegate, UIImagePickerControllerDelegate,UINavigationControllerDelegate, CommunicatorDelegate>

@property (weak, nonatomic) IBOutlet UITableViewCell *nameCell;
@property (weak, nonatomic) IBOutlet UITableViewCell *prepTimeCell;
@property (weak, nonatomic) IBOutlet UITextField *nameText;
@property (weak, nonatomic) IBOutlet UITextField *prepTimeText;
@property (weak, nonatomic) IBOutlet UIImageView *recipeImage;

@property (strong) NSMutableArray *ingredients;
@end
