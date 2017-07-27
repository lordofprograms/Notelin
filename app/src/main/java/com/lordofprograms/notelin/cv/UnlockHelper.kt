package com.lordofprograms.notelin.cv

import android.view.inputmethod.InputMethodManager
import com.lordofprograms.notelin.ui.activities.NoteActivity
import kotlinx.android.synthetic.main.activity_note.*

/**
 * Created by Михаил on 08.07.2017.
 */
class UnlockHelper(activity: NoteActivity) : Runnable {

    var noteActivity: NoteActivity = activity

    override fun run() {
        noteActivity.etText.requestFocusFromTouch()
        val imm = noteActivity.applicationContext.getSystemService("input_method") as InputMethodManager
        imm.showSoftInput(noteActivity.etText, 0)
    }

}