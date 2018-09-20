package com.govibs.piknic.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.govibs.core.data.model.GroupModel
import com.govibs.piknic.R

class HomeGroupRecyclerAdapter(private val groupList: ArrayList<GroupModel?>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var groupArrayList = groupList

    companion object {
        private val TAG = "VIBS"
        val VIEW_TYPE_LIST = 1
        val VIEW_TYPE_LOADING = 2
    }

    override fun getItemCount(): Int = groupArrayList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == VIEW_TYPE_LOADING) {
            return
        } else if (holder.itemViewType == VIEW_TYPE_LIST) {
            onBindGenericItemViewHolder(holder as GroupViewHolder, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return onGenericRecyclerViewHolder(parent, viewType)
    }

    override fun getItemViewType(position: Int): Int {
        if (groupArrayList[position] == null) {
            return VIEW_TYPE_LOADING
        } else {
            return VIEW_TYPE_LIST
        }
    }

    fun onGenericRecyclerViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_LOADING) {
            return onIndicatorRecyclerViewHolder(parent)
        }
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.layout_item_group, parent, false)
        return GroupViewHolder(view)
    }

    fun onIndicatorRecyclerViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.layout_content_progress, parent, false)
        return ProgressBarViewHolder(view)
    }

    fun onBindGenericItemViewHolder(groupViewHolder: GroupViewHolder, position: Int) {
        groupViewHolder.bindGroup(groupArrayList[position])
    }

    fun isEmpty(): Boolean {
        return itemCount == 0
    }

    fun addLoadingView(): Boolean {
        if (getItemViewType(groupArrayList.size - 1) != VIEW_TYPE_LOADING) {
            add(null)
            return true
        }
        return false
    }

    fun removeLoadingView(): Boolean {
        if (groupArrayList.size > 1) {
            val loadingPosition = groupArrayList.size - 1
            if (getItemViewType(loadingPosition) == VIEW_TYPE_LOADING) {
                remove(loadingPosition)
                return true
            }
        }
        return false
    }


    private fun add(group: GroupModel?) {
        add(null, group)
    }

    private fun add(position: Int?, group: GroupModel?) {
        if (null == position) {
            groupArrayList.add(group)
            notifyItemInserted(groupArrayList.size - 1)
        } else {
            groupArrayList.add(position, group)
            notifyItemInserted(position)
        }
    }

    fun addAll(groupList: ArrayList<GroupModel>) {
        groupArrayList = groupList.toCollection(groupArrayList)
        notifyDataSetChanged()
    }

    fun removeAll() {
        groupArrayList.clear()
        notifyDataSetChanged()
    }

    private fun remove(position: Int) {
        if (groupArrayList.size < position) {
            Log.w(TAG, "The item at position: $position doesn't exist")
            return
        }
        groupArrayList.removeAt(position)
        notifyItemRemoved(position)
    }

    class ProgressBarViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bindProgressBar(view: View) {
            with(view) {
                if (this.visibility == View.GONE) {
                    this.visibility = View.VISIBLE
                } else {
                    this.visibility = View.GONE
                }
            }
        }
    }

    class GroupViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bindGroup(groupModel: GroupModel?) {
            with(groupModel) {
                this?.let { group ->

                }
            }
        }
    }
}