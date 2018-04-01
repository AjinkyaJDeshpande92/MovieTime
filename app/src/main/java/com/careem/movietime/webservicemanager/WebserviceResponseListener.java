package com.careem.movietime.webservicemanager;

/**
 * This interface is used to declare the Webservice expected response states from server
 *
 * @author AjinkyaD
 * @version 1.0
 */
public interface WebserviceResponseListener {
    void onSuccess(int statusCode, WebCall webCall, Object response);

    void onFailure(int statusCode, WebCall webCall, Object response);
}

