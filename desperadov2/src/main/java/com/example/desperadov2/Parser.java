package com.example.desperadov2;

import android.util.Log;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//for parsing photo and albums from desperado.tomsk.ru
public class Parser {
    private static final String TAG = "Parser work";
//    <div> class for parsing
    private static final String CLASS_URL_THUMBNAIL = "td-module-thumb";
    private static final String CLASS_TITLE = "entry-title td-module-title";
    private static final String CLASS_PLACE = "td-post-category";
    private static final String CLASS_DATE = "entry-date updated td-module-date";
    private static final String CLASS_ELEMENT = "td_module_1 td_module_wrap td-animation-stack";
    private static final String CLASS_ELEMENT_PHOTO = "wppa-tn-img-container";

//    for parsing albums from desperado
//    input param URL with number page
//    return array of album objects
    static List<Album> parserAlbum(String[] url) throws IOException{
        List<Album> albums = new ArrayList<>();
        Album album;
        int page = 0;
        int counter = 0;
        Document doc = Jsoup.connect(url[0])
                .userAgent("Chrome/4.0.249.0 Safari/532.5")
                .referrer("http://www.google.com")
                .get();
        Elements elements = doc.getElementsByClass(CLASS_ELEMENT);
        for (Element element : elements) {
            album = new Album();
            album.setDate(element.getElementsByClass(CLASS_DATE).text());
            album.setPlace(element.getElementsByClass(CLASS_PLACE).text());
            album.setURLAlbum(element.getElementsByClass(CLASS_TITLE).select("a").attr("href"));
            album.setTitle(element.getElementsByClass(CLASS_TITLE).text());
            album.setURLThumbnailAlbum(element.getElementsByClass(CLASS_URL_THUMBNAIL).select("img").attr("src"));
            albums.add(album);
            counter++;
            Log.i(TAG, "page "+page+"\tparse element"+counter);
        }
        return albums;
    }
//    for parsing photo from desperado
//    input param URL ALbum
//    return array of photo objects
    public static List<Photo> parserPhotoByAlbum(String[] albumURL) throws IOException{
        List<Photo> photos = new ArrayList<>();
        Photo photo;
        Document doc = Jsoup.connect(albumURL[0])
                .userAgent("Chrome/4.0.249.0 Safari/532.5")
                .referrer("http://www.google.com")
                .get();
        Elements listNews = doc.getElementsByClass(CLASS_ELEMENT_PHOTO);
        for (Element element : listNews) {
            photo = new Photo();
            photo.setAlbumURL(albumURL[0]);
            photo.setURLPhoto(element.select("a").attr("href"));
            photo.setURLThumbnailPhoto(element.select("img").attr("src"));
            photos.add(photo);
            Log.i(TAG, "parse photo");
        }
        return photos;
    }


}

