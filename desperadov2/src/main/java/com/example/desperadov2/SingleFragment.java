package com.example.desperadov2;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

// for starting fragment from activity
public abstract class SingleFragment extends AppCompatActivity {
    abstract Fragment createFragment();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.frame_container);
//        find fragment by ID if the fragment not created create a new and add to fragment manager
        if (fragment == null) {
            fragment = createFragment();
            fragmentManager.beginTransaction().add(R.id.frame_container, fragment).commit();
        }
    }
}
