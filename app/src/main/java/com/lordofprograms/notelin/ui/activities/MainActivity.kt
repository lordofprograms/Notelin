package com.lordofprograms.notelin.ui.activities

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.afollestad.materialdialogs.MaterialDialog
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder
import com.lordofprograms.notelin.R
import com.lordofprograms.notelin.mvp.models.Note
import com.lordofprograms.notelin.mvp.presenters.MainPresenter
import com.lordofprograms.notelin.mvp.views.MainView
import com.lordofprograms.notelin.ui.adapters.NotesAdapter
import com.lordofprograms.notelin.ui.commons.ItemClickSupport
import com.lordofprograms.notelin.utils.confirmDeleting
import com.lordofprograms.notelin.utils.snackBar
import com.nshmura.snappysmoothscroller.SnapType
import com.nshmura.snappysmoothscroller.SnappyLinearLayoutManager
import com.pawegio.kandroid.onQueryChange
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MvpAppCompatActivity(), MainView {

    @InjectPresenter
    lateinit var mPresenter: MainPresenter
    private var mNoteContextDialog: MaterialDialog? = null
    private var mNoteDeleteDialog: MaterialDialog? = null
    private var mNoteInfoDialog: MaterialDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(ItemClickSupport.addTo(rvNotesList)) {
            setOnItemClickListener { _, position, _ -> mPresenter.openNote(this@MainActivity, position) }
            setOnItemLongClickListener { _, position, _ -> mPresenter.showNoteContextDialog(position); true }
        }

        supportActionBar?.setTitle(R.string.all_notes)
        fabButton.setOnClickListener { mPresenter.openNewNote(this) }

    }

    override fun onNotesLoaded(notes: List<Note>) {
        rvNotesList.adapter = NotesAdapter(notes)
        rvNotesList.isNestedScrollingEnabled = false
        rvNotesList.layoutManager = setLayoutManger(this)
        updateView()
    }

    override fun updateView() {
        rvNotesList.adapter.notifyDataSetChanged()
        if (rvNotesList.adapter.itemCount == 0) {
            rvNotesList.visibility = View.GONE
            tvNotesIsEmpty.visibility = View.VISIBLE
        } else {
            rvNotesList.visibility = View.VISIBLE
            tvNotesIsEmpty.visibility = View.GONE
        }
    }

    override fun onNoteDeleted() {
        updateView()
        snackBar(notesList, getString(R.string.note_is_deleted))
    }

    override fun onAllNotesDeleted() {
        updateView()
        snackBar(notesList, getString(R.string.all_notes_is_deleted))
    }

    override fun onSearchResult(notes: List<Note>) {
        rvNotesList.adapter = NotesAdapter(notes)
    }

    override fun showNoteContextDialog(notePosition: Int) {

        BottomSheetBuilder(this,R.style.AppTheme_BottomSheetDialog)
                .expandOnStart(true)
                .setMode(BottomSheetBuilder.MODE_LIST)
                .setMenu(R.menu.bottom_sheet)
                .setItemClickListener { item ->
                    when(item.itemId){
                        R.id.bs_open -> mPresenter.openNote(this, notePosition)
                        R.id.bs_info -> mPresenter.showNoteInfo(notePosition)
                        R.id.bs_delete -> confirmDeleting(this@MainActivity, R.string.deleting_note, R.string.confirm_deleting_note,
                                { mPresenter.deleteNoteByPosition(notePosition)})
                    }
                }
                .createDialog()
                .show()
    }

    override fun hideNoteContextDialog() {
        mNoteContextDialog?.dismiss()
    }

    override fun showNoteDeleteDialog(notePosition: Int) {
        mNoteDeleteDialog = MaterialDialog.Builder(this)
                .title(R.string.deleting_note)
                .content(R.string.confirm_deleting_note)
                .positiveText(R.string.yes)
                .negativeText(R.string.no)
                .onPositive { _, _ ->
                    mPresenter.deleteNoteByPosition(notePosition)
                    mNoteInfoDialog?.dismiss()
                }
                .onNegative { _, _ -> mPresenter.hideNoteDeleteDialog() }
                .cancelListener { mPresenter.hideNoteDeleteDialog() }
                .show()
    }

    override fun hideNoteDeleteDialog() {
        mNoteDeleteDialog?.dismiss()
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

    private fun setLayoutManger(context: Context) : LinearLayoutManager{
        val llm = SnappyLinearLayoutManager(context)
        llm.setSnapType(SnapType.CENTER)
        llm.setSnapInterpolator(DecelerateInterpolator())
        return llm
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        initSearchView(menu)
        return true
    }

    private fun initSearchView(menu: Menu) {
        val searchViewMenuItem = menu.findItem(R.id.action_search)
        val searchView = searchViewMenuItem.actionView as SearchView
        searchView.onQueryChange { query -> mPresenter.search(query) }
        searchView.setOnCloseListener { mPresenter.search(""); false }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuDeleteAllNotes -> confirmDeleting(this@MainActivity, R.string.delete_all,
                    R.string.confirm_deleting_all_notes, {mPresenter.deleteAllNotes()})
            R.id.menuSortByName -> mPresenter.sortNotesBy(MainPresenter.SortNotesBy.NAME)
            R.id.menuSortByDate -> mPresenter.sortNotesBy(MainPresenter.SortNotesBy.DATE)
        }
        return super.onOptionsItemSelected(item)
    }

}
