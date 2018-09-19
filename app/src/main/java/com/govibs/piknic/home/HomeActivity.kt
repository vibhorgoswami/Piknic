package com.govibs.piknic.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import com.govibs.core.data.DataManager
import com.govibs.core.data.model.GroupModel
import com.govibs.core.ui.home.HomeContract
import com.govibs.core.ui.home.HomePresenter
import com.govibs.piknic.R

import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), HomeContract.View {

    private var homePresenter: HomePresenter? = null

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        homePresenter = HomePresenter(DataManager.getInstance())
        homePresenter?.attachView(this)
        fabAddGroup.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onDestroy() {
        homePresenter?.detachView()
        homePresenter = null
        super.onDestroy()
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun showGroupList(groupModelList: ArrayList<GroupModel>) {

    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }

    override fun showEmpty() {

    }

    override fun showError(errorMessage: String?) {

    }

    override fun showMessageLayout(show: Boolean) {

    }
}
