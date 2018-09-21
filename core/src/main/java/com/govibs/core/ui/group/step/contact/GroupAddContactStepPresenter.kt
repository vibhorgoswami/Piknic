package com.govibs.core.ui.group.step.contact

import android.content.Context
import com.govibs.core.data.DataManager
import com.govibs.core.ui.base.BasePresenter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GroupAddContactStepPresenter(private val dataManager: DataManager):
        BasePresenter<GroupAddContactStepContract.View>(), GroupAddContactStepContract.Actions {

    override fun getContactList(context: Context) {
        if (!isViewAttached) {
            return
        }
        addToCompositeDisposable(Single.fromCallable { dataManager.getContactList(context) }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ contactList ->
                    if (isViewAttached) {
                        view?.showContactList(contactList)
                    }
                }, { throwable ->
                    throwable.printStackTrace()
                }))
    }
}