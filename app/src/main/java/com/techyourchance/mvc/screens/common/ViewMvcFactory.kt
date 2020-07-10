package com.techyourchance.mvc.screens.common

import android.view.LayoutInflater
import android.view.ViewGroup
import com.techyourchance.mvc.screens.common.dialogs.promptdialog.PromptViewMvc
import com.techyourchance.mvc.screens.common.dialogs.promptdialog.PromptViewMvcImpl
import com.techyourchance.mvc.screens.common.navdrawer.NavDrawerHelper
import com.techyourchance.mvc.screens.common.navdrawer.NavDrawerViewMvc
import com.techyourchance.mvc.screens.common.navdrawer.NavDrawerViewMvcImpl
import com.techyourchance.mvc.screens.common.toolbar.ToolbarViewMvc
import com.techyourchance.mvc.screens.questiondetails.QuestionDetailsViewMvc
import com.techyourchance.mvc.screens.questiondetails.QuestionDetailsViewMvcImpl
import com.techyourchance.mvc.screens.questionslist.QuestionsListViewMvc
import com.techyourchance.mvc.screens.questionslist.QuestionsListViewMvcImpl
import com.techyourchance.mvc.screens.questionslist.questionslistitem.QuestionsListItemViewMvc
import com.techyourchance.mvc.screens.questionslist.questionslistitem.QuestionsListItemViewMvcImpl

class ViewMvcFactory(private val mLayoutInflater: LayoutInflater, private val mNavDrawerHelper: NavDrawerHelper) {
    fun getQuestionsListViewMvc(parent: ViewGroup?): QuestionsListViewMvc {
        return QuestionsListViewMvcImpl(mLayoutInflater, parent, mNavDrawerHelper, this)
    }

    fun getQuestionsListItemViewMvc(parent: ViewGroup?): QuestionsListItemViewMvc {
        return QuestionsListItemViewMvcImpl(mLayoutInflater, parent)
    }

    fun getQuestionDetailsViewMvc(parent: ViewGroup?): QuestionDetailsViewMvc {
        return QuestionDetailsViewMvcImpl(mLayoutInflater, parent, this)
    }

    fun getToolbarViewMvc(parent: ViewGroup?): ToolbarViewMvc {
        return ToolbarViewMvc(mLayoutInflater, parent)
    }

    fun getNavDrawerViewMvc(parent: ViewGroup?): NavDrawerViewMvc {
        return NavDrawerViewMvcImpl(mLayoutInflater, parent)
    }

    fun getPromptViewMvc(parent: ViewGroup?): PromptViewMvc {
        return PromptViewMvcImpl(mLayoutInflater, parent)
    }

}
