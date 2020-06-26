package com.example.desperadov2;

import androidx.fragment.app.Fragment;

public class ActivityGallery extends SingleFragment {
    @Override
    Fragment createFragment() {
        return FragmentGallery.newInstance();
    }
}
