package com.govibs.piknic.group.adapter

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.govibs.piknic.group.steps.GroupStepAddContacts
import com.stepstone.stepper.Step
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter

class GroupStepAdapter(fragmentManager: FragmentManager, private val context: Context):
        AbstractFragmentStepAdapter(fragmentManager, context) {

    companion object {
        private const val STEP_ADD_CONTACTS = 0
        private const val STEP_ADD_GROUP_DETAILS = 1
        private const val STEP_PERMISSIONS = 2
    }

    override fun getCount(): Int = 1

    override fun createStep(position: Int): Step {
        when (position) {
            STEP_ADD_CONTACTS -> { return GroupStepAddContacts.createInstance() }
        }
        return GroupStepAddContacts.createInstance()
    }
}