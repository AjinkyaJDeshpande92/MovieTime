package com.careem.movietime.modules.common;

import android.app.Fragment;

/**
 * This interface is used to declare the Fragment Instance Functions to be used/implemented by the Activity.
 *
 * @author AjinkyaD
 * @version 1.0
 */
public interface FragmentInstanceHandler {
    /**
     * This function is used to handle the fragment operations.
     * @param currentFragment - The Fragment to be added/updated
     * @param fragmentName    - The Fragment Name
     * @param addToBackStack  - Whether to add to back-stack or not
     * @param removePrevious  - Whether to remove the previous instance of the fragment if found in stack
     */
    void changeFragment(Fragment currentFragment, FragmentName fragmentName, boolean addToBackStack, boolean removePrevious);
}
