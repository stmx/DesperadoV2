package com.example.desperadov2;

import androidx.fragment.app.Fragment;

public class ActivityPhoto extends SingleFragment {

    @Override
    Fragment createFragment() {
        return FragmentPhoto.newInstance();
    }
}
