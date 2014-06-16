//
//  RecipeViewController.m
//  RecipeViewController
//
//  Created by Diego Cibils on 4/10/14.
//  Copyright (c) 2014 KONA Cloud. All rights reserved.
//

#import "RecipeViewController.h"
#import "RecipeManager.h"
#import "IngredientsTableViewController.h"

@interface RecipeViewController ()
@end

@implementation RecipeViewController
@synthesize ingredients;
NSString *imageURL;
IngredientsTableViewController *ingredientsController;

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
    
    self.title = @"NEW RECIPE";
    // Uncomment the following line to preserve selection between presentations.
    // self.clearsSelectionOnViewWillAppear = NO;
    
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    
    UITapGestureRecognizer *tapGesture = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(choosePicture)];
    tapGesture.numberOfTapsRequired = 1;
    [self.recipeImage addGestureRecognizer:tapGesture];
    
    UIBarButtonItem *anotherButton = [[UIBarButtonItem alloc] initWithTitle:@"Save" style:UIBarButtonItemStylePlain
                                                                     target:self
                                                                     action:@selector(saveRecipe)];
    self.navigationItem.rightBarButtonItem = anotherButton;
    imageURL = @""; //initialize the url in blank
    
    UIStoryboard* storyboard = [UIStoryboard storyboardWithName:@"Main" bundle:nil];
    ingredientsController = (IngredientsTableViewController*)[storyboard instantiateViewControllerWithIdentifier: @"IngredientsTableViewController"];
    [ingredientsController setRecipeViewController:self];
    
    ingredients = [NSMutableArray new];
    
}

-(void)saveRecipe {
    
    Recipe *recipe = [Recipe new];
    [recipe setName:self.nameText.text];
    [recipe setPreptime:self.prepTimeText.text];
    
   // NSArray *ingredients = @[@"Meat", @"Lettuce", @"Tomato"];
    [recipe setIngredients:ingredients];
    [recipe setImage:imageURL];
    
    [RecipeManager saveRecipeWithRecipe:recipe withDelegate:self];
}

-(void)didFinishServiceSaveRecipeWithResult:(id)response {
    [self.navigationController popViewControllerAnimated:YES];
}

- (void)choosePicture
{
    NSString *placeInNewCategory = @"Take photo";
    NSString *chooseExisting = @"Select from library";
    NSString *cancelTitle = @"Cancel";
    UIActionSheet *actionSheet = [[UIActionSheet alloc]
                                  initWithTitle:nil
                                  delegate:self
                                  cancelButtonTitle:cancelTitle
                                  destructiveButtonTitle:nil
                                  otherButtonTitles:placeInNewCategory, chooseExisting, nil];
    
    [actionSheet showInView:self.view];
}

- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex {
    
    UIImagePickerController *picker = [[UIImagePickerController alloc] init];
    
    switch (buttonIndex) {
        case 0:
            //Doesn't work in the emulator
            picker.delegate = self;
            picker.allowsEditing = YES;
            picker.sourceType = UIImagePickerControllerSourceTypeCamera;
            [self presentViewController:picker animated:YES completion:NULL];
            break;
        case 1:
            picker.delegate = self;
            picker.allowsEditing = YES;
            picker.sourceType = UIImagePickerControllerSourceTypePhotoLibrary;
            [self presentViewController:picker animated:YES completion:NULL];
            break;
        default:
            break;
    }
}


- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary *)info {
    
    UIImage *chosenImage = info[UIImagePickerControllerEditedImage];
    NSData *imageData = UIImageJPEGRepresentation(chosenImage, 0.80f);
    [picker dismissViewControllerAnimated:YES completion:NULL];
    
    [self.recipeImage setImage:chosenImage];
    
    [RecipeManager uploadFileWithData:imageData withName:@"image" withMimeType:@"image/jpeg" withDelegate:self];
    
}

-(void)didFinishServiceUploadFileWithResult:(id)response {
    
    NSDictionary *fileData = [[response valueForKey:@"data"] objectAtIndex:0];
    NSString *uploadedImageURL = [fileData valueForKey:@"url"];
    NSLog(@"Uploaded image: %@", uploadedImageURL);

    imageURL = uploadedImageURL;
}

#pragma mark - Table view data source


- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.row == 3) {
        [ingredientsController setTableData:self.ingredients];
        [self.navigationController pushViewController:ingredientsController animated:YES];
    }
}

/*
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
#warning Potentially incomplete method implementation.
    // Return the number of sections.
    return 0;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
#warning Incomplete method implementation.
    // Return the number of rows in the section.
    return 0;
}
*/


/*
// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the specified item to be editable.
    return YES;
}
*/

/*
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
*/

/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath
{
}
*/

/*
// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}
*/

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
