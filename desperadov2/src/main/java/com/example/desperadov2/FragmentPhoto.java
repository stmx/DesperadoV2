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
    private GestureDetector mGestureDetector;
    private ScaleGestureDetector mScaleGestureDetector;
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

//        mGestureDetector = new GestureDetector(mImageViewPhoto.getContext(), new GestureListenerScroll());

//        mGestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
//            @Override
//            public boolean onSingleTapConfirmed(MotionEvent e) {
//                return false;
//            }
//
//            @Override
//            public boolean onDoubleTap(MotionEvent e) {
//                countZoom++;
//                mScaleFactor *= 2;
//                if (countZoom == 3) {
//                    countZoom = 0;
//                    mScaleFactor = 1;
//                }
//                mImageViewPhoto.animate().scaleX(mScaleFactor).scaleY(mScaleFactor).setDuration(300);
//                return true;
//            }
//
//            @Override
//            public boolean onDoubleTapEvent(MotionEvent e) {
//                return false;
//            }
//        });
//        mImageViewPhoto.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                mScaleGestureDetector.onTouchEvent(event);
//                return true;
//            }
//        });

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
//        mScaleGestureDetector = new ScaleGestureDetector(getActivity(), new ScaleListener());
        return v;
    }

    class GestureListenerScroll extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//            Log.i("mGestureDetector", e2.toString());
//            Log.i("mGestureDetector", String.valueOf(mImageViewPhoto.getScrollX()));

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

//    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
//        @Override
//        public boolean onScaleBegin(ScaleGestureDetector detector) {
//            Log.i("scaleFactor", "scaleBegin");
//            return super.onScaleBegin(detector);
//        }
//
//        @Override
//        public boolean onScale(ScaleGestureDetector scaleGestureDetector){
//            mScaleFactor *= scaleGestureDetector.getScaleFactor();
//
//            mScaleFactor = Math.max(1f,
//                    Math.min(mScaleFactor, 10.0f));
//            mScaleFactor = Filter.filt(mScaleFactor);
//            Log.i("scaleFactor", String.valueOf(mScaleFactor));
//            mImageViewPhoto.setScaleX(mScaleFactor);
//            mImageViewPhoto.setScaleY(mScaleFactor);
//            return true;
//        }
//    }
}
