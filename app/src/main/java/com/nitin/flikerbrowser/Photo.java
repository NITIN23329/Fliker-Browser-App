/*  created by: nitin23329 
    created on 20/12/21 
    inside the package - com.nitin.flikerbrowser 
*/
package com.nitin.flikerbrowser;

import androidx.annotation.NonNull;

class Photo {
    /*
        This hold all the information we need for displaying an entity in the RecyclerView.
     */
    private String title, author, authorID, link, tags, imageUrl;

    public Photo(String title, String author, String authorID, String link, String tags, String imageUrl) {
        this.title = title;
        this.author = author;
        this.authorID = authorID;
        this.link = link;   // link to image to open it in full screen
        this.tags = tags;
        this.imageUrl = imageUrl;     // jpg image link


    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getAuthorID() {
        return authorID;
    }

    public String getLink() {
        return link;
    }

    public String getTags() {
        return tags;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @NonNull
    @Override
    public String toString() {
        return "Entity{" +
                "title=" + title + '\t' +
                ", author=" + author + '\t' +
                ", authorID=" + authorID + '\t' +
                ", link=" + link + '\t' +
                ", tags=" + tags + '\t' +
                ", imageUrl=t" + imageUrl + '\t' +
                '}';
    }
}
