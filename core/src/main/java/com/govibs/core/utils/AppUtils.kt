package com.govibs.core.utils

import android.content.Context
import com.govibs.core.R

object AppUtils {

    fun isTablet(context: Context): Boolean = context.resources.getBoolean(R.bool.tablet)

}