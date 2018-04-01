package com.careem.movietime.modules.common;

/**
 * This interface is used to declare the Base Activity Functions to be used/implemented by the Activity.
 *
 * @author AjinkyaD
 * @version 1.0
 */
public interface CommonView {

    void showLoader();

    void hideLoader();

    void initialiseControls();

    void renderToolbarData();
}
