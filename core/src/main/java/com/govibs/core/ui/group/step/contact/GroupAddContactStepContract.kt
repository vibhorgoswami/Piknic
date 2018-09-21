package com.govibs.core.ui.group.step.contact

import android.content.Context
import com.govibs.core.data.model.ContactModel
import com.govibs.core.ui.base.RemoteView

interface GroupAddContactStepContract {

    interface Actions {
        fun getContactList(context: Context)
    }

    interface View: RemoteView {
        fun showContactList(contactModelList: ArrayList<ContactModel>)
    }

}