package com.example.desperadov2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import com.example.desperadov2.database.AlbumDBSchema.*;

public class DesperadoBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "desperadoBase.db";
    public DesperadoBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String PRIMARY_KEY = " primary key";
        String requestCreateAlbumTable = "create table " + AlbumTable.NAME + "("+
                AlbumTable.Cols.PLACE + ", " +
                AlbumTable.Cols.TITLE + ", " +
                AlbumTable.Cols.DATE + ", " +
                AlbumTable.Cols.URL_ALBUM + PRIMARY_KEY+", " +
                AlbumTable.Cols.THUMBNAIL_URL +
                ")";
        db.execSQL(requestCreateAlbumTable);
        String requestCreatePhotoTable = "create table " + PhotoTable.NAME + "("+
                PhotoTable.Cols.URL_PHOTO + PRIMARY_KEY+", " +
                PhotoTable.Cols.THUMBNAIL_URL + ", " +
                PhotoTable.Cols.URL_ALBUM +
                ")";
        db.execSQL(requestCreatePhotoTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
