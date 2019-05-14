package com.androidapp.sasmovies.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.androidapp.sasmovies.R;
import com.androidapp.sasmovies.delegate.ItemClickDelegate;
import com.androidapp.sasmovies.entity.Movie;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter {

    private Context mContext;

    private ItemClickDelegate itemClickDelegate;

    private List<Movie> mList;

    public MovieAdapter(Context c, ItemClickDelegate cd, List<Movie> list) {
        mContext = c;
        itemClickDelegate = cd;
        mList = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txName;
        public ProgressBar progressBar;
        public ImageView imgPoster;

        public ViewHolder(View view) {
            super(view);

            txName = view.findViewById(R.id.txName);
            progressBar = view.findViewById(R.id.progressBar);
            imgPoster = view.findViewById(R.id.imgPoster);
        }
    }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movie_item, parent, false);
        MovieAdapter.ViewHolder vh = new MovieAdapter.ViewHolder(v);

        return vh;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        final Movie entity = mList.get(position);

        final MovieAdapter.ViewHolder featuredViewHolder = (MovieAdapter.ViewHolder) viewHolder;

        featuredViewHolder.txName.setText(entity.getTitle());

        featuredViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickDelegate.onItemClick(entity.getId() + "", v);
            }
        });

        if( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP ) {
            featuredViewHolder.imgPoster.setTransitionName(mContext.getString(R.string.transition_detail) + entity.getId());
        }

        String url = "https://image.tmdb.org/t/p/w500/" + entity.getPosterPath();

        ImageLoader.getInstance().displayImage(url, featuredViewHolder.imgPoster, null, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

                if (featuredViewHolder.progressBar != null) {
                    featuredViewHolder.progressBar.setVisibility(View.VISIBLE);
                }

                view.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                if (featuredViewHolder.progressBar != null) {
                    featuredViewHolder.progressBar.setVisibility(View.GONE);
                }

                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                if (featuredViewHolder.progressBar != null) {
                    featuredViewHolder.progressBar.setVisibility(View.GONE);
                }

                view.setVisibility(View.VISIBLE);
            }
        });

    }

    public Movie getById(int id ){

        for( Movie ee : mList ){

            if(ee.getId() == id){
                return ee;
            }

        }

        return null;

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
