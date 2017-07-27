@file:JvmName("SbUtils")
package com.lordofprograms.notelin.utils

import android.support.design.widget.Snackbar
import android.view.View

/**
 * Created by Михаил on 30.05.2017.
 */
fun snackBar(view: View, msg: CharSequence, duration: Int = Snackbar.LENGTH_LONG){
    Snackbar.make(view , msg , duration).show();
}
