package com.careem.movietime.modules.details;

import android.content.Context;

import com.careem.movietime.application.MovieTimeApplication;
import com.careem.movietime.data.AuthDetails;
import com.careem.movietime.entities.common.details.MovieDetailsResponse;
import com.squareup.otto.Subscribe;

import java.util.List;


/**
 * This class is used to initialise the Movie Details Functions UI Components and Objects.
 *
 * @author AjinkyaD
 * @version 1.0
 */
class MovieDetailsPresenterImplementor implements MovieDetailsPresenter {

    private Context context;
    private MovieDetailsView movieDetailsView;
    private MovieDetailsInteractor movieDetailsInteractor;

    MovieDetailsPresenterImplementor(Context context, MovieDetailsView movieDetailsView) {

        this.context = context;
        this.movieDetailsView = movieDetailsView;
        movieDetailsInteractor = new MovieDetailsInteractorImplementor(context);
    }

    @Override
    public void fetchMovieDetails(int movieID) {


        MovieTimeApplication.getBusInstance().register(this);
        movieDetailsInteractor.fetchMoviesDetails(AuthDetails.APIToken, movieID);

    }


    @Override
    public void showLoader() {
        //Show Loader for fetching the initial details
        if (movieDetailsView != null) {
            movieDetailsView.showLoader();
        }
    }

    @Override
    public void initialiseControls() {
        if (movieDetailsView != null) {
            movieDetailsView.initialiseControls();
        }
    }

    @Override
    public void renderToolbarData() {
        if (movieDetailsView != null) {
            movieDetailsView.renderToolbarData();
        }
    }

    @Subscribe
    public void onResponse(Object responseDetails) {

        if (movieDetailsView != null) {

            movieDetailsView.hideLoader();
            if (responseDetails instanceof MovieDetailsResponse) {
                MovieTimeApplication.getBusInstance().unregister(this);

                MovieDetailsResponse movieDetailsResponse = (MovieDetailsResponse) responseDetails;

                movieDetailsView.renderMovieGenre(getMovieGenre(movieDetailsResponse.getGenres()));
                movieDetailsView.renderProductionCompanies(getProductionCompanies(movieDetailsResponse.getProduction_companies()));
                movieDetailsView.renderLanguages(getLanguages(movieDetailsResponse.getSpoken_languages()));
                movieDetailsView.renderDetails(movieDetailsResponse);
            }
        }
    }

    private String getLanguages(List<MovieDetailsResponse.SpokenLanguagesBean> spoken_languages) {
        StringBuilder spokenLanguages = new StringBuilder();
        for (MovieDetailsResponse.SpokenLanguagesBean spokenLanguagesBean :
                spoken_languages) {

            if (spokenLanguages.toString().trim().equals("")) {
                spokenLanguages = new StringBuilder(spokenLanguagesBean.getName());
            } else {
                spokenLanguages.append(" / ").append(spokenLanguagesBean.getName());
            }

        }
        return spokenLanguages.toString();

    }

    private String getProductionCompanies(List<MovieDetailsResponse.ProductionCompaniesBean> production_companies) {
        StringBuilder productionCompanies = new StringBuilder();
        for (MovieDetailsResponse.ProductionCompaniesBean productionCompaniesBean :
                production_companies) {

            if (productionCompanies.toString().trim().equals("")) {
                productionCompanies = new StringBuilder(productionCompaniesBean.getName());
            } else {
                productionCompanies.append(" / ").append(productionCompaniesBean.getName());
            }

        }
        return productionCompanies.toString();
    }

    private String getMovieGenre(List<MovieDetailsResponse.GenresBean> genres) {

        StringBuilder movieGenre = new StringBuilder();
        for (MovieDetailsResponse.GenresBean genresBean :
                genres) {

            if (movieGenre.toString().trim().equals("")) {
                movieGenre = new StringBuilder(genresBean.getName());
            } else {
                movieGenre.append(" / ").append(genresBean.getName());
            }

        }
        return movieGenre.toString();
    }

}
