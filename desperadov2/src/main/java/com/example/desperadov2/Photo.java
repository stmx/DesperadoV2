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

    public String getAlbumURL() {
        return mAlbumURL;
    }

    public void setAlbumURL(String album) {
        mAlbumURL = album;
    }

    private String mURLPhoto;
    private String mURLThumbnailPhoto;
    private String mAlbumURL;
}
