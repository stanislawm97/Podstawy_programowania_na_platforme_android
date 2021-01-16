package com.example.podstawyprogramowanianaplatformandroid.ui.note

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.podstawyprogramowanianaplatformandroid.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    private val items = ArrayList<NoteItem>()
    private val copyItems = ArrayList<NoteItem>()
    private var onDeleteCallback: ((title: String) -> Unit)? = null
    private var onListChangedCallback: ((List<NoteItem>) -> Unit)? = null
    private val partialItems = ArrayList<NoteItem>()

    fun submitList(noteItems: List<NoteItem>) {
        items.addAll(noteItems)
        copyItems.addAll(noteItems)
        onListChangedCallback?.invoke(items)
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        onDeleteCallback?.invoke(items[position].title)
        items.removeAt(position)
        copyItems.removeAt(position)
        onListChangedCallback?.invoke(items)
        notifyItemRemoved(position)
    }

    fun setOnListChangedCallback(action: (List<NoteItem>) -> Unit) {
        onListChangedCallback = action
    }

    fun setOnDeleteCallbackAction(action: (title: String) -> Unit) {
        onDeleteCallback = action
    }

    fun search(title: String) {
        partialItems.clear()
        copyItems.forEach {
            if(it.title.contains(title)) partialItems.add(it)
        }
        items.clear()
        items.addAll(partialItems)
        onListChangedCallback?.invoke(items)
        notifyDataSetChanged()
    }

    fun clearSearch() {
        items.clear()
        items.addAll(copyItems)
        onListChangedCallback?.invoke(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        with(holder) {
            tilNote.hint = item.title
            (tilNote.editText as TextInputEditText).apply {
                setText(item.value)
                keyListener = null
            }
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tilNote: TextInputLayout = itemView.findViewById(R.id.til_note)
    }
}

