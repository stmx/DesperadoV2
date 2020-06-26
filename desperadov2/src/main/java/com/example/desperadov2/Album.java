package com.example.desperadov2;

import java.util.List;

public class Album {
    private String mURLAlbum;
    private String mURLThumbnailAlbum;
    private String mTitle;
    private String mDate;
    private String mPlace;
    private List<Photo> mPhotos;

    public void addPhotoToAlbum(Photo photo) {
        mPhotos.add(photo);
    }

    public String getURLAlbum() {
        return mURLAlbum;
    }

    public void setURLAlbum(String URLAlbum) {
        mURLAlbum = URLAlbum;
    }

    public String getURLThumbnailAlbum() {
        return mURLThumbnailAlbum;
    }

    public void setURLThumbnailAlbum(String URLThumbnailAlbum) {
        mURLThumbnailAlbum = URLThumbnailAlbum;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getPlace() {
        return mPlace;
    }

    public void setPlace(String place) {
        mPlace = place;
    }

    public List<Photo> getPhotos() {
        return mPhotos;
    }

    public void setPhotos(List<Photo> photos) {
        mPhotos = photos;
    }
}
