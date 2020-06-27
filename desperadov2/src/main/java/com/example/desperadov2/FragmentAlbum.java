package com.example.desperadov2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FragmentAlbum extends Fragment {
    private static final String TAG_ALBUM_URL = "album_URL";
//    List<Photo> mPhotos;
    RecyclerView mRecyclerViewAlbum;
    AlbumAdapter mAlbumAdapter;
    Album mAlbum;

    FragmentAlbum() {
//        mPhotos = new ArrayList<>();
    }
    public static Fragment newInstance(String albumURL) {
        Bundle args = new Bundle();
        args.putString(TAG_ALBUM_URL, albumURL);
        Fragment fragment = new FragmentAlbum();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String albumURL;
        albumURL = getArguments().getString(TAG_ALBUM_URL);
        mAlbum = AlbumSingleton.get().getAlbum(albumURL);
        new downloadAlbumItems().execute(mAlbum);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        mAlbumAdapter = new AlbumAdapter();
        mRecyclerViewAlbum = view.findViewById(R.id.recycler_view_album);
        mRecyclerViewAlbum.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mRecyclerViewAlbum.setAdapter(mAlbumAdapter);
        return view;
    }
    public class AlbumAdapter extends RecyclerView.Adapter<AlbumHolder> {

        @NonNull
        @Override
        public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new AlbumHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull AlbumHolder holder, int position) {
            Photo photo = mAlbum.getPhotos().get(position);
            holder.bind(photo);
        }

        @Override
        public int getItemCount() {
            return mAlbum.getPhotos().size();
        }
    }
    public class AlbumHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView mImageViewItemAlbum;
        private Photo mPhoto;
        public AlbumHolder(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
            super(inflater.inflate(R.layout.item_album, container,false));
            mImageViewItemAlbum = itemView.findViewById(R.id.image_view_item_album);
            itemView.setOnClickListener(this);
        }

        public void bind(Photo photo) {
            mPhoto = photo;
            Glide.with(getActivity()).load(photo.getURLThumbnailPhoto()).into(mImageViewItemAlbum);
        }

        @Override
        public void onClick(View v) {
            Intent intent = ActivityPhoto.newIntent(getActivity(),mPhoto.getURLPhoto());
            startActivity(intent);
        }
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
            mAlbum.setPhotos(photos);
            mAlbumAdapter.notifyDataSetChanged();
//            mPhotos = photos;
        }
    }
}
