package com.careem.movietime.webservicemanager;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.careem.movietime.application.MovieTimeApplication;
import com.careem.movietime.utilities.Utilities;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to make the Webservice requests.All the Webservice request are called via callWebservice().
 * The Webservice request is constructed here and later on added in the Volley request queue.
 *
 * @author AjinkyaD
 * @version 1.0
 */
public class WebserviceManager {
    private WebserviceResponseListener webserviceResponseListener;
    private Context context;
    private final static String TAG = WebserviceManager.class.getName();
    private Utilities utilities;

    public WebserviceManager(Context context) {
        this.context = context;
        utilities = new Utilities(context);
    }

    /**
     * Method to set WebResponse Listener form the calling class, so that the webService Manager an pass the Response data to the calling class.
     *
     * @param webserviceResponseListener -The Caller Screen
     */
    public void setWebResponseListener(WebserviceResponseListener webserviceResponseListener) {
        this.webserviceResponseListener = webserviceResponseListener;
    }

    /**
     * This function adds the webservice call in the Volley Calls queue.
     *
     * @param url               - The URL which needs to be called
     * @param postObject        - The Post JSON Object (can be null)
     * @param callType          - Web Method call type
     * @param methodCall        - GET/POST
     * @param headerParams      - Customised Header Parameters
     * @param volleyRequestType - The Volley Request Type
     */
    private void callWebservice(final String url, Object postObject, final WebCall callType, int methodCall,
                                final HashMap<String, String> headerParams, VolleyRequestType volleyRequestType) {
        JSONObject postJSON = null;
        if (postObject == null) {
            postJSON = null;
        } else if (postObject instanceof JSONObject) {
            postJSON = (JSONObject) postObject;
        }
        WebserviceResponseImplementor webResponseListener = new WebserviceResponseImplementor(context, callType, webserviceResponseListener
        );
        if (volleyRequestType == VolleyRequestType.JSONOBJECT) {
            // The request is added into Volley request Queue to be executed
            MovieTimeApplication.getInstance().addToRequestQueue(getJSONObjectRequest(url, postJSON, methodCall, headerParams, webResponseListener), null);
        } else if (volleyRequestType == VolleyRequestType.JSONARRAY) {
            // The request is added into Volley request Queue to be executed
            MovieTimeApplication.getInstance().addToRequestQueue(getJSONArrayRequest(url, postJSON, methodCall, headerParams, webResponseListener), null);
        }

    }

    /**
     * This function is used to generate the JSONArray request .This object in then added into Volley Request Queue
     *
     * @param url                 - The URL which needs to be called
     * @param postJSON            - The Post JSON Object (can be null)
     * @param methodCall          - GET/POST
     * @param headerParams        - Customised Header Parameters
     * @param webResponseListener - The Response Listener
     * @return - The generated JSONArray Request
     */
    private JsonArrayRequest getJSONArrayRequest(String url, final JSONObject postJSON, int methodCall, final HashMap<String, String> headerParams, WebserviceResponseImplementor webResponseListener) {
        return new JsonArrayRequest(methodCall, url, null, webResponseListener, webResponseListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                if (postJSON == null) {
                    // Special Condition.Need to handle if post body object is null
                    // If post json object is present .It goes in the Body Content Type Method.
                    headers.put("Content-Type", "application/json; charset=utf-8");
                }

                if (headerParams != null) {
                    //Add the custom headers if any in the request
                    for (Map.Entry<String, String> entry : headerParams.entrySet()) {
                        headers.put(entry.getKey(), entry.getValue());
                    }
                }
                return headers;
            }

            @Override
            public String getBodyContentType() {
                // This function is called when the POST body is not empty.
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    if (postJSON == null)
                        return super.getBody();
                    else {
                        String json = postJSON.toString();
                        return json.getBytes(getParamsEncoding());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return super.getBody();
            }
        };
    }

    /**
     * This function is used to generate the JSONObject request .This object in then added into Volley Request Queue
     *
     * @param url                 - The URL which needs to be called
     * @param postJSON            - The Post JSON Object (can be null)
     * @param methodCall          - GET/POST
     * @param headerParams        - Customised Header Parameters
     * @param webResponseListener - The Response Listener
     * @return - The generated JSONObject Request
     */
    private JsonObjectRequest getJSONObjectRequest(String url, final JSONObject postJSON, int methodCall, final HashMap<String, String> headerParams, WebserviceResponseImplementor webResponseListener) {

        return new JsonObjectRequest(methodCall, url, null, webResponseListener, webResponseListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                if (postJSON == null) {
                    // Special Condition.Need to handle if post body object is null
                    // If post json object is present .It goes in the Body Content Type Method.
                    headers.put("Content-Type", "application/json; charset=utf-8");
                }

                if (headerParams != null) {
                    //Add the custom headers if any in the request
                    for (Map.Entry<String, String> entry : headerParams.entrySet()) {
                        headers.put(entry.getKey(), entry.getValue());
                    }
                }
                return headers;
            }

            @Override
            public String getBodyContentType() {
                // This function is called when the POST body is not empty.
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    if (postJSON == null)
                        return super.getBody();
                    else {
                        String json = postJSON.toString();
                        return json.getBytes(getParamsEncoding());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return super.getBody();
            }
        };
    }

    /**
     * This function generates the Web URL to be called without blank spaces.
     *
     * @param url -The URL which needs to be encoded
     * @return - The Encoded URL
     * @throws UnsupportedEncodingException
     */
    private String generateWebURL(String url) throws UnsupportedEncodingException {
        url = url.replaceAll(" ", "%20");
        url = url.replaceAll("\n", "%0A");
        return url;
    }

    public void fetchMoviesList(String apiToken, int iPageNumber, Date today, Date fromDate) {

        try {

            // The Get Parameters for the current Request
            HashMap<String, String> sessionIdParameters = new HashMap<>();
            sessionIdParameters.put("api_key", apiToken);

            //We need latest movies first
            //So this is hardcoded. sortby desc release date
            sessionIdParameters.put("sort_by", "release_date.desc");

            //We need only the latest movies
            //Which means the movies which are released till today
            sessionIdParameters.put("release_date.lte", Utilities.formattedDateFromDate(today, "yyyy-MM-dd"));

            //If we have added filter
            //We need to add the date greater than parameter

            if (fromDate != null) {
                sessionIdParameters.put("release_date.gte", Utilities.formattedDateFromDate(fromDate, "yyyy-MM-dd"));
            }

            sessionIdParameters.put("page", "" + iPageNumber);

            String url = WebURL.WebServiceBaseUrl + WSMethod.movies_list + utilities.getURLParamsConstructed(sessionIdParameters);
            url = generateWebURL(url);

            callWebservice(url, null, WebCall.MOVIES_LIST, Request.Method.GET, null, VolleyRequestType.JSONOBJECT);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void fetchMovieDetails(String apiToken, int movieId) {

        try {

            // The Get Parameters for the current Request
            HashMap<String, String> sessionIdParameters = new HashMap<>();
            sessionIdParameters.put("api_key", apiToken);

            String url = WebURL.WebServiceBaseUrl + WSMethod.movies_details + movieId + utilities.getURLParamsConstructed(sessionIdParameters);
            url = generateWebURL(url);

            callWebservice(url, null, WebCall.MOVIE_DETAILS, Request.Method.GET, null, VolleyRequestType.JSONOBJECT);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
