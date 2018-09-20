package com.govibs.piknic.group

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.govibs.piknic.R
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError

class GroupActivity: AppCompatActivity(), StepperLayout.StepperListener {

    companion object {

        fun createIntent(context: Context): Intent {
            return Intent(context, GroupActivity::class.java)
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)
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