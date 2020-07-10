package com.techyourchance.mvc.screens.common.controllers

import androidx.fragment.app.Fragment
import com.techyourchance.mvc.common.dependencyinjection.ControllerCompositionRoot
import com.techyourchance.mvc.screens.common.main.MainActivity

open class BaseFragment : Fragment() {
    private var mControllerCompositionRoot: ControllerCompositionRoot? = null
    protected val compositionRoot: ControllerCompositionRoot
        protected get() {
            if (mControllerCompositionRoot == null) {
                mControllerCompositionRoot = ControllerCompositionRoot(
                    (requireActivity() as MainActivity).activityCompositionRoot
                )
            }
            return mControllerCompositionRoot!!
        }
}
