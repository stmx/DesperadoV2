package com.example.desperadov2.database;

final public class AlbumDBSchema {

    public static class AlbumTable {
        public static final String NAME = "albums";
        public static class Cols {
            public static final String DATE = "date";
            public static final String PLACE = "place";
            public static final String TITLE = "title";
            public static final String URL_ALBUM = "URL";
            public static final String THUMBNAIL_URL = "ThumbnailUrl";
        }
    }
    public static class PhotoTable {
        public static final String NAME = "photos";
        public static class Cols {
            public static final String URL_ALBUM = "albumUrl";
            public static final String URL_PHOTO = "URL";
            public static final String THUMBNAIL_URL = "thumbnailUrl";
        }
    }
}
