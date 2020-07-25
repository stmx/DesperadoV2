package com.example.desperadov2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FragmentGallery extends Fragment{

    RecyclerView mRecyclerViewGallery;
    GalleryAdapter mGalleryAdapter;
    private static final int ITEM_BEFORE_LOAD = 2;
    static int page = 0;

    public static Fragment newInstance() {
        return new FragmentGallery();
    }

    private String nextPageURL(int size) {
//        return string value URL
        String START_URL = "https://xn--j1adfnc.xn--80ahbca0ddjg.xn--p1ai/category/photo/";
        page = (int) size / 10 + 1;
        return (START_URL + "page/" + page + "/");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new DownloadGalleryItems().execute(nextPageURL(0));

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        mGalleryAdapter = new GalleryAdapter(AlbumSingleton.get(getActivity()).getAlbums());
        mRecyclerViewGallery = view.findViewById(R.id.recycler_view_gallery);
        mRecyclerViewGallery.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerViewGallery.setAdapter(mGalleryAdapter);
        return view;
    }

    private class GalleryAdapter extends RecyclerView.Adapter<GalleryHolder> {
//        adapter for recycler view
        List<Album> mAlbums;
        public GalleryAdapter(List<Album> albums) {
            mAlbums = albums;
        }
        @NonNull
        @Override
        public GalleryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new GalleryHolder(inflater, parent);
        }
        @Override
        public void onBindViewHolder(@NonNull GalleryHolder holder, int position) {
            Album album = mAlbums.get(position);
            holder.bind(album);
//            need to download new albums for recyclerview
            if (mAlbums.size() - position < ITEM_BEFORE_LOAD) {
                new DownloadGalleryItems().execute(nextPageURL(mAlbums.size()));
            }
        }
        @Override
        public int getItemCount() {
            return mAlbums.size();
        }

        public void setAlbums(List<Album> albums) {
            mAlbums = albums;
        }
    }

    private class GalleryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mImageViewThumbnails;
        TextView mTextViewTitle;
        TextView mTextViewDate;
        TextView mTextViewPlace;
        Album mAlbum;
        public GalleryHolder(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
            super(inflater.inflate(R.layout.item_gallery, container, false));
            mImageViewThumbnails = itemView.findViewById(R.id.image_view_item_gallery);
            mTextViewDate = itemView.findViewById(R.id.text_view_date);
            mTextViewPlace = itemView.findViewById(R.id.text_view_place);
            mTextViewTitle = itemView.findViewById(R.id.text_view_title);
            itemView.setOnClickListener(this);
        }
        public void bind(Album album) {
            mAlbum = album;
            mTextViewTitle.setText(album.getTitle());
            mTextViewPlace.setText(album.getPlace());
            mTextViewDate.setText(album.getDate());
            Glide.with(getActivity()).load(album.getURLThumbnailAlbum()).into(mImageViewThumbnails);
        }
        @Override
        public void onClick(View v) {
            Intent intent = ActivityAlbum.newIntent(getActivity(),mAlbum.getURLAlbum());
            startActivity(intent);
        }
    }
    private class DownloadGalleryItems extends AsyncTask<String, Void, List<Album>> {
        @Override
        protected List<Album> doInBackground(String... urls) {
            List<Album> albums = new ArrayList<>();
            try {
                albums = Parser.parserAlbum(urls);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return albums;
        }
        @Override
        protected void onPostExecute(List<Album> albums) {
            int start = AlbumSingleton.get(getActivity()).getAlbums().size();
            AlbumSingleton.get(getActivity()).add(albums);
            mGalleryAdapter.setAlbums(AlbumSingleton.get(getActivity()).getAlbums());
            mGalleryAdapter.notifyItemInserted(start);
        }
    }
}
