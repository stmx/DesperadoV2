package com.example.desperadov2;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {
    private static final String TAG = "Parser work";

    static List<Album> parserAlbum(String[] url) throws IOException{
        List<Album> albums = new ArrayList<>();
        Album album;
        int page = 0;
        int elemrntsd = 0;
        Document doc = Jsoup.connect(url[0])
                .userAgent("Chrome/4.0.249.0 Safari/532.5")
                .referrer("http://www.google.com")
                .get();
        Elements listNews = doc.getElementsByClass("td_module_1 td_module_wrap td-animation-stack");
        for (Element element : listNews) {
            album = new Album();
            album.setDate(element.getElementsByClass("entry-date updated td-module-date").text());
            album.setPlace(element.getElementsByClass("td-post-category").text());
            album.setURLAlbum(element.getElementsByClass("entry-title td-module-title").select("a").attr("href"));
            album.setTitle(element.getElementsByClass("entry-title td-module-title").text());
            album.setURLThumbnailAlbum(element.getElementsByClass("td-module-thumb").select("img").attr("src"));
            //disko.setPhotoUrl(parserListPhotoUrl(disko.getURL()));
            albums.add(album);
            elemrntsd++;
            Log.i(TAG, "page "+page+"\tparse element"+elemrntsd);
        }
        return albums;
    }
    public static List<Photo> parserPhotoByAlbum(Album[] album) throws IOException{
        List<Photo> photos = new ArrayList<>();
        Photo photo;
        Document doc = Jsoup.connect(album[0].getURLAlbum())
                .userAgent("Chrome/4.0.249.0 Safari/532.5")
                .referrer("http://www.google.com")
                .get();
        Elements listNews = doc.getElementsByClass("wppa-tn-img-container");
        for (Element element : listNews.select("a")) {
            photo = new Photo();
            photo.setAlbum(album[0]);
            photo.setURLPhoto(element.attr("href"));
            photo.setURLThumbnailPhoto("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            album[0].addPhotoToAlbum(photo);
            photos.add(photo);
            Log.i(TAG, "parse photo");
        }
        return photos;
    }


}

