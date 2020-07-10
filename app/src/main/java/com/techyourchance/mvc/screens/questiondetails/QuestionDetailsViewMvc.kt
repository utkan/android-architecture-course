package com.techyourchance.mvc.screens.questiondetails

import com.techyourchance.mvc.questions.QuestionDetails
import com.techyourchance.mvc.screens.common.views.ObservableViewMvc

interface QuestionDetailsViewMvc : ObservableViewMvc<QuestionDetailsViewMvc.Listener?> {
    interface Listener {
        fun onNavigateUpClicked()
        fun onLocationRequestClicked()
    }

    fun bindQuestion(question: QuestionDetails?)
    fun showProgressIndication()
    fun hideProgressIndication()
}
