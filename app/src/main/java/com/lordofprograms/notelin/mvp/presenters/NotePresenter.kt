package com.lordofprograms.notelin.mvp.presenters

import android.app.Activity
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import org.greenrobot.eventbus.EventBus
import java.util.*
import javax.inject.Inject
import android.content.Intent
import com.lordofprograms.notelin.NotelinApplication
import com.lordofprograms.notelin.R
import com.lordofprograms.notelin.bus.NoteDeleteAction
import com.lordofprograms.notelin.bus.NoteEditAction
import com.lordofprograms.notelin.mvp.models.Note
import com.lordofprograms.notelin.mvp.models.NoteDao
import com.lordofprograms.notelin.mvp.views.NoteView

@InjectViewState
class NotePresenter : MvpPresenter<NoteView>() {

    @Inject
    lateinit var mNoteDao: NoteDao
    lateinit var mNote: Note
    var mNotePosition: Int = -1

    init {
        NotelinApplication.graph.inject(this)
    }

    fun showNote(noteId: Long, notePosition: Int) {
        mNotePosition = notePosition
        mNote = mNoteDao.getNoteById(noteId)
        viewState.showNote(mNote)
    }

    fun saveNote(title: String, text: String) {
        mNote.title = title
        if(title.trim().isEmpty()) mNote.title = "Заметка без названия"
        mNote.text = text
        mNote.changeDate = Date()
        mNoteDao.saveNote(mNote)
        EventBus.getDefault().post(NoteEditAction(mNotePosition))
        viewState.onNoteSaved()
    }

    fun shareNote(activity: Activity){
        val intent : Intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(android.content.Intent.EXTRA_TEXT, mNote.getShareNoteInfo())
        activity.startActivity(Intent.createChooser(intent,activity.getString(R.string.share_via)))
    }

    fun deleteNote() {
        mNoteDao.deleteNote(mNote)
        EventBus.getDefault().post(NoteDeleteAction(mNotePosition))
        viewState.onNoteDeleted()
    }

    fun showNoteDeleteDialog() {
        viewState.showNoteDeleteDialog()
    }

    fun hideNoteDeleteDialog() {
        viewState.hideNoteDeleteDialog()
    }

    fun showNoteInfoDialog() {
        viewState.showNoteInfoDialog(mNote.getInfo())
    }

    fun hideNoteInfoDialog() {
        viewState.hideNoteInfoDialog()
    }

}
