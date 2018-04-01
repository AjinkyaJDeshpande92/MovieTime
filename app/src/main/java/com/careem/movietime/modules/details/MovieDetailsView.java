package com.careem.movietime.modules.details;


import com.careem.movietime.entities.common.details.MovieDetailsResponse;
import com.careem.movietime.modules.common.CommonView;

import java.util.List;

/**
 * This interface is used to declare the Base Activity Functions to be used/implemented by the Activity.
 *
 * @author AjinkyaD
 * @version 1.0
 */
interface MovieDetailsView extends CommonView {

    void renderMovieGenre(String genre);

    void renderProductionCompanies(String production_companies);

    void renderDetails(MovieDetailsResponse movieDetailsResponse);

    void renderLanguages(String spoken_languages);
}
