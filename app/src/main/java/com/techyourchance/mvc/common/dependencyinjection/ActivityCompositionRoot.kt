package com.techyourchance.mvc.common.dependencyinjection

import androidx.fragment.app.FragmentActivity
import com.techyourchance.mvc.common.permissions.PermissionsHelper
import com.techyourchance.mvc.networking.StackoverflowApi
import com.techyourchance.mvc.screens.common.dialogs.DialogsEventBus

class ActivityCompositionRoot(
    private val compositionRoot: CompositionRoot,
    val activity: FragmentActivity
) {
    val permissionsHelper: PermissionsHelper = PermissionsHelper(activity)

    val stackoverflowApi: StackoverflowApi
        get() = compositionRoot.stackoverflowApi

    val dialogsEventBus: DialogsEventBus
        get() = compositionRoot.dialogsEventBus
}
