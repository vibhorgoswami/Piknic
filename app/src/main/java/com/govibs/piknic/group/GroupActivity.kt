package com.govibs.piknic.group

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.govibs.piknic.R
import com.govibs.piknic.group.adapter.GroupStepAdapter
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import kotlinx.android.synthetic.main.activity_group.*

class GroupActivity: AppCompatActivity(), StepperLayout.StepperListener {

    companion object {

        private const val BUNDLE_KEY_STEP_POSITION = "BUNDLE_KEY_STEP_POSITION"

        fun createIntent(context: Context): Intent {
            return Intent(context, GroupActivity::class.java)
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)
        if (null == savedInstanceState) {
            mStepperLayout.adapter = GroupStepAdapter(supportFragmentManager, this)
        } else {
            mStepperLayout.currentStepPosition = savedInstanceState.getInt(BUNDLE_KEY_STEP_POSITION, 0)
        }
        mStepperLayout.setListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(BUNDLE_KEY_STEP_POSITION, mStepperLayout.currentStepPosition)
        super.onSaveInstanceState(outState)
    }

    override fun onStepSelected(newStepPosition: Int) {

    }

    override fun onError(verificationError: VerificationError?) {

    }

    override fun onReturn() {

    }

    override fun onCompleted(completeButton: View?) {

    }
}