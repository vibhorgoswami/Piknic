package com.govibs.core.ui.home

import android.content.Context
import com.govibs.core.data.model.GroupModel
import com.govibs.core.ui.base.RemoteView

/**
 * Contract between Presenter and View
 */
interface HomeContract {

    interface Actions {
        fun getGroupList(context: Context)
    }

    interface View: RemoteView {
        fun showGroupList(groupModelList: ArrayList<GroupModel>)
    }

}