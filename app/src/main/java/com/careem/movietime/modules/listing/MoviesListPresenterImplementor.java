package com.careem.movietime.modules.listing;

import android.content.Context;

import com.careem.movietime.application.MovieTimeApplication;
import com.careem.movietime.data.AuthDetails;
import com.careem.movietime.entities.common.listing.MoviesListResponseWrapper;
import com.squareup.otto.Subscribe;

import java.util.Calendar;
import java.util.Date;


/**
 * This class is used to initialise the Movie List Functions UI Components and Objects.
 *
 * @author AjinkyaD
 * @version 1.0
 */
class MoviesListPresenterImplementor implements MoviesListPresenter {

    private Context context;
    private MoviesListView moviesListView;
    private MoviesListInteractor moviesListInteractor;
    private int iPageNumber = 0;

    MoviesListPresenterImplementor(Context context, MoviesListView moviesListView) {

        this.context = context;
        this.moviesListView = moviesListView;
        moviesListInteractor = new MoviesListInteractorImplementor(context);
    }

    @Override
    public void fetchLatestMovies(int dayOfMonth, int monthOfYear, int year) {

        Date fromDate = null;

        if (dayOfMonth != -1) {
            Calendar fromCalendar = Calendar.getInstance();
            fromCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            fromCalendar.set(Calendar.YEAR, year);
            fromCalendar.set(Calendar.MONTH, monthOfYear-1);
            fromDate = fromCalendar.getTime();
        }

        MovieTimeApplication.getBusInstance().register(this);
        moviesListInteractor.fetchMoviesList(AuthDetails.APIToken, ++iPageNumber, new Date(), fromDate);

    }

    @Override
    public void resetPageNumber() {
        iPageNumber = 0;
    }

    @Override
    public void showLoader() {
        //Show Loader for fetching the initial details
        if (moviesListView != null) {
            moviesListView.showLoader();
        }
    }

    @Override
    public void initialiseControls() {
        if (moviesListView != null) {
            moviesListView.initialiseControls();
        }
    }

    @Override
    public void renderToolbarData() {
        if (moviesListView != null) {
            moviesListView.renderToolbarData();
        }
    }

    @Override
    public void showDatePicker() {

        if (moviesListView != null) {
            moviesListView.showDatePicker(Calendar.getInstance());
        }
    }

    @Subscribe
    public void onResponse(Object responseDetails) {

        if (moviesListView != null) {

            if (responseDetails instanceof MoviesListResponseWrapper) {
                MovieTimeApplication.getBusInstance().unregister(this);
                MoviesListResponseWrapper moviesListResponseWrapper = (MoviesListResponseWrapper) responseDetails;

                if (iPageNumber == 1) {
                    //First Page is fetched
                    moviesListView.hideLoader();
                    moviesListView.renderMoviesList(moviesListResponseWrapper.getResults(), moviesListResponseWrapper.getPage() <= moviesListResponseWrapper.getTotal_pages());
                } else {
                    moviesListView.appendMovieList(moviesListResponseWrapper.getResults(), moviesListResponseWrapper.getPage() <= moviesListResponseWrapper.getTotal_pages());
                }

            }
        }
    }

}
