package com.careem.movietime.modules.listing;

import android.content.Context;


import com.careem.movietime.application.MovieTimeApplication;
import com.careem.movietime.entities.common.listing.MoviesListResponseWrapper;
import com.careem.movietime.webservicemanager.WebCall;
import com.careem.movietime.webservicemanager.WebserviceManager;
import com.careem.movietime.webservicemanager.WebserviceResponseListener;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Date;

/**
 * This class is used as a Interactor layer for the Movies List Module.
 * <p>
 * The Web call/Caching/Parsing Logic for the UI is handled in this layer.
 * Auth Token generation is performed on the API for the same.
 *
 * @author AjinkyaD
 * @version 1.0
 */
public class MoviesListInteractorImplementor implements MoviesListInteractor, WebserviceResponseListener {

    private Context context;
    private WebserviceManager webserviceManager;

    MoviesListInteractorImplementor(Context context) {
        this.context = context;
        webserviceManager = new WebserviceManager(context);
        webserviceManager.setWebResponseListener(this);
    }

    @Override
    public void onSuccess(int statusCode, WebCall webCall, Object response) {
        Gson gson = new Gson();
        JSONObject resJsonObject = (JSONObject) response;
        switch (webCall) {
            case MOVIES_LIST:
                MovieTimeApplication.getBusInstance().post(gson.fromJson(resJsonObject.toString(), MoviesListResponseWrapper.class));
                break;

        }
    }

    @Override
    public void onFailure(int statusCode, WebCall webCall, Object response) {
        switch (webCall) {
            case MOVIES_LIST:
                MovieTimeApplication.getBusInstance().post(response);
                break;
        }
    }


    @Override
    public void fetchMoviesList(String APIToken, int iPageNumber, Date today, Date fromDate) {
        webserviceManager.fetchMoviesList(APIToken, iPageNumber, today,fromDate);
    }
}
