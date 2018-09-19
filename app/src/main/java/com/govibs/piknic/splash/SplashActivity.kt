package com.govibs.piknic.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.govibs.piknic.R
import com.govibs.piknic.home.HomeActivity

class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            startHomeActivity()
        }, 1500)
    }

    private fun startHomeActivity() {
        startActivity(HomeActivity.createIntent(applicationContext))
        this.supportFinishAfterTransition()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

}