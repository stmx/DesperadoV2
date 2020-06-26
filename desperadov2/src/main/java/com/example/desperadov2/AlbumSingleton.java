package com.example.desperadov2;

import java.util.ArrayList;
import java.util.List;

public class AlbumSingleton {

    List<Album> mAlbumList;
    private static AlbumSingleton sAlbums;

    private AlbumSingleton() {
       mAlbumList = new ArrayList<>();
    }

    public static AlbumSingleton get() {
        if (sAlbums == null) {
            sAlbums = new AlbumSingleton();
        }
        return sAlbums;
    }
    public void addAlbum(List<Album> addAlbumList) {
        mAlbumList.addAll(addAlbumList);
    }

    public List<Album> getAlbums() {
        return mAlbumList;
    }
    public Album getAlbum(String url) {
        for (Album album : mAlbumList) {
            if (album.getURLAlbum().equals(url)) {
                return album;
            }
        }
        return null;
    }

}
