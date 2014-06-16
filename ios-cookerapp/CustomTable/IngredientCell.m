//
//  IngredientCell.m
//  RecipeApp
//
//  Created by Diego Cibils on 4/15/14.
//  Copyright (c) 2014 KONA Cloud. All rights reserved.
//

#import "IngredientCell.h"

@implementation IngredientCell

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
    }
    return self;
}

- (void)awakeFromNib
{
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
