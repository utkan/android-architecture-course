package com.techyourchance.mvc.screens.questionslist.questionslistitem

import com.techyourchance.mvc.questions.Question
import com.techyourchance.mvc.screens.common.views.ObservableViewMvc

interface QuestionsListItemViewMvc :
    ObservableViewMvc<QuestionsListItemViewMvc.Listener?> {
    interface Listener {
        fun onQuestionClicked(question: Question)
    }

    fun bindQuestion(question: Question?)
}
