package com.careem.movietime.webservicemanager;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.careem.movietime.entities.common.FailureResponse;
import com.google.gson.Gson;

/**
 * This class is used to handle the response from the Webservice.This class implements the Volley Library onResponse and onErrorResponse Callbacks
 * <p>
 * Functions-
 * 1)Handle the response from the Webservice
 * 2)Distinguish between Success/Failure
 * 3)Send the Data to Calling Module
 *
 * @author AjinkyaD
 * @version 1.0
 */
class WebserviceResponseImplementor implements Response.ErrorListener, Response.Listener {
    private WebCall callType;
    private WebserviceResponseListener webserviceResponseListener;
    private Context context;

    /**
     * The Listener Initialisation Class.The response will be redirected to the caller screen
     *
     * @param context                    - Current Context
     * @param callType                   - The Web Method Call Type
     * @param webserviceResponseListener - The Caller Screen Listener
     */
    public WebserviceResponseImplementor(Context context, WebCall callType, WebserviceResponseListener webserviceResponseListener) {
        this.callType = callType;
        this.webserviceResponseListener = webserviceResponseListener;
        this.context = context;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        try {
            if (webserviceResponseListener != null) {

                try {
                    webserviceResponseListener.onFailure(error.networkResponse.statusCode, callType, new Gson().fromJson(new String(error.networkResponse.data, "UTF-8"), FailureResponse.class));
                } catch (Exception e) {
                    e.printStackTrace();
                    webserviceResponseListener.onFailure(error.networkResponse.statusCode, callType, new FailureResponse());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponse(Object response) {
        if (webserviceResponseListener != null) {

            try {
                Log.e("RESPONSE ", response.toString());
                webserviceResponseListener.onSuccess(200, callType, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
