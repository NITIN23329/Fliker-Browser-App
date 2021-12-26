# Fliker-Browser-App

## Introduction: This app will search and display photos of specified tags present in Flickr site. 

I have implemented search facilities where users can specify the tags(comma seperated) of the images they want, and this app will only search images for those tags. 
Upon searching, a list of photos and their title will be displayed using a Recycler-view. User can scroll up and down in the list to find more photos.
And if the user wants to know more details about a particular photo, it can single tap on a specific entry, and this will generate another activity 
which will show the picture in a bigger size, the title, author, and tags allocated to that particular photo.
If the user long-press a particular photo, another activity will get generated, which will open the Flicker site link of that image in the browser.

## Functionalities Implemented :
1) When app is launched, originally we will see the UI having close icon and search icon like this:

    <img src=https://user-images.githubusercontent.com/55681638/147400005-19398319-2f75-482e-80ac-c93b5628e383.jpeg width=200 height=350>

2) Enter tags using the search bar present at the Toolbar by tapping the search icon.

   <img src=https://user-images.githubusercontent.com/55681638/147399802-839b7161-0a54-4ef6-9612-34677ee66ced.jpeg  width=200 height=350>
   
3) Download JSON data from the Flicker site for searched tags.
4) Parse the JSON data using JSONObject and JSONArray and convert them to a list of Photos.
5) The List of photos is displayed in a scrollable Recycler View using a custom View-Adapter and View-Holder.

    <img src=https://user-images.githubusercontent.com/55681638/147399929-2b7ed351-159c-4097-a790-c4d41a2f654b.jpeg width=200 height=350>

6) Implemented Gesture detector functionality to detect when the single tap or long-press has been done in any photo.
7) If a single tap has been done, a new activity will launch to display more details and a large image of the tapped photo.

    <img src=https://user-images.githubusercontent.com/55681638/147399825-d9bbc0a3-7c2e-422d-a1cf-3c80744d49fc.jpeg width=200 height=350>

8) If a long tap is done, a new activity will launch the browser with the Flickr site opened for the same photo.

    <img src=https://user-images.githubusercontent.com/55681638/147399832-c6653a7b-5214-4569-902a-33122be9755d.jpeg width=200 height=350>
