package com.lordofprograms.notelin.mvp.views

import com.arellomobile.mvp.MvpView
import com.lordofprograms.notelin.mvp.models.Note

interface NoteView : MvpView {

    fun showNote(note: Note)

    fun onNoteSaved()

    fun onNoteDeleted()

    fun showNoteInfoDialog(noteInfo: String)

    fun hideNoteInfoDialog()

    fun showNoteDeleteDialog()

    fun hideNoteDeleteDialog()

}
