package com.techyourchance.mvc.screens.common.main

import android.os.Bundle
import android.widget.FrameLayout
import com.techyourchance.mvc.common.permissions.PermissionsHelper
import com.techyourchance.mvc.screens.common.controllers.BackPressDispatcher
import com.techyourchance.mvc.screens.common.controllers.BackPressedListener
import com.techyourchance.mvc.screens.common.controllers.BaseActivity
import com.techyourchance.mvc.screens.common.fragmentframehelper.FragmentFrameWrapper
import com.techyourchance.mvc.screens.common.navdrawer.NavDrawerHelper
import com.techyourchance.mvc.screens.common.navdrawer.NavDrawerViewMvc
import com.techyourchance.mvc.screens.common.screensnavigator.ScreensNavigator
import java.util.*

class MainActivity : BaseActivity(), BackPressDispatcher, FragmentFrameWrapper, NavDrawerViewMvc.Listener, NavDrawerHelper {
    private val mBackPressedListeners: MutableSet<BackPressedListener?> = HashSet()
    private var mScreensNavigator: ScreensNavigator? = null
    private var mPermissionsHelper: PermissionsHelper? = null
    private var mViewMvc: NavDrawerViewMvc? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mScreensNavigator = compositionRoot.screensNavigator
        mPermissionsHelper = compositionRoot.permissionsHelper
        mViewMvc = compositionRoot.viewMvcFactory.getNavDrawerViewMvc(null)
        mViewMvc?.let {
        setContentView(it.rootView)
        }
        if (savedInstanceState == null) {
            mScreensNavigator!!.toQuestionsList()
        }
    }

    override fun onStart() {
        super.onStart()
        mViewMvc!!.registerListener(this)
    }

    override fun onStop() {
        super.onStop()
        mViewMvc!!.unregisterListener(this)
    }

    override fun onQuestionsListClicked() {
        mScreensNavigator!!.toQuestionsList()
    }

    override fun registerListener(listener: BackPressedListener?) {
        mBackPressedListeners.add(listener)
    }

    override fun unregisterListener(listener: BackPressedListener?) {
        mBackPressedListeners.remove(listener)
    }

    override fun onBackPressed() {
        var isBackPressConsumedByAnyListener = false
        for (listener in mBackPressedListeners) {
            if (listener!!.onBackPressed()) {
                isBackPressConsumedByAnyListener = true
            }
        }
        if (isBackPressConsumedByAnyListener) {
            return
        }
        if (mViewMvc!!.isDrawerOpen) {
            mViewMvc!!.closeDrawer()
        } else {
            super.onBackPressed()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        mPermissionsHelper!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override val fragmentFrame: FrameLayout
        get() = mViewMvc!!.fragmentFrame!!

    override fun openDrawer() {
        mViewMvc!!.openDrawer()
    }

    override fun closeDrawer() {
        mViewMvc!!.closeDrawer()
    }

    override fun isDrawerOpen(): Boolean {
        return mViewMvc!!.isDrawerOpen
    }
}
