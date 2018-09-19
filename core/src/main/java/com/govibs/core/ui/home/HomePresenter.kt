package com.govibs.core.ui.home

import android.content.Context
import com.govibs.core.data.DataManager
import com.govibs.core.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomePresenter(private val dataManager: DataManager): BasePresenter<HomeContract.View>(), HomeContract.Actions {

    override fun getGroupList(context: Context) {
        if (!isViewAttached) {
            return
        }
        view?.showProgress()
        addToCompositeDisposable(dataManager.getGroupList(context)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ groupModelList ->
                    if (isViewAttached) {
                        view?.hideProgress()
                        if (groupModelList.isEmpty()) {
                            view?.showEmpty()
                        } else {
                            view?.showGroupList(groupModelList)
                        }
                    }
                }, { throwable ->

                }))
    }
}