package com.ass2.i190582_i190534;

public class Song {
    String title, genre, description, image,  url;

    public Song (){

    }

    public Song(String title, String genre, String description, String image, String song) {
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.image = image;

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}
