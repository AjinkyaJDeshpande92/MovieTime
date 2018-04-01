package com.careem.movietime.modules.listing;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RelativeLayout;

import com.careem.movietime.R;
import com.careem.movietime.entities.common.listing.MoviesListResponse;
import com.careem.movietime.modules.MoviesActivity;
import com.careem.movietime.modules.common.FragmentInstanceHandler;
import com.careem.movietime.modules.common.FragmentName;
import com.careem.movietime.modules.common.ItemActionListener;
import com.careem.movietime.modules.common.ToolbarClickListener;
import com.careem.movietime.modules.details.MovieDetailsFragment;
import com.careem.movietime.utilities.Utilities;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MoviesListFragment extends Fragment implements MoviesListView, ItemActionListener, SwipeRefreshLayout.OnRefreshListener, ToolbarClickListener {

    @BindView(R.id.xcVwMovies)
    RecyclerView rcVwMovies;
    @BindView(R.id.xswpLayMoviesList)
    SwipeRefreshLayout swpLayMoviesList;
    @BindView(R.id.xrellayMainParent)
    RelativeLayout rellayMainParent;
    Unbinder unbinder;

    MoviesListPresenter moviesListPresenter;
    MoviesListAdapter moviesListAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<MoviesListResponse> movies;
    RecyclerView.OnScrollListener endListener;

    FragmentInstanceHandler fragmentInstanceHandler;

    int dayOfMonth = -1, monthOfYear = -1, year = -1;

    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_listing, vg, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initObjects();
    }

    public void initObjects() {

        movies = new ArrayList<>();
        swpLayMoviesList.setOnRefreshListener(this);
        moviesListPresenter = new MoviesListPresenterImplementor(getActivity(), this);
        showUI();
        moviesListPresenter.showLoader();
        moviesListPresenter.fetchLatestMovies(dayOfMonth, (monthOfYear + 1), year);

    }

    public void showUI() {
        moviesListPresenter.initialiseControls();
        moviesListPresenter.renderToolbarData();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void renderMoviesList(ArrayList<MoviesListResponse> results, boolean fetchMoreData) {

        movies.clear();
        movies.addAll(results);
        moviesListAdapter = new MoviesListAdapter(getActivity(), movies, this);

        rcVwMovies.setAdapter(moviesListAdapter);

        linearLayoutManager = new LinearLayoutManager(getActivity());

        rcVwMovies.setLayoutManager(linearLayoutManager);

        //Add Scroll Listener so that we can detect the End and Fetch the next page of the movies
        endListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //We need this listener to check for the Last item in the List and call the pagination
                //As the page size on the APIs is 20 , we call the APIs for smooth paging after 15 records are reached
                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() >= (movies.size() - 5)) {
                    //Last item is visible
                    rcVwMovies.removeOnScrollListener(this);
                    moviesListPresenter.fetchLatestMovies(dayOfMonth, (monthOfYear + 1), year);
                }

            }
        };

        if (fetchMoreData) {
            rcVwMovies.addOnScrollListener(endListener);
        }

        if (swpLayMoviesList.isRefreshing()) {
            swpLayMoviesList.setRefreshing(false);
        }
    }

    @Override
    public void appendMovieList(ArrayList<MoviesListResponse> results, boolean fetchMoreData) {
        movies.addAll(results);
        for (int i = 0; i < results.size(); i++) {
            moviesListAdapter.add(results.get(i));
        }
        if (fetchMoreData) {
            rcVwMovies.addOnScrollListener(endListener);

        }
    }

    @Override
    public void showDatePicker(Calendar instance) {

        this.dayOfMonth = instance.get(Calendar.DAY_OF_MONTH);
        this.monthOfYear = instance.get(Calendar.MONTH);
        this.year = instance.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        moviesListPresenter.resetPageNumber();
                        moviesListPresenter.showLoader();
                        moviesListPresenter.fetchLatestMovies(dayOfMonth, (monthOfYear + 1), year);

                    }
                }, this.year, this.monthOfYear, this.dayOfMonth);
        datePickerDialog.show();
    }

    @Override
    public void onItemClicked(Object currentObject, int position) {

        if (currentObject instanceof MoviesListResponse) {
            //Movie is selected by the User
            MoviesListResponse selectedMovie = (MoviesListResponse) currentObject;
            MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", selectedMovie);
            movieDetailsFragment.setArguments(bundle);
            fragmentInstanceHandler.changeFragment(movieDetailsFragment, FragmentName.MOVIE_DETAILS, true, true);
        }

    }

    @Override
    public void onRefresh() {

        moviesListPresenter.resetPageNumber();
        moviesListPresenter.fetchLatestMovies(dayOfMonth, (monthOfYear + 1), year);
    }

    @Override
    public void showLoader() {
        Utilities.showLoader(getString(R.string.fetching_movies), getActivity());
    }

    @Override
    public void hideLoader() {

        Utilities.dismissLoader();
    }

    @Override
    public void initialiseControls() {
        ((MoviesActivity) getActivity()).initialiseAppBar(false);
        ((MoviesActivity) getActivity()).initialiseFilterFAB(true);
        ((MoviesActivity) getActivity()).setFilterClickListener(this);

    }

    @Override
    public void renderToolbarData() {
        ((MoviesActivity) getActivity()).setTitle(getResources().getString(R.string.app_name));

        Picasso.get()
                .load(R.drawable.movie_time)
                .placeholder(R.drawable.movie_time)
                .into(((MoviesActivity) getActivity()).getPosterView());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentInstanceHandler = (FragmentInstanceHandler) getActivity();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        fragmentInstanceHandler = (FragmentInstanceHandler) getActivity();
    }

    @Override
    public void onFilterIconClick() {

        moviesListPresenter.showDatePicker();
    }
}
