package com.techyourchance.mvc.screens.questionslist.questionslistitem

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.techyourchance.mvc.R
import com.techyourchance.mvc.questions.Question
import com.techyourchance.mvc.screens.common.views.BaseObservableViewMvc

class QuestionsListItemViewMvcImpl(inflater: LayoutInflater, parent: ViewGroup?) :
    BaseObservableViewMvc<QuestionsListItemViewMvc.Listener?>(),
    QuestionsListItemViewMvc {
    private val mTxtTitle: TextView?
    private var mQuestion: Question? = null
    override fun bindQuestion(question: Question?) {
        mQuestion = question
        mTxtTitle!!.text = question!!.title
    }

    init {
        setRootView(inflater.inflate(R.layout.layout_question_list_item, parent, false))
        mTxtTitle = findViewById<TextView>(R.id.txt_title)
        rootView!!.setOnClickListener {
            for (listener in listeners) {
                listener?.onQuestionClicked(mQuestion!!)
            }
        }
    }
}
