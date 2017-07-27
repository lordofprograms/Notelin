package com.lordofprograms.notelin.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lordofprograms.notelin.R
import com.lordofprograms.notelin.mvp.models.Note
import com.lordofprograms.notelin.utils.formatDate

class NotesAdapter(notesList: List<Note>) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private var mNotesList: List<Note> = notesList

    /**
     * Создание новых View и ViewHolder элемента списка, которые впоследствии могут переиспользоваться
     */
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): NotesAdapter.ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.note_item_layout, viewGroup, false)
        return NotesAdapter.ViewHolder(v);
    }

    /**
     * Заполнение виджетов View данными из элемента списка с номером i
     */
    override
    fun onBindViewHolder(viewHolder: NotesAdapter.ViewHolder, i: Int) {
        val note = mNotesList[i]
        viewHolder.mNoteTitle.text = note.title;
        viewHolder.mNoteDate.text = formatDate(note.changeDate)
    }

    /**
     * Возвращает количество элементов
     */
    override fun getItemCount(): Int {
        return mNotesList.size
    }

    /**
     * Реализация класса ViewHolder, хранящего ссылки на виджеты.
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val mNoteTitle: TextView = itemView.findViewById(R.id.tvItemNoteTitle) as TextView
        val mNoteDate: TextView = itemView.findViewById(R.id.tvItemNoteDate) as TextView

    }

}
