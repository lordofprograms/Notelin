@file: JvmName("UiUtils")
package com.lordofprograms.notelin.utils

import android.app.Activity
import android.content.Context
import android.widget.EditText
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.lordofprograms.notelin.R

/**
 * Created by Михаил on 06.07.2017.
 */

 fun confirmDeleting(activity: Activity, titleRes: Int, contentRes: Int, func: () -> Unit){
    MaterialDialog.Builder(activity)
            .title(titleRes)
            .content(contentRes)
            .positiveText(R.string.yes)
            .negativeText(R.string.no)
            .onPositive { _, _ -> func()}
            .onNegative { dialog, _ -> dialog.dismiss() }
            .show()
}

    fun toast(context: Context, string: String){
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }

 fun checkNote(activity: Activity, title: EditText, content: EditText, saveFunc: () -> Unit, deleteFunc: () -> Unit){
     if(content.text.toString().trim().isNotEmpty() || title.text.toString().isNotEmpty() ){
         saveFunc()
     }
     else{
         deleteFunc()
         toast(activity, activity.getString(R.string.empty_note))
     }
 }
