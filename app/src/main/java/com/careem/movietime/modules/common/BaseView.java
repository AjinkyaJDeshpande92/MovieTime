package com.careem.movietime.modules.common;

import android.widget.ImageView;

/**
 * This interface is used to declare the Base Activity Functions to be used/implemented by the Activity.
 *
 * @author AjinkyaD
 * @version 1.0
 */
public interface BaseView {

    void setTitle(String title);

    ImageView getPosterView();

    void initialiseAppBar(boolean expand);

    void initialiseFilterFAB(boolean show);

    void setFilterClickListener(ToolbarClickListener toolbarClickListener);

}
