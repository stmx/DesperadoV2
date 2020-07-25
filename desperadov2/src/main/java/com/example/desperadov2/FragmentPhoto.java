package com.example.desperadov2;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

public class FragmentPhoto extends Fragment {
    private static final String TAG_PHOTO_URL = "photo_URL";
    private String mPhotoURL;
    private ImageViewer mImageViewPhoto;
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

    @SuppressLint("WrongViewCast")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo, container, false);
        mImageViewPhoto = v.findViewById(R.id.image_view_photo);
        Glide.with(getActivity())
                .asBitmap()
                .load(mPhotoURL)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                        mImageViewPhoto.setImageBitmap(bitmap);
                    }
                });
        return v;
    }
}
