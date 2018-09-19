package com.govibs.core.ui.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Simple abstract class which allows binding of the view
 * and provides option for adding to disposables.
 */
abstract class BasePresenter<V> {

    private lateinit var compositeDisposable: CompositeDisposable

    protected var view: V? = null
    protected val TAG = "VIBS"

    /**
     * Check if the view is attached.
     * This checking is only necessary when returning from an asynchronous call
     */
    protected val isViewAttached: Boolean
        get() = view != null

    fun attachView(view: V) {
        initCompositeDisposable()
        this.view = view
    }

    fun detachView() {
        disposeCompositeDisposable()
        view = null
    }

    fun addToCompositeDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }


    private fun initCompositeDisposable() {
        compositeDisposable = CompositeDisposable()
    }

    private fun disposeCompositeDisposable() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

}