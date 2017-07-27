package com.lordofprograms.notelin.cv

import android.text.Editable
import android.text.TextWatcher

/**
 * Created by Михаил on 08.07.2017.
 */
class TextWatcherHelper(val myEditText: MyEditText, string: String) : TextWatcher {

     val s: String = string

    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        myEditText.removeTextChangedListener(this)
        myEditText.setText(s)
        myEditText.addTextChangedListener(this)
    }
}