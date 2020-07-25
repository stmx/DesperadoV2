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
// necessary to store ref to the database and array of album
// read and write data from/to database
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private static AlbumSingleton sAlbums;

    private AlbumSingleton(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new DesperadoBaseHelper(mContext).getWritableDatabase();
    }

    public static AlbumSingleton get(Context context) {
//      return class object, if object doesn't exist create it
        if (sAlbums == null) {
            sAlbums = new AlbumSingleton(context);
        }
        return sAlbums;
    }

    public<X> void add(List<X> addObject) {
//        insert array (album or photo) to database
        ContentValues values;
        Class<?> objectClass;
        for (X object : addObject) {
            objectClass = object.getClass();
            if (Album.class.equals(objectClass)) {
                values = getContentValues((Album) object);
                mDatabase.insert(AlbumTable.NAME, null, values);
            } else if (Photo.class.equals(objectClass)) {
                values = getContentValues((Photo) object);
                mDatabase.insert(PhotoTable.NAME, null, values);
            }
        }
    }

    public List<Album> getAlbums() {
//        find all albums in database
//        return array of albums from database
        List<Album> albums = new ArrayList<>();
        DesperadoCursorWrapper cursor = query(AlbumTable.NAME,null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                albums.add(cursor.getAlbum());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return albums;
    }
    public List<Photo> getPhotos(String URLAlbum) {
//        find all photos by URL album
//        return array of photo from database
        List<Photo> photos = new ArrayList<>();
        DesperadoCursorWrapper cursor = query(PhotoTable.NAME,PhotoTable.Cols.URL_ALBUM + " = ?", new String[] {URLAlbum});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                photos.add(cursor.getPhoto());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return photos;
    }
    public Album getAlbum(String URLAlbum) {
//        find album by URL album
//        return album from database
        DesperadoCursorWrapper cursorWrapper = query(AlbumTable.NAME,AlbumTable.Cols.URL_ALBUM + " = ?", new String[] {URLAlbum});
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
    public Photo getPhoto(String URLPhoto) {
//        find photo by URL photo
//        return photo from database
        DesperadoCursorWrapper cursorWrapper = query(PhotoTable.NAME,PhotoTable.Cols.URL_PHOTO + " = ?", new String[] {URLPhoto});
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

    private static ContentValues getContentValues(Album album) {
//        create contentValues for insert album object to database
        ContentValues values = new ContentValues();
        values.put(AlbumTable.Cols.DATE, album.getDate());
        values.put(AlbumTable.Cols.PLACE, album.getPlace());
        values.put(AlbumTable.Cols.TITLE, album.getTitle());
        values.put(AlbumTable.Cols.URL_ALBUM, album.getURLAlbum());
        values.put(AlbumTable.Cols.THUMBNAIL_URL, album.getURLThumbnailAlbum());
        return values;
    }
    private static ContentValues getContentValues(Photo photo) {
//        create contentValues for insert photo object to database
        ContentValues values = new ContentValues();
        values.put(PhotoTable.Cols.THUMBNAIL_URL, photo.getURLThumbnailPhoto());
        values.put(PhotoTable.Cols.URL_ALBUM, photo.getAlbumURL());
        values.put(PhotoTable.Cols.URL_PHOTO, photo.getURLPhoto());
        return values;
    }

    private DesperadoCursorWrapper query(String name,String whereClause, String[] whereArgs) {
//        get data from database with query request
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
