package com.careem.movietime.webservicemanager;

/**
 * This class holds the Webservice functions/procedures used in the entire project
 *
 * @author AjinkyaD
 * @version 1.0
 */
public class WSMethod {

    private final static String backslash = "/";
    private final static int api_version = 3;

    private final static String discover = "discover";
    private final static String movie = "movie";


    //WS Methods to be used
    // /discover/movie
    public final static String movies_list = backslash + api_version + backslash + discover + backslash + movie;

    // /movie/{movie_id}
    public final static String movies_details = backslash + api_version + backslash + movie + backslash;
}