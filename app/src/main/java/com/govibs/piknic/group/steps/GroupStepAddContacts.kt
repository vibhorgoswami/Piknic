package com.govibs.piknic.group.steps

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.govibs.core.ui.view.EndlessRecyclerViewOnScrollListener
import com.govibs.core.utils.AppUtils
import com.govibs.piknic.R
import com.govibs.piknic.group.adapter.ContactRecyclerAdapter
import com.pchmn.materialchips.ChipsInput
import com.pchmn.materialchips.model.ChipInterface
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError
import kotlinx.android.synthetic.main.fragment_group_step_contacts.*

class GroupStepAddContacts: Fragment(), Step {

    private var mContactRecyclerAdapter: ContactRecyclerAdapter? = null

    companion object {
        private const val TAB_LAYOUT_SPAN_SIZE = 2
        private const val TAB_LAYOUT_ITEM_SPAN_SIZE = 1

        fun createInstance(): GroupStepAddContacts = GroupStepAddContacts()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_group_step_contacts, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tablet = AppUtils.isTablet(activity!!)
        rvContactList.layoutManager = setupLayoutManager(tablet)
        rvContactList.addOnScrollListener(setupScrollListener(tablet, (rvContactList as RecyclerView).layoutManager!!))
        rvContactList.adapter = mContactRecyclerAdapter
        mChipInput.addChipsListener(object : ChipsInput.ChipsListener {
            override fun onChipAdded(chip: ChipInterface, newSize: Int) {
                Log.v("VIBS", "Chip added ${chip.id}, size $newSize")
            }

            override fun onChipRemoved(chip: ChipInterface, newSize: Int) {
                Log.v("VIBS", "Chip removed ${chip.id}, size $newSize")
            }

            override fun onTextChanged(p0: CharSequence?) {

            }
        })
    }

    override fun onSelected() {

    }

    override fun verifyStep(): VerificationError? {
        val contactsSelected = mChipInput.filterableList?.isEmpty() ?: true
        return if (!contactsSelected) {
            null
        } else VerificationError(getString(R.string.text_error_add_group))
    }

    override fun onError(error: VerificationError) {

    }

    private fun setupLayoutManager(tablet: Boolean): RecyclerView.LayoutManager {
        return if (tablet) {
            initGridLayoutManager(TAB_LAYOUT_SPAN_SIZE, TAB_LAYOUT_ITEM_SPAN_SIZE)
        } else {
            LinearLayoutManager(activity!!, RecyclerView.VERTICAL, false)
        }
    }

    private fun initGridLayoutManager(spanCount: Int, itemSpanCount: Int): RecyclerView.LayoutManager {
        val gridLayoutManager = GridLayoutManager(activity!!, spanCount)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (mContactRecyclerAdapter!!.getItemViewType(position)) {
                    ContactRecyclerAdapter.VIEW_TYPE_LOADING ->
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
                rvContactList.post { mContactRecyclerAdapter!!.addLoadingView() }
            }
        }
    }
}