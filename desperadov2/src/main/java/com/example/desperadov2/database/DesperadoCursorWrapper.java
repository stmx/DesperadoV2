package com.example.desperadov2.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.desperadov2.Album;

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
}
