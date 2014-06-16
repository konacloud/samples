//
//  IngredientsTableViewController.m
//  RecipeApp
//
//  Created by Diego Cibils on 4/11/14.
//  Copyright (c) 2014 KONA Cloud. All rights reserved.
//

#import "IngredientsTableViewController.h"
#import "IngredientCell.h"

@interface IngredientsTableViewController ()

@end

@implementation IngredientsTableViewController
@synthesize  tableData;
@synthesize recipeViewController;

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    self.title = @"INGREDIENTS";
    
    UIBarButtonItem *add = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemAdd target:self action:@selector(addRow)];
    UIBarButtonItem *back = [[UIBarButtonItem alloc] initWithTitle:@"Back" style:UIBarButtonItemStylePlain target:self action:@selector(back)];
    
    self.navigationItem.leftBarButtonItem = back;
    self.navigationItem.rightBarButtonItem = add;

    tableData = [NSMutableArray new];
}

-(void)back {
    
    NSLog(@"back");
    // run through the table and get the ingredients

    for (int row = 0; row < tableData.count; row++) {
        NSIndexPath* cellPath = [NSIndexPath indexPathForRow:row inSection:0];
        IngredientCell* cell = (IngredientCell*)[self.tableView cellForRowAtIndexPath:cellPath];
        [tableData setObject:cell.ingredientText.text atIndexedSubscript:row];
    }
    
    [recipeViewController setIngredients:tableData];
    [self.navigationController popViewControllerAnimated:YES];
}

-(void)addRow {
    
    [tableData addObject:@""];
    
   // self.numberOfRows = self.numberOfRows + 1;
    [self.tableView insertRowsAtIndexPaths:@[[NSIndexPath indexPathForRow:0 inSection:0]]
                          withRowAnimation:UITableViewRowAnimationAutomatic];
    
 }

-(void)did :(UIViewController *)parent {
    NSLog(@"willMoveToParentViewController");
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    return tableData.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    IngredientCell *cell = [tableView dequeueReusableCellWithIdentifier:@"ingredient" forIndexPath:indexPath];
    return cell;
}


// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the specified item to be editable.
    return YES;
}


// Override to support editing the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        // Delete the row from the data source
        [tableView deleteRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationFade];
    } else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
    }   
}

// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath
{
}


// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}


/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
