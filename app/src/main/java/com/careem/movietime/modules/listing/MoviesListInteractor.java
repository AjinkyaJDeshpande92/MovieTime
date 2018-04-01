package com.careem.movietime.modules.listing;

import java.util.Date;

/**
 * This interface is used to declare the functions that are needed to be implemented by the Movies List Interactor Implementor.
 *
 * @author AjinkyaD
 * @version 1.0
 */

public interface MoviesListInteractor {

    void fetchMoviesList(String APIToken, int iPageNumber, Date today, Date fromDate);
}
