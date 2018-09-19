package com.govibs.core.ui.base

/**
 * Simple interface to allow common view methods
 * such as show/hide progress, show/hide empty view
 * are available.
 */
interface RemoteView {

    fun showProgress()

    fun hideProgress()

    fun showEmpty()

    fun showError(errorMessage: String?)

    fun showMessageLayout(show: Boolean)

}