package com.example.desperadov2;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

public class ActivityPhoto extends SingleFragment {
    private static final String TAG_URL = "com.example.desperadov2.photo_url";
    public static Intent newIntent(Context context,String photoURL) {
        Intent intent = new Intent(context, ActivityPhoto.class);
        intent.putExtra(TAG_URL, photoURL);
        return intent;
    }

    @Override
    Fragment createFragment() {
        String photoURL = (String) getIntent().getSerializableExtra(TAG_URL);
        return FragmentPhoto.newInstance(photoURL);
    }
}
