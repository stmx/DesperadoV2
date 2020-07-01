package com.example.desperadov2.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.desperadov2.Album;
import com.example.desperadov2.Photo;

public class DesperadoCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public DesperadoCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Album getAlbum() {
        Album album = new Album();
        album.setDate(getString(getColumnIndex(AlbumDBSchema.AlbumTable.Cols.DATE)));
        album.setPlace(getString(getColumnIndex(AlbumDBSchema.AlbumTable.Cols.PLACE)));
        album.setURLAlbum(getString(getColumnIndex(AlbumDBSchema.AlbumTable.Cols.URL_ALBUM)));
        album.setTitle(getString(getColumnIndex(AlbumDBSchema.AlbumTable.Cols.TITLE)));
        album.setURLThumbnailAlbum(getString(getColumnIndex(AlbumDBSchema.AlbumTable.Cols.THUMBNAIL_URL)));
        return album;
    }
    public Photo getPhoto() {
        Photo photo = new Photo();
        photo.setAlbumURL(getString(getColumnIndex(AlbumDBSchema.AlbumTable.Cols.URL_ALBUM)));
        photo.setURLPhoto(getString(getColumnIndex(AlbumDBSchema.PhotoTable.Cols.URL_PHOTO)));
        photo.setURLThumbnailPhoto(getString(getColumnIndex(AlbumDBSchema.PhotoTable.Cols.THUMBNAIL_URL)));
        return photo;
    }
}
