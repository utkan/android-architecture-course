package com.techyourchance.mvc.screens.common.screensnavigator

import com.techyourchance.mvc.screens.common.fragmentframehelper.FragmentFrameHelper
import com.techyourchance.mvc.screens.questiondetails.QuestionDetailsFragment
import com.techyourchance.mvc.screens.questionslist.QuestionsListFragment

class ScreensNavigator(private val mFragmentFrameHelper: FragmentFrameHelper) {
    fun toQuestionDetails(questionId: String?) {
        mFragmentFrameHelper.replaceFragment(QuestionDetailsFragment.newInstance(questionId))
    }

    fun toQuestionsList() {
        mFragmentFrameHelper.replaceFragmentAndClearBackstack(QuestionsListFragment.newInstance())
    }

    fun navigateUp() {
        mFragmentFrameHelper.navigateUp()
    }

}
