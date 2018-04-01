package com.careem.movietime.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.careem.movietime.webservicemanager.WebURL;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * This class is used to perform commonly used functions throughout the application. Functions like - 1)Show/Dismiss Loader 2)Show/Dismiss Snackbar
 *
 * @author AjinkyaD
 * @version 1.0
 */
public class Utilities {

    private static ProgressDialog pgLoader;

    private Context mContext;
    private Activity mActivity;
    private static AlertDialog.Builder alertDialogBuilder;
    private static AlertDialog alertDialog;

    /**
     * This is a parameterized constructor used to initialize Utilities.
     *
     * @param callingActivity -The caller Screen
     */
    public Utilities(Activity callingActivity) {
        mActivity = callingActivity;
        mContext = callingActivity;
    }

    /**
     * This is a parameterised constructor used to initialize Utilities.
     *
     * @param context -The caller Screen
     */
    public Utilities(Context context) {
        mContext = context;
    }

    /**
     * This function shows a Progress (Loading) dialog to User.
     *
     * @param strMessage -The Message needed to be displayed to the User on the Loading Dialog.
     */
    public static void showLoader(String strMessage, Context currentContext) {
        try {
            dismissLoader();
            pgLoader = new ProgressDialog(currentContext);
            pgLoader.setMessage(strMessage);
            pgLoader.setCancelable(false);
            pgLoader.setIndeterminate(true);

            if (pgLoader != null && !pgLoader.isShowing()) {
                pgLoader.show();
            } else {
                // It is already Showing
                pgLoader.setMessage(strMessage);
            }
        } catch (Exception e) {
            if (pgLoader.isShowing())
                pgLoader.dismiss();
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * This function dismiss the current loading dialog present on the screen
     */
    public static void dismissLoader() {
        try {
            if (pgLoader != null && pgLoader.isShowing())
                pgLoader.dismiss();
            pgLoader = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * This function displays a toast message to user.
     *
     * @param strMessage - Toast Message Content to be Displayed
     */
    public void showToast(String strMessage) {
        Toast toast = Toast.makeText(mContext, strMessage, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }


    /**
     * This function is used to construct the suffix part of the URL in the Volley Request.
     *
     * @param url       -      The URL with/without Params
     * @param getParams - The Hashmap list of all the Params
     * @return - The Well constructed get request params
     */
    public String getParamsConstructedURL(String url, HashMap<String, String> getParams) {

        Uri.Builder builder = Uri.parse(url).buildUpon();

        for (Map.Entry<String, String> entry : getParams.entrySet()) {
            builder.appendQueryParameter(entry.getKey(), entry.getValue());
        }

        return builder.toString();
    }

    /**
     * This function is used to construct the suffix part of the URL in the Volley Request.
     *
     * @param url       -      The URL with/without Params
     * @param getParams - The Hashmap list of all the Params
     * @return - The Well constructed get request params
     */
    public String getArrayParamsConstructedURL(String url, HashMap<String, String[]> getParams) {

        Uri.Builder builder = Uri.parse(url).buildUpon();

        for (Map.Entry<String, String[]> entry : getParams.entrySet()) {
            for (String value : entry.getValue()) {
                builder.appendQueryParameter(entry.getKey(), value);
            }
        }

        return builder.toString();
    }

    /**
     * This function is used to construct the suffix part of the URL in the Volley Request.
     *
     * @param getParams - The Hashmap list of all the Params
     * @return - The Well constructed get request params
     */
    public String getURLParamsConstructed(HashMap<String, String> getParams) {

        Uri.Builder builder = Uri.parse("").buildUpon();

        for (Map.Entry<String, String> entry : getParams.entrySet()) {
            builder.appendQueryParameter(entry.getKey(), entry.getValue());
        }

        return builder.toString();
    }


    public static String getPosterPath(String posterPath, boolean fetchLowResolution) {

        if (fetchLowResolution) {
            return WebURL.image_base_url_low_res + posterPath;
        } else {
            return WebURL.image_base_url_high_res + posterPath;
        }
    }

    /**
     * This function is used to format the given double number into the requested format.
     * <p>
     * If now format is supplied it will format the number in default format.
     *
     * @param value           - the Value to be formatted
     * @param requestedFormat - The format requested
     * @return - The formatted value
     */
    public static String formatDecimalNumber(double value, String requestedFormat) {

        if (requestedFormat == null)
            return new DecimalFormat("#.#").format(value);
        else
            return new DecimalFormat(requestedFormat).format(value);
    }

    /**
     * This function is used to convert the Calendar date to output Format.
     *
     * @param outputFormat - The desired ouput format of the date
     * @param inputDate    - The Date in Calendar Object
     * @return - The formatted date
     */
    public static String formattedDateFromDate(Date inputDate, String outputFormat) {

        String outputDate = "";

        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, Locale.getDefault());

        try {
            outputDate = df_output.format(inputDate);
        } catch (Exception e) {
            Log.e("formattedDateFromCal", "Exception in formattedDateFromCalendar(): " + e.getMessage());
        }
        return outputDate;

    }
}