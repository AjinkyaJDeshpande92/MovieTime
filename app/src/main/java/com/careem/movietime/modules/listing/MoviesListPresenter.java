package com.careem.movietime.modules.listing;


/**
 * This interface is used to declare the Movie List Functions to be used/implemented by the Presenter.
 *
 * @author AjinkyaD
 * @version 1.0
 */
interface MoviesListPresenter{

    void fetchLatestMovies(int dayOfMonth, int monthOfYear, int year);

    void resetPageNumber();

    void showLoader();

    void initialiseControls();

    void renderToolbarData();

    void showDatePicker();
}
