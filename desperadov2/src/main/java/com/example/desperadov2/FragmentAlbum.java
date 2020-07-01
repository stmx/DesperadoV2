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
    String mAlbumURL;

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

        mAlbumURL = getArguments().getString(TAG_ALBUM_URL);
//        mAlbum = AlbumSingleton.get(getActivity()).getAlbum(mAlbumURL);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        mAlbumAdapter = new AlbumAdapter();
        mRecyclerViewAlbum = view.findViewById(R.id.recycler_view_album);
        mRecyclerViewAlbum.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mRecyclerViewAlbum.setAdapter(mAlbumAdapter);
        new downloadAlbumItems().execute(mAlbumURL);
        return view;
    }
    public class AlbumAdapter extends RecyclerView.Adapter<AlbumHolder> {
        List<Photo> mPhotos;
        AlbumAdapter() {
            mPhotos = new ArrayList<>();
/*            if (mPhotos.size() == 0) {
                new downloadAlbumItems().execute(mAlbumURL);
            }*/
        }
        @NonNull
        @Override
        public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new AlbumHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull AlbumHolder holder, int position) {
//            Photo photo = mAlbum.getPhotos().get(position);
            Photo photo = mPhotos.get(position);
            holder.bind(photo);
        }

        @Override
        public int getItemCount() {
//            return mAlbum.getPhotos().size();
            return mPhotos.size();
        }

        public void setPhotos(List<Photo> photos) {
            mPhotos = photos;
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


    private class downloadAlbumItems extends AsyncTask<String, Void, List<Photo>> {
        @Override
        protected List<Photo> doInBackground(String... albumURL) {
            List<Photo> photos;
            photos = AlbumSingleton.get(getActivity()).getPhotos(mAlbumURL);
            if (photos.size() == 0) {
                try {
                    photos = Parser.parserPhotoByAlbum(albumURL);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return photos;
        }
        @Override
        protected void onPostExecute(List<Photo> photos) {
//            mAlbum.setPhotos(photos);
            mAlbumAdapter.setPhotos(photos);
            mAlbumAdapter.notifyDataSetChanged();
            AlbumSingleton.get(getActivity()).add(photos);
//            mPhotos = photos;
        }
    }
}
