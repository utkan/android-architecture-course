package com.techyourchance.mvc.screens.common.dialogs

import android.content.Context
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.techyourchance.mvc.R
import com.techyourchance.mvc.screens.common.dialogs.infodialog.InfoDialog.Companion.newInfoDialog
import com.techyourchance.mvc.screens.common.dialogs.promptdialog.PromptDialog.Companion.newPromptDialog

class DialogsManager(private val mContext: Context, private val mFragmentManager: FragmentManager) {
    fun showUseCaseErrorDialog(tag: String?) {
        val dialogFragment: DialogFragment = newPromptDialog(
            getString(R.string.error_network_call_failed_title),
            getString(R.string.error_network_call_failed_message),
            getString(R.string.error_network_call_failed_positive_button_caption),
            getString(R.string.error_network_call_failed_negative_button_caption)
        )
        dialogFragment.show(mFragmentManager, tag)
    }

    fun showPermissionGrantedDialog(tag: String?) {
        val dialogFragment: DialogFragment = newInfoDialog(
            getString(R.string.permission_dialog_title),
            getString(R.string.permission_dialog_granted_message),
            getString(R.string.permission_dialog_button_caption)
        )
        dialogFragment.show(mFragmentManager, tag)
    }

    fun showPermissionDeclinedCantAskMoreDialog(tag: String?) {
        val dialogFragment: DialogFragment = newInfoDialog(
            getString(R.string.permission_dialog_title),
            getString(R.string.permission_dialog_cant_ask_more),
            getString(R.string.permission_dialog_button_caption)
        )
        dialogFragment.show(mFragmentManager, tag)
    }

    fun showDeclinedDialog(tag: String?) {
        val dialogFragment: DialogFragment = newInfoDialog(
            getString(R.string.permission_dialog_title),
            getString(R.string.permission_dialog_user_declined),
            getString(R.string.permission_dialog_button_caption)
        )
        dialogFragment.show(mFragmentManager, tag)
    }

    private fun getString(stringId: Int): String {
        return mContext.getString(stringId)
    }

    val shownDialogTag: String?
        get() {
            for (fragment in mFragmentManager.fragments) {
                if (fragment is BaseDialog) {
                    return fragment.getTag()
                }
            }
            return null
        }

}
