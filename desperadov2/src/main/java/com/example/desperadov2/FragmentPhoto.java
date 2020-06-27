package com.example.desperadov2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class FragmentPhoto extends Fragment {
    private static final String TAG_PHOTO_URL = "photo_URL";
    private String mPhotoURL;
    private ImageView mImageViewPhoto;
    public static Fragment newInstance(String photoURL) {
        Bundle args = new Bundle();
        args.putString(TAG_PHOTO_URL,photoURL);
        Fragment fragment = new FragmentPhoto();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPhotoURL = (String) getArguments().getSerializable(TAG_PHOTO_URL);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo, container, false);
        mImageViewPhoto = v.findViewById(R.id.image_view_photo);
        Glide.with(getActivity()).load(mPhotoURL).into(mImageViewPhoto);
        return v;
    }
}
