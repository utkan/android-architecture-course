package com.techyourchance.mvc.common.permissions

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.techyourchance.mvc.common.BaseObservable

class PermissionsHelper(private val activity: Activity) :
    BaseObservable<PermissionsHelper.Listener?>() {
    interface Listener {
        fun onPermissionGranted(permission: String?, requestCode: Int)
        fun onPermissionDeclined(permission: String?, requestCode: Int)
        fun onPermissionDeclinedDontAskAgain(permission: String?, requestCode: Int)
    }

    fun hasPermission(permission: String?): Boolean {
        return ContextCompat.checkSelfPermission(activity, permission!!) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(permission: String, requestCode: Int) {
        ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (permissions.isEmpty()) {
            throw RuntimeException("no permissions on request result")
        }
        val permission = permissions[0]
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            notifyPermissionGranted(permission, requestCode)
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission!!)) {
                notifyPermissionDeclined(permission, requestCode)
            } else {
                notifyPermissionDeclinedDontAskAgain(permission, requestCode)
            }
        }
    }

    private fun notifyPermissionDeclinedDontAskAgain(permission: String?, requestCode: Int) {
        for (listener in listeners) {
            listener?.onPermissionDeclinedDontAskAgain(permission, requestCode)
        }
    }

    private fun notifyPermissionDeclined(permission: String?, requestCode: Int) {
        for (listener in listeners) {
            listener?.onPermissionDeclined(permission, requestCode)
        }
    }

    private fun notifyPermissionGranted(permission: String?, requestCode: Int) {
        for (listener in listeners) {
            listener?.onPermissionGranted(permission, requestCode)
        }
    }

}
