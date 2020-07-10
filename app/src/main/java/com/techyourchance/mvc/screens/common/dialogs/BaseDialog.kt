package com.techyourchance.mvc.screens.common.dialogs

import androidx.fragment.app.DialogFragment
import com.techyourchance.mvc.common.dependencyinjection.ControllerCompositionRoot
import com.techyourchance.mvc.screens.common.main.MainActivity

abstract class BaseDialog : DialogFragment() {
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
