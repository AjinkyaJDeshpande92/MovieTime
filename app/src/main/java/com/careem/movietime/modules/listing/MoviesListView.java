package com.careem.movietime.modules.listing;


import com.careem.movietime.entities.common.listing.MoviesListResponse;
import com.careem.movietime.modules.common.CommonView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * This interface is used to declare the Base Activity Functions to be used/implemented by the Activity.
 *
 * @author AjinkyaD
 * @version 1.0
 */
interface MoviesListView extends CommonView {

    void renderMoviesList(ArrayList<MoviesListResponse> results, boolean fetchMoreData);

    void appendMovieList(ArrayList<MoviesListResponse> results, boolean fetchMoreData);


    void showDatePicker(Calendar instance);
}
