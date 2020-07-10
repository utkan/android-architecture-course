package com.techyourchance.mvc.screens.questionslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.techyourchance.mvc.screens.common.controllers.BaseFragment

class QuestionsListFragment : BaseFragment() {
    private var mQuestionsListController: QuestionsListController? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewMvc = compositionRoot.viewMvcFactory.getQuestionsListViewMvc(container)
        mQuestionsListController = compositionRoot.questionsListController
        savedInstanceState?.let { restoreControllerState(it) }
        mQuestionsListController!!.bindView(viewMvc)
        return viewMvc.rootView
    }

    private fun restoreControllerState(savedInstanceState: Bundle) {
        mQuestionsListController!!.restoreSavedState(
            (savedInstanceState.getSerializable(SAVED_STATE_CONTROLLER) as QuestionsListController.SavedState?)!!
        )
    }

    override fun onStart() {
        super.onStart()
        mQuestionsListController!!.onStart()
    }

    override fun onStop() {
        super.onStop()
        mQuestionsListController!!.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(SAVED_STATE_CONTROLLER, mQuestionsListController!!.savedState)
    }

    companion object {
        fun newInstance(): Fragment {
            return QuestionsListFragment()
        }

        private const val SAVED_STATE_CONTROLLER = "SAVED_STATE_CONTROLLER"
    }
}
