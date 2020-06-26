package com.example.desperadov2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FragmentAlbum extends Fragment {
    List<Photo> mPhotos;
    RecyclerView mRecyclerViewAlbum;

    FragmentAlbum() {
        mPhotos = new ArrayList<>();
    }
    public static Fragment newInstance() {
        return new FragmentAlbum();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        mRecyclerViewAlbum = view.findViewById(R.id.recycler_view_album);
        mRecyclerViewAlbum.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    private class downloadAlbumItems extends AsyncTask<Album, Void, List<Photo>> {
        @Override
        protected List<Photo> doInBackground(Album... albums) {
            List<Photo> photos = new ArrayList<>();
            try {
                photos = Parser.parserPhotoByAlbum(albums);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return photos;
        }
        @Override
        protected void onPostExecute(List<Photo> photos) {
            mPhotos = photos;
        }
    }
}
