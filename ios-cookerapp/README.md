CookerApp - An iOS sample app for storing cooking recipes

This app is a very simple app that basically does these 2 things:

- Allows you to upload/save a recipe you know with an awesome picture of your dish
- Browse these recipes for when you are hungry and ready to do some cooking

Download the sample code from here:

https://github.com/konacloud/samples/tree/master/ios-cookerapp

Import the cookerapp.kson (KONA App) into your KONA Cloud account. This will create the app backend for you with the following elements:

- Recipe data model
- images bucket for storing recipes images

Once this is done, open the CookerApp Xamarin solution, and change the service URL according to your own KONA Cloud account service URL.

This is done in the RecipeManager.m, line 15:

#define K_SERVICE_API_BASE_URL @"http://app.konacloud.io/api/diego/CookerApp/"

IF you have enabled TOKEN security for your backend, you need to also change this in line 16:

#define K_SERVICE_API_TOKEN @"89e36f59-2cf0-479d-a332-355393a1ff28"

Compile, run & enjoy!

Need support? support@konacloud.io
