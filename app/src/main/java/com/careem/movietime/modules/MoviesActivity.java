package com.careem.movietime.modules;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.careem.movietime.R;
import com.careem.movietime.modules.common.BaseView;
import com.careem.movietime.modules.common.FragmentInstanceHandler;
import com.careem.movietime.modules.common.FragmentName;
import com.careem.movietime.modules.common.ToolbarClickListener;
import com.careem.movietime.modules.details.MovieDetailsFragment;
import com.careem.movietime.modules.listing.MoviesListFragment;

public class MoviesActivity extends AppCompatActivity implements FragmentInstanceHandler, View.OnClickListener, BaseView {

    FloatingActionButton fab;
    ToolbarClickListener toolbarClickListener;
    ImageView imgvwPoster;
    Toolbar toolbar;
    CollapsingToolbarLayout toolbar_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        toolbar_layout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        imgvwPoster = (ImageView) findViewById(R.id.ximgvwPoster);
        fab.setOnClickListener(this);

        changeFragment(new MoviesListFragment(), FragmentName.MOVIES_LIST, true, true);

    }

    @Override
    public void changeFragment(Fragment currentFragment, FragmentName fragmentName, boolean addToBackStack, boolean removePrevious) {

        FragmentManager fm = getFragmentManager();
        if (removePrevious) {
            for (int entry = 0; entry < fm.getBackStackEntryCount(); entry++) {
                if (("" + fragmentName).equals(fm.getBackStackEntryAt(entry).getName())) {
                    fm.popBackStackImmediate("" + fragmentName, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    removeFragment(fragmentName);
                }
            }
        }

        try {
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            if (addToBackStack) {
                fragmentTransaction.addToBackStack("" + fragmentName);
            }
            fragmentTransaction.add(R.id.fragmentHolder, currentFragment, "" + fragmentName);
            fragmentTransaction.commit();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void removeFragment(FragmentName fragmentName) {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.remove(getFragmentByTag(fragmentName));
        fragmentTransaction.commit();
    }

    private Fragment getFragmentByTag(FragmentName fragmentName) {
        return getFragmentManager().findFragmentByTag("" + fragmentName);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
            updateUI();
        } else {

            super.onBackPressed();
        }

    }

    private void updateUI() {
        Fragment currentFragment = getCurrentTopFragment();

        if (currentFragment instanceof MoviesListFragment) {
            // Current Visible Fragment is Movies List Fragment
            MoviesListFragment moviesListFragment = (MoviesListFragment) currentFragment;
            moviesListFragment.showUI();


        } else if (currentFragment instanceof MovieDetailsFragment) {
            // Current Visible Fragment is Movie Details Fragment
            MovieDetailsFragment movieDetailsFragment = (MovieDetailsFragment) currentFragment;
            movieDetailsFragment.initObjects();

        }

    }

    /**
     * This function is used to get the current Top Fragment from the Back stack
     *
     * @return - The Fragment on the Top
     */
    private Fragment getCurrentTopFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        //Performs any previous pending operations in the queue for the fragments
        fragmentManager.executePendingTransactions();
        String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
        return fragmentManager.findFragmentByTag(fragmentTag);
    }

    @Override
    public void setTitle(String title) {

        try {
            toolbar_layout.setTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ImageView getPosterView() {
        return imgvwPoster;
    }

    @Override
    public void initialiseAppBar(boolean expand) {
        AppBarLayout appbar = (AppBarLayout) findViewById(R.id.app_bar);
        float heightDp = 0;
        if (expand) {
            heightDp = (float) (getResources().getDisplayMetrics().heightPixels / 1.5);
        } else {
            heightDp = getResources().getDisplayMetrics().heightPixels / 4;
        }
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
        lp.height = (int) heightDp;
    }

    @Override
    public void initialiseFilterFAB(boolean show) {

        if (show) {
            fab.setVisibility(View.VISIBLE);
        } else {
            fab.setVisibility(View.GONE);
        }

    }

    @Override
    public void setFilterClickListener(ToolbarClickListener toolbarClickListener) {
        this.toolbarClickListener = toolbarClickListener;
    }


    @Override
    public void onClick(View view) {

        if (view == fab) {
            if (toolbarClickListener != null) {
                toolbarClickListener.onFilterIconClick();
            }
        }
    }
}
