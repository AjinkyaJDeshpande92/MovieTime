package com.careem.movietime.modules.details;

import android.content.Context;

import com.careem.movietime.application.MovieTimeApplication;
import com.careem.movietime.entities.common.details.MovieDetailsResponse;
import com.careem.movietime.entities.common.listing.MoviesListResponseWrapper;
import com.careem.movietime.webservicemanager.WebCall;
import com.careem.movietime.webservicemanager.WebserviceManager;
import com.careem.movietime.webservicemanager.WebserviceResponseListener;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * This class is used as a Interactor layer for the Movies List Module.
 * <p>
 * The Web call/Caching/Parsing Logic for the UI is handled in this layer.
 * Auth Token generation is performed on the API for the same.
 *
 * @author AjinkyaD
 * @version 1.0
 */
public class MovieDetailsInteractorImplementor implements MovieDetailsInteractor, WebserviceResponseListener {

    private Context context;
    private WebserviceManager webserviceManager;

    MovieDetailsInteractorImplementor(Context context) {
        this.context = context;
        webserviceManager = new WebserviceManager(context);
        webserviceManager.setWebResponseListener(this);
    }

    @Override
    public void onSuccess(int statusCode, WebCall webCall, Object response) {
        Gson gson = new Gson();
        JSONObject resJsonObject = (JSONObject) response;
        switch (webCall) {
            case MOVIE_DETAILS:
                MovieTimeApplication.getBusInstance().post(gson.fromJson(resJsonObject.toString(), MovieDetailsResponse.class));
                break;

        }
    }

    @Override
    public void onFailure(int statusCode, WebCall webCall, Object response) {
        switch (webCall) {
            case MOVIE_DETAILS:
                MovieTimeApplication.getBusInstance().post(response);
                break;
        }
    }


    @Override
    public void fetchMoviesDetails(String APIToken, int movieID) {
        webserviceManager.fetchMovieDetails(APIToken, movieID);
    }
}
