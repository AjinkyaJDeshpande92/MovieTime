package com.careem.movietime.modules.details;


/**
 * This interface is used to declare the Movie List Functions to be used/implemented by the Presenter.
 *
 * @author AjinkyaD
 * @version 1.0
 */
interface MovieDetailsPresenter {

    void fetchMovieDetails(int movieID);

    void showLoader();

    void initialiseControls();

    void renderToolbarData();
}
