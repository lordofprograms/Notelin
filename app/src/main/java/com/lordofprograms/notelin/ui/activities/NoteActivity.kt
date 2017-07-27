package com.lordofprograms.notelin.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import com.afollestad.materialdialogs.MaterialDialog
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.lordofprograms.notelin.R
import com.lordofprograms.notelin.mvp.models.Note
import com.lordofprograms.notelin.mvp.presenters.NotePresenter
import com.lordofprograms.notelin.mvp.views.NoteView
import com.lordofprograms.notelin.utils.checkNote
import com.lordofprograms.notelin.utils.formatDate
import com.lordofprograms.notelin.utils.snackBar
import com.lordofprograms.notelin.utils.toast
import kotlinx.android.synthetic.main.activity_note.*

class NoteActivity : MvpAppCompatActivity(), NoteView {

    @InjectPresenter
    lateinit var mPresenter: NotePresenter
    private var mNoteDeleteDialog: MaterialDialog? = null
    private var mNoteInfoDialog: MaterialDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        supportActionBar?.setTitle(R.string.edit_note)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //перемещаем курсор в конец поля ввода
        etTitle.onFocusChangeListener = View.OnFocusChangeListener{ view, hasFocus ->
            if (hasFocus) {
                val editText = view as EditText
                editText.setSelection((editText.text.length))
            }
        }


        val noteId = intent.extras.getLong("note_id", -1)
        val notePosition = intent.extras.getInt("note_position", -1)
        mPresenter.showNote(noteId, notePosition)

        fabSave.setOnClickListener {
            mPresenter.saveNote(etTitle.text.toString(), etText.text.toString())
        }
    }

    override fun showNote(note: Note) {
        tvNoteDate.text = formatDate(note.changeDate)
        etTitle.setText(note.title)
        etText.setText(note.text)
    }

    override fun showNoteInfoDialog(noteInfo: String) {
        mNoteInfoDialog = MaterialDialog.Builder(this)
                .title(R.string.about_note)
                .positiveText(R.string.ok)
                .content(noteInfo)
                .onPositive { _, _ -> mPresenter.hideNoteInfoDialog() }
                .cancelListener { mPresenter.hideNoteInfoDialog() }
                .show()
    }

    override fun hideNoteInfoDialog() {
        mNoteInfoDialog?.dismiss()
    }

    override fun showNoteDeleteDialog() {
        mNoteDeleteDialog = MaterialDialog.Builder(this)
                .title(R.string.deleting_note)
                .content(R.string.deleting_note)
                .positiveText(R.string.yes)
                .negativeText(R.string.no)
                .onPositive { _, _ ->
                    mPresenter.hideNoteDeleteDialog()
                    mPresenter.deleteNote()
                   toast(this, getString(R.string.deleted))
                }
                .onNegative { _, _ -> mPresenter.hideNoteDeleteDialog() }
                .cancelListener { mPresenter.hideNoteDeleteDialog() }
                .show()
    }


    override fun hideNoteDeleteDialog() {
        mNoteDeleteDialog?.dismiss()
    }

    override fun onNoteSaved() {
        snackBar(noteLayout, getString(R.string.saved))
    }

    override fun onNoteDeleted() {
        snackBar(noteLayout, getString(R.string.note_is_deleted))
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
                checkNote(this, etTitle, etText,
                        {mPresenter.saveNote(etTitle.text.toString(), etText.text.toString())}, {mPresenter.deleteNote()})
            }

            R.id.menuShareNote -> mPresenter.shareNote(this)

            R.id.menuNoteInfo -> mPresenter.showNoteInfoDialog()

            R.id.menuDeleteNote -> mPresenter.showNoteDeleteDialog()
        }
        return super.onOptionsItemSelected(item)
    }



    override fun onBackPressed() {
        super.onBackPressed()
        checkNote(this, etTitle, etText,
                {mPresenter.saveNote(etTitle.text.toString(), etText.text.toString())}, {mPresenter.deleteNote()})
    }

    /*    fun makeEditable(editText: MyEditText, fab: FloatingActionButton){
        flag = true
        editText.setReadOnly(false)
        editText.requestFocus()
        editText.post(UnlockHelper(this))
        val obj = etText.text.toString()
        if(p == "begin"){
            editText.isFocusableInTouchMode = true
            editText.extendSelection(0)
            editText.setSelection(0)
        }
        else if(p == "end"){
            editText.setSelection(obj.length, obj.length)
        }
        fab.setImageResource(R.drawable.ic_done)
    }*/

}