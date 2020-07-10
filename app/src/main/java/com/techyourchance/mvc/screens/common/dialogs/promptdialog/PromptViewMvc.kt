package com.techyourchance.mvc.screens.common.dialogs.promptdialog

import com.techyourchance.mvc.screens.common.views.ObservableViewMvc

interface PromptViewMvc : ObservableViewMvc<PromptViewMvc.Listener?> {
    interface Listener {
        fun onPositiveButtonClicked()
        fun onNegativeButtonClicked()
    }

    fun setTitle(title: String?)
    fun setMessage(message: String?)
    fun setPositiveButtonCaption(caption: String?)
    fun setNegativeButtonCaption(caption: String?)
}
