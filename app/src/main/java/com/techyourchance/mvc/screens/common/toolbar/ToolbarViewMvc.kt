package com.techyourchance.mvc.screens.common.toolbar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.techyourchance.mvc.R
import com.techyourchance.mvc.screens.common.views.BaseViewMvc

class ToolbarViewMvc(inflater: LayoutInflater, parent: ViewGroup?) : BaseViewMvc() {
    interface NavigateUpClickListener {
        fun onNavigateUpClicked()
    }

    interface HamburgerClickListener {
        fun onHamburgerClicked()
    }

    interface LocationRequestListener {
        fun onLocationRequestClicked()
    }

    private val mTxtTitle: TextView
    private val mBtnBack: ImageButton
    private val mBtnHamburger: ImageButton
    private val mBtnLocationRequest: ImageButton
    private var mNavigateUpClickListener: NavigateUpClickListener? = null
    private var mHamburgerClickListener: HamburgerClickListener? = null
    private var mLocationRequestListener: LocationRequestListener? = null
    fun setTitle(title: String?) {
        mTxtTitle.text = title
    }

    fun enableHamburgerButtonAndListen(hamburgerClickListener: HamburgerClickListener?) {
        if (mNavigateUpClickListener != null) {
            throw RuntimeException("hamburger and up shouldn't be shown together")
        }
        mHamburgerClickListener = hamburgerClickListener
        mBtnHamburger.visibility = View.VISIBLE
    }

    fun enableUpButtonAndListen(navigateUpClickListener: NavigateUpClickListener?) {
        if (mHamburgerClickListener != null) {
            throw RuntimeException("hamburger and up shouldn't be shown together")
        }
        mNavigateUpClickListener = navigateUpClickListener
        mBtnBack.visibility = View.VISIBLE
    }

    fun enableLocationRequestButtonAndListen(locationRequestListener: LocationRequestListener?) {
        mLocationRequestListener = locationRequestListener
        mBtnLocationRequest.visibility = View.VISIBLE
    }

    init {
        setRootView(inflater.inflate(R.layout.layout_toolbar, parent, false))
        mTxtTitle = findViewById(R.id.txt_toolbar_title)!!
        mBtnHamburger = findViewById(R.id.btn_hamburger)!!
        mBtnHamburger.setOnClickListener { mHamburgerClickListener!!.onHamburgerClicked() }
        mBtnBack = findViewById(R.id.btn_back)!!
        mBtnBack.setOnClickListener { mNavigateUpClickListener!!.onNavigateUpClicked() }
        mBtnLocationRequest = findViewById(R.id.btn_location)!!
        mBtnLocationRequest.setOnClickListener { mLocationRequestListener!!.onLocationRequestClicked() }
    }
}
