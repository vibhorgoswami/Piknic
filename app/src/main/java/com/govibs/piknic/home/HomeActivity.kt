package com.govibs.piknic.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.govibs.core.data.DataManager
import com.govibs.core.data.model.GroupModel
import com.govibs.core.ui.home.HomeContract
import com.govibs.core.ui.home.HomePresenter
import com.govibs.core.ui.view.EndlessRecyclerViewOnScrollListener
import com.govibs.core.utils.AppUtils
import com.govibs.piknic.R
import com.govibs.piknic.home.adapter.HomeGroupRecyclerAdapter
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.layout_content_progress.*
import kotlinx.android.synthetic.main.layout_message.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

class HomeActivity : AppCompatActivity(), HomeContract.View, SwipeRefreshLayout.OnRefreshListener {

    private var homePresenter: HomePresenter? = null
    private var mGroupRecyclerAdapter: HomeGroupRecyclerAdapter? = null

    companion object {

        private val TAB_LAYOUT_SPAN_SIZE = 2
        private val TAB_LAYOUT_ITEM_SPAN_SIZE = 1
        private const val PERMISSION_REQUEST_CODE_READ_CONTACTS = 100

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
            startAddGroupActivity()
        }
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.colorWhite)
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent)
        mSwipeRefreshLayout.setOnRefreshListener(this)
        btnTryAgain.setOnClickListener(this::onClickTryAgainListener)
        rvHomeGroupList.layoutManager = setupLayoutManager(AppUtils.isTablet(this))
        rvHomeGroupList.addOnScrollListener(setupScrollListener(AppUtils.isTablet(this),
                rvHomeGroupList.layoutManager as RecyclerView.LayoutManager))
        mGroupRecyclerAdapter = HomeGroupRecyclerAdapter(ArrayList(1))
        rvHomeGroupList.adapter = mGroupRecyclerAdapter
        homePresenter?.getGroupList(applicationContext)
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
        if (!mSwipeRefreshLayout.isActivated) {
            mSwipeRefreshLayout.isEnabled = true
        }
        mGroupRecyclerAdapter!!.addAll(groupModelList)
    }

    override fun showProgress() {
        if (mGroupRecyclerAdapter!!.isEmpty() && !mSwipeRefreshLayout.isRefreshing) {
            contentProgress.visibility = View.VISIBLE
        }
    }

    override fun hideProgress() {
        mSwipeRefreshLayout.isRefreshing = false
        contentProgress.visibility = View.GONE
        mGroupRecyclerAdapter?.removeLoadingView()
    }

    override fun showEmpty() {
        mMessageImageView.setBackgroundResource(R.drawable.ic_group_generic_120dp)
        mTextMessage.setText(R.string.text_error_add_group)
        btnTryAgain.setText(R.string.text_error_add)
        showMessageLayout(true)
    }

    override fun showError(errorMessage: String?) {
        mTextMessage.text = errorMessage
        mMessageImageView.setBackgroundResource(R.drawable.ic_empty_generic_120dp)
        btnTryAgain.visibility = View.VISIBLE
        btnTryAgain.text = getString(R.string.text_error_try_again)
        showMessageLayout(true)
    }

    override fun showMessageLayout(show: Boolean) {
        if (show) {
            mMessageLayout.visibility = View.VISIBLE
            rvHomeGroupList.visibility = View.GONE
        } else {
            mMessageLayout.visibility = View.GONE
            rvHomeGroupList.visibility = View.VISIBLE
        }
    }

    override fun onRefresh() {
        homePresenter?.getGroupList(applicationContext)
    }

    private fun onClickTryAgainListener(view: View) {
        if (btnTryAgain.text == getString(R.string.text_error_try_again)) {
            homePresenter?.getGroupList(applicationContext)
        } else if (btnTryAgain.text == getString(R.string.text_error_add)) {
            startAddGroupActivity()
        }
    }

    private fun setupLayoutManager(tablet: Boolean): RecyclerView.LayoutManager {
        var layoutManager: RecyclerView.LayoutManager? = null
        if (tablet) {
            layoutManager = initGridLayoutManager(TAB_LAYOUT_SPAN_SIZE, TAB_LAYOUT_ITEM_SPAN_SIZE)
        } else {
            layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }
        return layoutManager
    }

    private fun initGridLayoutManager(spanCount: Int, itemSpanCount: Int): RecyclerView.LayoutManager {
        val gridLayoutManager = GridLayoutManager(this, spanCount)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (mGroupRecyclerAdapter!!.getItemViewType(position)) {
                    HomeGroupRecyclerAdapter.VIEW_TYPE_LOADING ->
                        // If it is a loading view we wish to accomplish a single item per row
                        spanCount
                    else ->
                        // Else, define the number of items per row (considering TAB_LAYOUT_SPAN_SIZE).
                        itemSpanCount
                }
            }
        }
        return gridLayoutManager
    }

    private fun setupScrollListener(tablet: Boolean, layoutManager: RecyclerView.LayoutManager): EndlessRecyclerViewOnScrollListener {
        return object : EndlessRecyclerViewOnScrollListener(if (tablet)
            layoutManager as GridLayoutManager
        else
            layoutManager as LinearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                rvHomeGroupList.post { mGroupRecyclerAdapter!!.addLoadingView() }
            }
        }
    }

    @AfterPermissionGranted(PERMISSION_REQUEST_CODE_READ_CONTACTS)
    fun startAddGroupActivity() {
        if (EasyPermissions.hasPermissions(this, android.Manifest.permission.READ_CONTACTS)) {

            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.text_permission_contact_rationale),
                    PERMISSION_REQUEST_CODE_READ_CONTACTS, android.Manifest.permission.READ_CONTACTS)
        }
    }
}
