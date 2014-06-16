//
//  RecipeTableCell.h
//  RecipeApp
//
//  Created by Diego Cibils on 11/12/13.
//  Copyright (c) 2013 KONA Cloud. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface RecipeTableCell : UITableViewCell

@property (nonatomic, weak) IBOutlet UILabel *nameLabel;
@property (nonatomic, weak) IBOutlet UILabel *prepTimeLabel;
@property (nonatomic, weak) IBOutlet UIImageView *thumbnailImageView;

@end
