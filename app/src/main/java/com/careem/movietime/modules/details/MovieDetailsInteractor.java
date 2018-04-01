package com.careem.movietime.modules.details;

/**
 * This interface is used to declare the functions that are needed to be implemented by the Movies List Interactor Implementor.
 *
 * @author AjinkyaD
 * @version 1.0
 */

public interface MovieDetailsInteractor {

    void fetchMoviesDetails(String APIToken, int movieID);
}
