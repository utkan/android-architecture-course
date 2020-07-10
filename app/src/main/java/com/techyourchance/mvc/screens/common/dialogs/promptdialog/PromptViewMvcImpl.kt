package com.techyourchance.mvc.screens.common.dialogs.promptdialog

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.techyourchance.mvc.R
import com.techyourchance.mvc.screens.common.views.BaseObservableViewMvc

class PromptViewMvcImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?
) : BaseObservableViewMvc<PromptViewMvc.Listener?>(), PromptViewMvc {
    private val mTxtTitle: TextView
    private val mTxtMessage: TextView
    private val mBtnPositive: AppCompatButton
    private val mBtnNegative: AppCompatButton
    override fun setTitle(title: String?) {
        mTxtTitle.text = title
    }

    override fun setMessage(message: String?) {
        mTxtMessage.text = message
    }

    override fun setPositiveButtonCaption(caption: String?) {
        mBtnPositive.text = caption
    }

    override fun setNegativeButtonCaption(caption: String?) {
        mBtnNegative.text = caption
    }

    init {
        setRootView(inflater.inflate(R.layout.dialog_prompt, parent, false))

        mTxtTitle = findViewById(R.id.txt_title)!!
        mTxtMessage = findViewById(R.id.txt_message)!!
        mBtnPositive = findViewById(R.id.btn_positive)!!
        mBtnNegative = findViewById(R.id.btn_negative)!!
        mBtnPositive.setOnClickListener {
            for (listener in listeners) {
                listener!!.onPositiveButtonClicked()
            }
        }
        mBtnNegative.setOnClickListener {
            for (listener in listeners) {
                listener!!.onNegativeButtonClicked()
            }
        }
    }
}
