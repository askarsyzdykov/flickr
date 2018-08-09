package kz.flickr.app.presentation.utils

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v4.widget.CursorAdapter
import android.view.View
import android.view.inputmethod.InputMethodManager


/**
 * Created by Askar Syzdykov on 8/8/18.
 */

fun Fragment.hideKeyboard(view: View) {
    val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun CursorAdapter.getItemAsString(): String {
    return convertToString(cursor) as String
}