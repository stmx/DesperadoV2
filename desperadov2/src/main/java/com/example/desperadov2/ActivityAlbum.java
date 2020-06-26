package com.example.desperadov2;

import androidx.fragment.app.Fragment;

public class ActivityAlbum extends SingleFragment{
    @Override
    Fragment createFragment() {
        return FragmentAlbum.newInstance();
    }
}
