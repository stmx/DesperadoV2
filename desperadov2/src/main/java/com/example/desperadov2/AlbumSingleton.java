package com.example.desperadov2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.desperadov2.database.AlbumDBSchema.*;
import com.example.desperadov2.database.DesperadoBaseHelper;
import com.example.desperadov2.database.DesperadoCursorWrapper;

import java.util.ArrayList;
import java.util.List;

public class AlbumSingleton {

    List<Album> mAlbumList;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private static AlbumSingleton sAlbums;

    private AlbumSingleton(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new DesperadoBaseHelper(mContext).getWritableDatabase();
        mAlbumList = new ArrayList<>();
    }

    public static AlbumSingleton get(Context context) {
        if (sAlbums == null) {
            sAlbums = new AlbumSingleton(context);
        }
        return sAlbums;
    }
    public void addAlbum(List<Album> addAlbumList) {
        for (Album album : addAlbumList) {
            ContentValues values = getContentValues(album);
            mDatabase.insert(AlbumTable.NAME, null, values);
        }
        mAlbumList.addAll(addAlbumList);
    }

    public List<Album> getAlbums() {
//        return mAlbumList;
        List<Album> list = new ArrayList<>();
        DesperadoCursorWrapper cursor = queryAlbum(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                list.add(cursor.getAlbum());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return list;
    }
    public Album getAlbum(String url) {
        for (Album album : mAlbumList) {
            if (album.getURLAlbum().equals(url)) {
                return album;
            }
        }
        return null;
    }

    private static ContentValues getContentValues(Album album) {
        ContentValues values = new ContentValues();
        values.put(AlbumTable.Cols.DATE, album.getDate());
        values.put(AlbumTable.Cols.PLACE, album.getPlace());
        values.put(AlbumTable.Cols.TITLE, album.getTitle());
        values.put(AlbumTable.Cols.URL_ALBUM, album.getURLAlbum());
        values.put(AlbumTable.Cols.THUMBNAIL_URL, album.getURLThumbnailAlbum());
        return values;
    }
    private DesperadoCursorWrapper queryAlbum(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                AlbumTable.NAME,
                null, // columns - с null выбираются все столбцы
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new DesperadoCursorWrapper(cursor);
    }
}
