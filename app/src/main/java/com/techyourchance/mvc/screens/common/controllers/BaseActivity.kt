package com.techyourchance.mvc.screens.common.controllers

import androidx.appcompat.app.AppCompatActivity
import com.techyourchance.mvc.common.CustomApplication
import com.techyourchance.mvc.common.dependencyinjection.ActivityCompositionRoot
import com.techyourchance.mvc.common.dependencyinjection.ControllerCompositionRoot

open class BaseActivity : AppCompatActivity() {
    private var mActivityCompositionRoot: ActivityCompositionRoot? = null
    private var mControllerCompositionRoot: ControllerCompositionRoot? = null
    val activityCompositionRoot: ActivityCompositionRoot
        get() {
            if (mActivityCompositionRoot == null) {
                mActivityCompositionRoot = ActivityCompositionRoot(
                    (application as CustomApplication).compositionRoot!!,
                    this
                )
            }
            return mActivityCompositionRoot!!
        }

    protected val compositionRoot: ControllerCompositionRoot
        protected get() {
            if (mControllerCompositionRoot == null) {
                mControllerCompositionRoot = ControllerCompositionRoot(activityCompositionRoot)
            }
            return mControllerCompositionRoot!!
        }
}
