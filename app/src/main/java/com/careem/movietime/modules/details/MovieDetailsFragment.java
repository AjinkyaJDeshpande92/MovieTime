package com.careem.movietime.modules.details;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.careem.movietime.R;
import com.careem.movietime.entities.common.details.MovieDetailsResponse;
import com.careem.movietime.entities.common.listing.MoviesListResponse;
import com.careem.movietime.modules.MoviesActivity;
import com.careem.movietime.utilities.Utilities;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MovieDetailsFragment extends Fragment implements MovieDetailsView {

    @BindView(R.id.xrellayMainParent)
    RelativeLayout rellayMainParent;
    Unbinder unbinder;
    MovieDetailsPresenter movieDetailsPresenter;
    MoviesListResponse selectedMovie;
    @BindView(R.id.xtxtvwMovieTagLine)
    TextView txtvwMovieTagLine;
    @BindView(R.id.xtxtvwLanguage)
    TextView txtvwLanguage;
    @BindView(R.id.ximgvw18)
    ImageView imgvw18;
    @BindView(R.id.xtxtvwGenre)
    TextView txtvwGenre;
    @BindView(R.id.xtxtvwMovieOverview)
    TextView txtvwMovieOverview;
    @BindView(R.id.ximgvwLikes)
    ImageView imgvwLikes;
    @BindView(R.id.xtxtvwRatings)
    TextView txtvwRatings;
    @BindView(R.id.ximgvwVotes)
    ImageView imgvwVotes;
    @BindView(R.id.xtxtvwVotesCount)
    TextView txtvwVotesCount;
    @BindView(R.id.xtxtvwReleaseDate)
    TextView txtvwReleaseDate;
    @BindView(R.id.xtxtvwProductionCompanies)
    TextView txtvwProductionCompanies;
    @BindView(R.id.xtxtvwReleaseStatus)
    TextView txtvwReleaseStatus;
    @BindView(R.id.xtxtvwSpokenLanguage)
    TextView txtvwSpokenLanguage;

    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details, vg, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            selectedMovie = (MoviesListResponse) bundle.getSerializable("data");
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initObjects();
    }

    public void initObjects() {

        movieDetailsPresenter = new MovieDetailsPresenterImplementor(getActivity(), this);
        movieDetailsPresenter.initialiseControls();
        movieDetailsPresenter.renderToolbarData();
        movieDetailsPresenter.showLoader();
        movieDetailsPresenter.fetchMovieDetails(selectedMovie.getId());
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void showLoader() {
        Utilities.showLoader(getString(R.string.fetching_movie_details), getActivity());
    }

    @Override
    public void hideLoader() {

        Utilities.dismissLoader();
    }

    @Override
    public void initialiseControls() {
        ((MoviesActivity) getActivity()).initialiseAppBar(true);
        ((MoviesActivity) getActivity()).initialiseFilterFAB(false);
    }

    @Override
    public void renderToolbarData() {
        ((MoviesActivity) getActivity()).setTitle(selectedMovie.getTitle());

        Picasso.get()
                .load(Utilities.getPosterPath(selectedMovie.getPoster_path(), false))
                .placeholder(R.drawable.image_placeholder)
                .into(((MoviesActivity) getActivity()).getPosterView());
    }

    @Override
    public void renderMovieGenre(String genre) {

        if (!genre.trim().equals("")) {
            txtvwGenre.setText(String.format("Genre - %s", genre));
        } else {
            txtvwGenre.setVisibility(View.GONE);
        }
    }

    @Override
    public void renderProductionCompanies(String production_companies) {
        if (!production_companies.trim().equals("")) {
            txtvwProductionCompanies.setText(String.format("Production House - %s", production_companies));
        } else {
            txtvwProductionCompanies.setVisibility(View.GONE);
        }
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void renderDetails(MovieDetailsResponse movieDetailsResponse) {

        if (movieDetailsResponse.getOverview() != null && !movieDetailsResponse.getOverview().trim().equals("")) {
            txtvwMovieOverview.setText(String.format("Overview - %s", movieDetailsResponse.getOverview()));
        } else {
            txtvwMovieOverview.setVisibility(View.GONE);
        }

        if (movieDetailsResponse.getTagline() != null && !movieDetailsResponse.getTagline().trim().equals("")) {
            txtvwMovieTagLine.setText(movieDetailsResponse.getTagline());
        } else {
            txtvwMovieTagLine.setVisibility(View.GONE);
        }

        txtvwRatings.setText(Utilities.formatDecimalNumber(movieDetailsResponse.getVote_average(), "#.#"));

        txtvwReleaseDate.setText(String.format("Release Date - %s", movieDetailsResponse.getRelease_date()));

        txtvwLanguage.setText(String.format("Language - %s", movieDetailsResponse.getOriginal_language().toUpperCase()));

        txtvwVotesCount.setText(String.format("%d", movieDetailsResponse.getVote_count()));

        if (movieDetailsResponse.isAdult()) {
            imgvw18.setVisibility(View.VISIBLE);
        } else {
            imgvw18.setVisibility(View.INVISIBLE);
        }

        txtvwReleaseStatus.setText(String.format("Release Status - %s", movieDetailsResponse.getStatus()));


    }

    @Override
    public void renderLanguages(String spoken_languages) {
        if (!spoken_languages.trim().equals("")) {
            txtvwSpokenLanguage.setText(String.format("Spoken Language - %s", spoken_languages));
        } else {
            txtvwSpokenLanguage.setVisibility(View.GONE);
        }
    }
}
