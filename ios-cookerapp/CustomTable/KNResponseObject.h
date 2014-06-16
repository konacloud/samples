//
//  KNResponseObject.h
//  RecipeApp
//
//  Created by Diego Cibils on 4/16/14.
//  Copyright (c) 2014 KONA All rights reserved.
//

#import <Foundation/Foundation.h>

@interface KNResponseObject : NSObject

-(KNResponseObject*)initWithDict:(NSDictionary *)dict;
-(BOOL) didFinishWithSuccess;

@property (nonatomic, retain) NSNumber* status;
@property (nonatomic, retain) NSString* message;
@property (nonatomic, retain) id data;
@property (nonatomic, retain) NSString* debug;

@end
