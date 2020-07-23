package com.example.desperadov2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

public class FragmentPhoto extends Fragment {
    private static final String TAG_PHOTO_URL = "photo_URL";
    private String mPhotoURL;
    private ImageViewer mImageViewPhoto;
    private float mScaleFactor = 1.0f;
    static int countZoom = 0;
    static int borderX = 100;
    int w;
    int h;
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
                        w = bitmap.getWidth();
                        h = bitmap.getHeight();
                        mImageViewPhoto.setW(w);
                        mImageViewPhoto.setH(h);
                        mImageViewPhoto.setImageBitmap(bitmap);
                    }
                });
        return v;
    }

    class GestureListenerScroll extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            borderX =100* (int)mScaleFactor;
            Log.i("mGestureDetector", String.valueOf(borderX));
            if (mImageViewPhoto.getScrollX() > borderX) {

                if (distanceX > 0) {
                    return false;
                } else {
                    mImageViewPhoto.scrollBy((int)distanceX,(int)distanceY);
                }

            }
            if (mImageViewPhoto.getScrollX() < -borderX) {

                if (distanceX < 0) {
                    return false;
                } else {
                    mImageViewPhoto.scrollBy((int)distanceX,(int)distanceY);
                }

            }
            mImageViewPhoto.scrollBy((int)distanceX,(int)distanceY);
            return false;
        }
    }

}
