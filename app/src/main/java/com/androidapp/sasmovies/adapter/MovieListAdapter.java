package com.androidapp.sasmovies.adapter;

import android.content.Context;
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
import com.androidapp.sasmovies.util.Util;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter {

    private Context mContext;

    private ItemClickDelegate itemClickDelegate;

    private List<Movie> mList;

    public MovieListAdapter(Context c, ItemClickDelegate cd, List<Movie> list) {
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
    public MovieListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movie_item, parent, false);
        MovieListAdapter.ViewHolder vh = new MovieListAdapter.ViewHolder(v);

        return vh;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        final Movie entity = mList.get(position);

        final MovieListAdapter.ViewHolder customViewHolder = (MovieListAdapter.ViewHolder) viewHolder;

        customViewHolder.txName.setText(entity.getTitle());

        customViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickDelegate.onItemClick(entity.getId(), v);
            }
        });

        if( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP ) {
            customViewHolder.imgPoster.setTransitionName(mContext.getString(R.string.transition_detail) + entity.getId());
        }

        String url = "https://image.tmdb.org/t/p/w500/" + entity.getPosterPath();
        Util.loadImage(url, customViewHolder.progressBar, customViewHolder.imgPoster);

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
