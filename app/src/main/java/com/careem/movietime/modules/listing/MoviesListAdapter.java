package com.careem.movietime.modules.listing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.careem.movietime.R;
import com.careem.movietime.entities.common.listing.MoviesListResponse;
import com.careem.movietime.modules.common.ItemActionListener;
import com.careem.movietime.utilities.Utilities;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * This adapter is used to render the Restaurant Working Hour Details UI to the User
 *
 * @author AjinkyaD
 * @version 1.0
 */

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MovieItemHolder> {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<MoviesListResponse> arrlstData;
    private ItemActionListener itemActionListener;

    public MoviesListAdapter(Context context, ArrayList<MoviesListResponse> arrlstData, ItemActionListener itemActionListener) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.arrlstData = arrlstData;
        this.itemActionListener = itemActionListener;
    }

    @Override
    public MovieItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieItemHolder(inflater.inflate(R.layout.custom_movie_list_item, parent, false));
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(final MovieItemHolder holder, final int position) {
        final MoviesListResponse moviesListResponse = arrlstData.get(position);

        holder.txtvwRatings.setText(Utilities.formatDecimalNumber(moviesListResponse.getVote_average(), "#.#"));
        holder.txtvwReleaseDate.setText(String.format("Release Date - %s", moviesListResponse.getRelease_date()));
        holder.txtvwLanguage.setText(String.format("Language - %s", moviesListResponse.getOriginal_language().toUpperCase()));
        holder.txtvwMovieName.setText(moviesListResponse.getTitle());
        holder.txtvwVotesCount.setText(String.format("%d", moviesListResponse.getVote_count()));

        if (moviesListResponse.isAdult()) {
            holder.imgvw18.setVisibility(View.VISIBLE);
        } else {
            holder.imgvw18.setVisibility(View.INVISIBLE);
        }
        if (moviesListResponse.getPoster_path() != null) {
            Picasso.get()
                    .load(Utilities.getPosterPath(moviesListResponse.getPoster_path(), true))
                    .placeholder(R.drawable.image_placeholder)
                    .into(holder.imgvwMoviePoster);
        } else {
            Picasso.get()
                    .load(R.drawable.error_placeholder)
                    .placeholder(R.drawable.error_placeholder)
                    .into(holder.imgvwMoviePoster);
        }

        holder.rellayMovieItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (itemActionListener != null) {
                    itemActionListener.onItemClicked(moviesListResponse, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrlstData.size();
    }


    public void add(MoviesListResponse newData) {

        arrlstData.add(arrlstData.size(), newData);
        notifyItemInserted(arrlstData.size());

    }


    static class MovieItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ximgvwMoviePoster)
        ImageView imgvwMoviePoster;
        @BindView(R.id.ximgvwLikes)
        ImageView imgvwLikes;
        @BindView(R.id.xtxtvwRatings)
        TextView txtvwRatings;
        @BindView(R.id.xtxtvwMovieName)
        TextView txtvwMovieName;
        @BindView(R.id.xtxtvwLanguage)
        TextView txtvwLanguage;
        @BindView(R.id.ximgvw18)
        ImageView imgvw18;
        @BindView(R.id.xtxtvwVotesCount)
        TextView txtvwVotesCount;
        @BindView(R.id.xtxtvwReleaseDate)
        TextView txtvwReleaseDate;
        @BindView(R.id.xrellayMovieItem)
        RelativeLayout rellayMovieItem;

        MovieItemHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
