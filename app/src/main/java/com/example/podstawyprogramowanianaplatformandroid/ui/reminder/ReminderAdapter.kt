package com.example.podstawyprogramowanianaplatformandroid.ui.reminder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.podstawyprogramowanianaplatformandroid.R

class ReminderAdapter : RecyclerView.Adapter<ReminderAdapter.ViewHolder>() {
    private val list = ArrayList<ReminderEntity>()
    private var deleteAction: ((ReminderEntity) -> Unit)? = null

    fun submitList(items: List<ReminderEntity>) {
        list.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    fun setDeleteAction(deleteAction: (ReminderEntity) -> Unit) {
        this.deleteAction = deleteAction
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.reminder_item, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        with(holder) {
            tvTitle.text = item.title
            tvText.text = item.text
            tvDate.text = item.time

            btnDelete.setOnClickListener {
                deleteAction?.invoke(item)
                list.removeAt(position)
                notifyItemRemoved(position)
            }

            btnEdit.setOnClickListener {

            }
        }
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvText: TextView = itemView.findViewById(R.id.tv_text)
        val tvDate: TextView = itemView.findViewById(R.id.tv_date)
        val btnDelete: Button = itemView.findViewById(R.id.btn_delete)
        val btnEdit: Button = itemView.findViewById(R.id.btn_edit)
    }
}