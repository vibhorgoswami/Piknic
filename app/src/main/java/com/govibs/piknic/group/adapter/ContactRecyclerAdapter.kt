package com.govibs.piknic.group.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.govibs.core.data.model.ContactModel
import com.govibs.piknic.R
import kotlinx.android.synthetic.main.layout_contact_card.view.*

class ContactRecyclerAdapter(contactModelList: ArrayList<ContactModel?>,
                             private val mSelectContactListener:
                             (position: Int, contactModel: ContactModel) -> Unit):
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mContactList = contactModelList

    companion object {
        const val VIEW_TYPE_LIST = 1
        const val VIEW_TYPE_LOADING = 2
    }

    override fun getItemCount(): Int = mContactList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == VIEW_TYPE_LOADING) {
            return
        } else if (holder.itemViewType == VIEW_TYPE_LIST) {
            onBindGenericItemViewHolder(holder as ContactViewHolder, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return onGenericRecyclerViewHolder(parent, viewType)
    }

    override fun getItemViewType(position: Int): Int {
        if (mContactList[position] == null) {
            return VIEW_TYPE_LOADING
        } else {
            return VIEW_TYPE_LIST
        }
    }

    private fun onGenericRecyclerViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_LOADING) {
            return onIndicatorRecyclerViewHolder(parent)
        }
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.layout_contact_card, parent, false)
        return ContactViewHolder(view, mSelectContactListener)
    }

    private fun onIndicatorRecyclerViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.layout_content_progress, parent, false)
        return ProgressBarViewHolder(view)
    }

    private fun onBindGenericItemViewHolder(groupViewHolder: ContactViewHolder, position: Int) {
        groupViewHolder.bindGroup(mContactList[position])
    }

    fun isEmpty(): Boolean {
        return itemCount == 0
    }

    fun addLoadingView(): Boolean {
        if (getItemViewType(mContactList.size - 1) != VIEW_TYPE_LOADING) {
            add(null)
            return true
        }
        return false
    }

    fun removeLoadingView(): Boolean {
        if (mContactList.size > 1) {
            val loadingPosition = mContactList.size - 1
            if (getItemViewType(loadingPosition) == VIEW_TYPE_LOADING) {
                remove(loadingPosition)
                return true
            }
        }
        return false
    }


    private fun add(contactModel: ContactModel?) {
        add(null, contactModel)
    }

    private fun add(position: Int?, contactModel: ContactModel?) {
        if (null == position) {
            mContactList.add(contactModel)
            notifyItemInserted(mContactList.size - 1)
        } else {
            mContactList.add(position, contactModel)
            notifyItemInserted(position)
        }
    }

    fun addAll(contactList: ArrayList<ContactModel>) {
        mContactList = contactList.filterNotNullTo(mContactList)
        notifyDataSetChanged()
    }

    fun removeAll() {
        mContactList.clear()
        notifyDataSetChanged()
    }

    private fun remove(position: Int) {
        if (mContactList.size < position) {
            return
        }
        mContactList.removeAt(position)
        notifyItemRemoved(position)
    }



    class ContactViewHolder(view: View,
                            private val selectContactModel: (position: Int, contactModel: ContactModel) -> Unit) : RecyclerView.ViewHolder(view) {
        fun bindGroup(contactModel: ContactModel?) {
            with(contactModel) {
                if (null == contactModel) {
                    return
                }
                itemView.tvContactName.text = this!!.name
                itemView.tvContactPhoneNo.text = getPhoneNo()
                itemView.setOnClickListener { selectContactModel(adapterPosition, this) }
            }
        }
    }

    class ProgressBarViewHolder(view: View): RecyclerView.ViewHolder(view)
}