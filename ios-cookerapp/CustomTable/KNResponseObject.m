//
//  KNResponseObject.m
//  RecipeApp
//
//  Created by Diego Cibils on 4/16/14.
//  Copyright (c) 2014 KONA Cloud All rights reserved.
//

#import "KNResponseObject.h"

@implementation KNResponseObject

-(KNResponseObject*)initWithDict:(NSDictionary *)dict
{
    self = [super init];
    if (self) {
        self.status           = [dict valueForKey:@"success"];
        self.message          = [dict valueForKey:@"errorMsg"];
        self.data             = [dict valueForKey:@"data"];
        //self.debug            = [dict valueForKey:@"_debug"];
    }
    return self;
}


-(BOOL) didFinishWithSuccess {
    return ([self.status intValue] == 1);
}
@end
