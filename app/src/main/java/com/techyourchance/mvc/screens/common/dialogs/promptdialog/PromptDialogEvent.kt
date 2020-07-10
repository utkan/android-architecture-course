package com.techyourchance.mvc.screens.common.dialogs.promptdialog

data class PromptDialogEvent(val clickedButton: Button) {
    enum class Button {
        POSITIVE, NEGATIVE
    }
}
