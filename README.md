# Fliker-Browser-App

## Introduction: This app will search images using Flickr API. 

I have implemented search facilities where users can specify the tags of the images they want, and this app will only search images for those tags. 
Upon searching, a list of photos and their title will be displayed using a Recycler-view.

And if the user wants to know more details about a particular photo, it can single tap on a specific entry, and this will generate another activity 
which will show the picture in a bigger size, the title, author, and tags allocated to that particular photo.

If the user long-press a particular photo, another activity will get generated, which will open the Flicker site link of that image in the browser.

## Functionalities Implemented :
0) Enter tags using the search bar present at the Toolbar. 

<img str=https://user-images.githubusercontent.com/55681638/147399669-f9b51f47-61f5-4848-9308-128cf601ccaa.jpeg width="200" height="400">

1) Download JSON data from the Flicker site for searched tags.
2) Parse the JSON data using JSONObject and JSONArray and convert them to a list of Photos.
3) The List of photos is displayed in a scrollable Recycler View using a custom View-Adapter and View-Holder.
4) Implemented Gesture detector functionality to detect when the single tap or long-press has been done in any photo.
5) If a single tap has been done, a new activity will launch to display more details and a large image of the tapped photo.
5) If a long tap is done, a new activity will launch the browser with the Flickr site opened for the same photo.

