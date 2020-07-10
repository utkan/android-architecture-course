package com.techyourchance.mvc.screens.common.toastshelper

import android.content.Context
import android.widget.Toast
import com.techyourchance.mvc.R

class ToastsHelper(private val mContext: Context) {
    fun showUseCaseError() {
        Toast.makeText(mContext, R.string.error_network_call_failed, Toast.LENGTH_SHORT).show()
    }

}
