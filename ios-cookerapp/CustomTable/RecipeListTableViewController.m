//
//  RecipeListTableViewController.h
//  RecipeListTableViewController
//
//  Created by Diego Cibils on 7/12/13.
//  Copyright (c) 2013 KONA Cloud. All rights reserved.
//

#import "RecipeListTableViewController.h"
#import "RecipeTableCell.h"
#import "RecipeDetailViewController.h"
#import "Recipe.h"
#import "RecipeManager.h"
#import "UIImageView+AFNetworking.h"
#import "RecipeViewController.h"
#import "IngredientsTableViewController.h"

@interface RecipeListTableViewController ()

@end

@implementation RecipeListTableViewController
{
    NSMutableArray *recipesArray;
    NSArray *searchResults;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.navigationItem.backBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:@""
                                                                               style:UIBarButtonItemStylePlain
                                                                              target:nil
                                                                              action:nil];
    recipesArray = [[NSArray alloc] init];
    UIBarButtonItem *anotherButton = [[UIBarButtonItem alloc] initWithTitle:@"Add"
                                                                      style:UIBarButtonItemStylePlain
                                                                     target:self
                                                                    action:@selector(addRecipe)];
    
    self.navigationItem.rightBarButtonItem = anotherButton;
    [self loadRecipes];

    [self.refreshControl addTarget:self action:@selector(loadRecipes) forControlEvents:UIControlEventValueChanged];

}

-(void)loadRecipes {
    [RecipeManager fetchAllRecipesWithDelegate:self];
    [self.refreshControl endRefreshing];
}

-(void)addRecipe {
    
    UIStoryboard* storyboard = [UIStoryboard storyboardWithName:@"Main" bundle:nil];
    RecipeViewController *controller = (RecipeViewController*)[storyboard instantiateViewControllerWithIdentifier: @"RecipeViewController"];

    [self.navigationController pushViewController:controller animated:YES];
    
}

-(void)didReceiveRecipies:(NSArray*)recipes {
    self->recipesArray = recipes;
    [self.tableView reloadData];
    
}

-(void)fetchingRecipesFailedWithError:(NSError*)error {
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    if (tableView == self.searchDisplayController.searchResultsTableView) {
        return [searchResults count];
        
    } else {
        return [recipesArray count];
    }
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 170;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"CustomTableCell";
    RecipeTableCell *cell = (RecipeTableCell *)[self.tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    
    if (cell == nil) {
        cell = [[RecipeTableCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier];
    }
    
    // Display recipe in the table cell
    Recipe *recipe = nil;
    if (tableView == self.searchDisplayController.searchResultsTableView) {
        recipe = [searchResults objectAtIndex:indexPath.row];
    } else {
        recipe = [recipesArray objectAtIndex:indexPath.row];
    }
    
    cell.nameLabel.text = recipe.name;
    [cell.thumbnailImageView  setImageWithURL:[NSURL URLWithString:recipe.image]];
    cell.prepTimeLabel.text = recipe.preptime;
    
    UIView *backView = [[UIView alloc] initWithFrame:CGRectZero];
    backView.backgroundColor = [UIColor clearColor];
    cell.backgroundView = backView;
    
    return cell;
}

-(BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath {
    return YES;
}

// Override to support editing the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        // Delete the row from the data source
        Recipe* recipe = [recipesArray objectAtIndex:indexPath.row];
        [RecipeManager deleteRecipeWithId:recipe.uid withDelegate:self];
        
        [recipesArray removeObjectAtIndex:indexPath.row];
        [tableView deleteRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationFade];
        
    } else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
    }
}

-(void)didFinishServiceDeleteRecipeWithResult:(KNResponseObject*)response {
 
    if ([response didFinishWithSuccess]) {
    
    }
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    if ([segue.identifier isEqualToString:@"showRecipeDetail"]) {
        NSIndexPath *indexPath = nil;
        Recipe *recipe = nil;
        
        if (self.searchDisplayController.active) {
            indexPath = [self.searchDisplayController.searchResultsTableView indexPathForSelectedRow];
            recipe = [searchResults objectAtIndex:indexPath.row];
        } else {
            indexPath = [self.tableView indexPathForSelectedRow];
            recipe = [recipesArray objectAtIndex:indexPath.row];
        }
        
        RecipeDetailViewController *destViewController = segue.destinationViewController;
        destViewController.recipe = recipe;
    }
}

- (void)filterContentForSearchText:(NSString*)searchText scope:(NSString*)scope
{
    NSLog(@"filterContentForSearchText");
    NSPredicate *resultPredicate = [NSPredicate predicateWithFormat:@"name contains[c] %@", searchText];
    searchResults = [recipesArray filteredArrayUsingPredicate:resultPredicate];
}

-(BOOL)searchDisplayController:(UISearchDisplayController *)controller shouldReloadTableForSearchString:(NSString *)searchString
{
    NSLog(@"searchDisplayController");

    [self filterContentForSearchText:searchString
                               scope:[[self.searchDisplayController.searchBar scopeButtonTitles]
                                      objectAtIndex:[self.searchDisplayController.searchBar
                                                     selectedScopeButtonIndex]]];
    
    return YES;
}


@end
