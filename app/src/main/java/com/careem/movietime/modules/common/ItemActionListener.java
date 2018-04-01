package com.careem.movietime.modules.common;

/**
 * This interface is used to implement the click listeners throughout the application.
 *
 * @author AjinkyaD
 * @version 1.0
 */
public interface ItemActionListener
{
	/**
	 * This function is used to handle the List Item Click event.
	 *
	 * @param currentObject
	 *            -The current List Clicked Object
	 * @param position-
	 *            The current Index
	 */
	void onItemClicked(Object currentObject, int position);

}
