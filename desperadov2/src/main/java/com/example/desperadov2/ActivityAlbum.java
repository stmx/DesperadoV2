package com.example.desperadov2;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

public class ActivityAlbum extends SingleFragment{
    private final static String TAG_ALBUM_URL = "com.example.desperadov2.album_url";
    public static Intent newIntent(Context context, String albumURL) {
        Intent intent = new Intent(context, ActivityAlbum.class);
        intent.putExtra(TAG_ALBUM_URL, albumURL);
        return intent;
    }
    @Override
    Fragment createFragment() {
        String albumURL = (String) getIntent().getSerializableExtra(TAG_ALBUM_URL);
        return FragmentAlbum.newInstance(albumURL);
    }
}
