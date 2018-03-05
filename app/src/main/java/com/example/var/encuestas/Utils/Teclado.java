package com.example.var.encuestas.Utils;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by rexv666480 on 07/03/2017.
 */
public class Teclado {

    public static void hideSoftKeyboard(Activity activity) {

        try {
            if(activity!=null) {
                InputMethodManager inputMethodManager =
                        (InputMethodManager) activity.getSystemService(
                                Activity.INPUT_METHOD_SERVICE);
                View focusedView = activity.getCurrentFocus();
                if (focusedView != null) {
                    inputMethodManager.hideSoftInputFromWindow(
                            activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }catch (Exception ex)
        {
            throw  ex;
        }
    }

    public static  void setupUI(View view , final Activity activity) {

        // Set up touch listener for non-text box views to hide keyboard.
        try{
        if(view != null) {
            if (!(view instanceof EditText)) {
                view.setOnTouchListener(new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        hideSoftKeyboard(activity);
                        return false;
                    }
                });
            }

            //If a layout container, iterate over children and seed recursion.
            if (view instanceof ViewGroup) {
                for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                    View innerView = ((ViewGroup) view).getChildAt(i);
                    setupUI(innerView, activity);
                }
            }
        }
        }catch (Exception ex)
        {
            throw  ex;
        }
    }

}
