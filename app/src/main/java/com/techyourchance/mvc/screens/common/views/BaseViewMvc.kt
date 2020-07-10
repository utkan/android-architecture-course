package com.techyourchance.mvc.screens.common.views

import android.content.Context
import android.view.View
import androidx.annotation.StringRes

abstract class BaseViewMvc : ViewMvc {
    private var mRootView: View? = null
    override val rootView: View?
        get() = mRootView

    protected fun setRootView(rootView: View?) {
        mRootView = rootView
    }

    protected fun <T : View?> findViewById(id: Int): T? {
        return rootView?.findViewById<T>(id)
    }

    protected val context: Context?
        protected get() = rootView?.context

    protected fun getString(@StringRes id: Int): String? {
        return context?.getString(id)
    }
}
