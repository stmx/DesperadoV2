package com.example.desperadov2;

public class Photo{
    public String getURLPhoto() {
        return mURLPhoto;
    }

    public void setURLPhoto(String URLPhoto) {
        mURLPhoto = URLPhoto;
    }

    public String getURLThumbnailPhoto() {
        return mURLThumbnailPhoto;
    }

    public void setURLThumbnailPhoto(String URLThumbnailPhoto) {
        mURLThumbnailPhoto = URLThumbnailPhoto;
    }

    public Album getAlbum() {
        return mAlbum;
    }

    public void setAlbum(Album album) {
        mAlbum = album;
    }

    private String mURLPhoto;
    private String mURLThumbnailPhoto;
    private Album mAlbum;
}
