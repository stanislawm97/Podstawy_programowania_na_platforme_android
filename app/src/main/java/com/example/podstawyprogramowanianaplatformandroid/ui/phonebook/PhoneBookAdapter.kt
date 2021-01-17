package com.example.podstawyprogramowanianaplatformandroid.ui.phonebook

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.podstawyprogramowanianaplatformandroid.R
import com.example.podstawyprogramowanianaplatformandroid.database.entity.PhoneBookEntity

class PhoneBookAdapter : RecyclerView.Adapter<PhoneBookAdapter.PhoneBookViewHolder>() {
    class PhoneBookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvGender: TextView = itemView.findViewById(R.id.tv_gender)
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvNumber: TextView = itemView.findViewById(R.id.tv_number)
        val tvEmail: TextView = itemView.findViewById(R.id.tv_email)
        val llContact: LinearLayout = itemView.findViewById(R.id.ll_contact)
        val ivAvatar: ImageView = itemView.findViewById(R.id.iv_avatar)
        val btCall: ImageButton = itemView.findViewById(R.id.bt_call)
    }

    private val items = ArrayList<PhoneBookEntity>()
    private val partialItems = ArrayList<PhoneBookEntity>()
    private val copyItems = ArrayList<PhoneBookEntity>()
    private var onListChangedCallback: ((List<PhoneBookEntity>) -> Unit)? = null
    private var onItemLongClickListener: ((PhoneBookEntity) -> Unit)? = null
    private var onItemClickListener: ((PhoneBookEntity) -> Unit)? = null

    fun setOnListChangedCallback(action: (List<PhoneBookEntity>) -> Unit) {
        onListChangedCallback = action
    }

    fun setOnItemLongClickListener(action: ((PhoneBookEntity) -> Unit)) {
        onItemLongClickListener = action
    }

    fun setOnItemClickListener(action: ((PhoneBookEntity) -> Unit)) {
        onItemClickListener = action
    }

    fun submitItems(items: List<PhoneBookEntity>) {
        this.items.apply {
            clear()
            addAll(items)
        }
        copyItems.addAll(items)
        onListChangedCallback?.invoke(items)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneBookViewHolder =
        PhoneBookViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_phone_book, parent, false)
        )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PhoneBookViewHolder, position: Int) {
        val item = items[position]

        with(holder) {
            tvGender.text = item.gender
            tvName.text = "${item.firstName} ${item.lastName}"
            tvNumber.text = item.number
            tvEmail.text = item.email

            when (item.gender) {
                itemView.resources.getString(R.string.woman) -> {
                    ivAvatar.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            itemView.resources,
                            R.drawable.ic_woman,
                            null
                        )
                    )
                }
                itemView.resources.getString(R.string.man) -> {
                    ivAvatar.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            itemView.resources,
                            R.drawable.ic_man,
                            null
                        )
                    )
                }
                else -> {
                    ivAvatar.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            itemView.resources,
                            R.drawable.ic_bigender,
                            null
                        )
                    )
                }
            }

            llContact.setOnLongClickListener {
                onItemLongClickListener?.invoke(item)
                true
            }

            btCall.setOnClickListener {
                onItemClickListener?.invoke(item)
            }
        }
    }

    fun search(text: String) {
        partialItems.clear()
        copyItems.forEach {
            val validation =
                it.firstName.contains(text) || it.lastName.contains(text) || it.email?.contains(text) == true || it.number.contains(
                    text
                )
            if (validation) partialItems.add(it)
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

    fun sortAZ() {
        items.sortBy {
            it.firstName
        }
        notifyDataSetChanged()
    }

    fun sortZA() {
        items.sortByDescending {
            it.firstName
        }
        notifyDataSetChanged()
    }

}
