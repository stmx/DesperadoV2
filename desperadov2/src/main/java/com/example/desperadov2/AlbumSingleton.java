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

//    List<Album> mAlbumList;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private static AlbumSingleton sAlbums;

    private AlbumSingleton(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new DesperadoBaseHelper(mContext).getWritableDatabase();
//        mAlbumList = new ArrayList<>();
    }

    public static AlbumSingleton get(Context context) {
        if (sAlbums == null) {
            sAlbums = new AlbumSingleton(context);
        }
        return sAlbums;
    }

    public<X> void add(List<X> addList) {
        ContentValues values;
        Class<?> aClass;
        for (X item : addList) {
            aClass = item.getClass();
            if (Album.class.equals(aClass)) {
                values = getContentValuesAlbum((Album) item);
                mDatabase.insert(AlbumTable.NAME, null, values);
            } else if (Photo.class.equals(aClass)) {
                values = getContentValuesPhoto((Photo) item);
                mDatabase.insert(PhotoTable.NAME, null, values);
            }
        }
//        mAlbumList.addAll(addAlbumList);
    }

    public List<Album> getAlbums() {
//        return mAlbumList;
        List<Album> list = new ArrayList<>();
        DesperadoCursorWrapper cursor = query(AlbumTable.NAME,null, null);
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
    public List<Photo> getPhotos(String url) {
        List<Photo> list = new ArrayList<>();
        DesperadoCursorWrapper cursor = query(PhotoTable.NAME,PhotoTable.Cols.URL_ALBUM + " = ?", new String[] {url});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                list.add(cursor.getPhoto());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return list;
    }
    public Album getAlbum(String url) {
//        for (Album album : mAlbumList) {
//            if (album.getURLAlbum().equals(url)) {
//                return album;
//            }
//        }
//        return null;
        DesperadoCursorWrapper cursorWrapper = query(AlbumTable.NAME,AlbumTable.Cols.URL_ALBUM + " = ?", new String[] {url});
        try {
            if (cursorWrapper.getCount() == 0) {
                return null;
            }
            cursorWrapper.moveToFirst();
            return cursorWrapper.getAlbum();
        }finally {
            cursorWrapper.close();
        }

    }
    public Photo getPhoto(String url) {
        DesperadoCursorWrapper cursorWrapper = query(PhotoTable.NAME,PhotoTable.Cols.URL_PHOTO + " = ?", new String[] {url});
        try {
            if (cursorWrapper.getCount() == 0) {
                return null;
            }
            cursorWrapper.moveToFirst();
            return cursorWrapper.getPhoto();
        }finally {
            cursorWrapper.close();
        }

    }

    private static ContentValues getContentValuesAlbum(Album album) {
        ContentValues values = new ContentValues();
        values.put(AlbumTable.Cols.DATE, album.getDate());
        values.put(AlbumTable.Cols.PLACE, album.getPlace());
        values.put(AlbumTable.Cols.TITLE, album.getTitle());
        values.put(AlbumTable.Cols.URL_ALBUM, album.getURLAlbum());
        values.put(AlbumTable.Cols.THUMBNAIL_URL, album.getURLThumbnailAlbum());
        return values;
    }
    private static ContentValues getContentValuesPhoto(Photo photo) {
        ContentValues values = new ContentValues();
        values.put(PhotoTable.Cols.THUMBNAIL_URL, photo.getURLThumbnailPhoto());
        values.put(PhotoTable.Cols.URL_ALBUM, photo.getAlbumURL());
        values.put(PhotoTable.Cols.URL_PHOTO, photo.getURLPhoto());
        return values;
    }
    private DesperadoCursorWrapper query(String name,String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                name,
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
