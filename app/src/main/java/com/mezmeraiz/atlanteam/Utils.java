package com.mezmeraiz.atlanteam;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * Created by max on 31.10.17.
 */

public class Utils {

    public static void snackBar(Activity activity, String message){
        Snackbar.make(activity.getWindow().getDecorView().getRootView(),
                message,
                Snackbar.LENGTH_SHORT).show();
    }

    public static void hideKeyboard(Context context, View v){
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

}
