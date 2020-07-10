package com.techyourchance.mvc.screens.questionslist

import com.techyourchance.mvc.questions.FetchLastActiveQuestionsUseCase
import com.techyourchance.mvc.questions.Question
import com.techyourchance.mvc.screens.common.dialogs.DialogsEventBus
import com.techyourchance.mvc.screens.common.dialogs.DialogsManager
import com.techyourchance.mvc.screens.common.dialogs.promptdialog.PromptDialogEvent
import com.techyourchance.mvc.screens.common.screensnavigator.ScreensNavigator
import java.io.Serializable

class QuestionsListController(
    private val mFetchLastActiveQuestionsUseCase: FetchLastActiveQuestionsUseCase,
    private val mScreensNavigator: ScreensNavigator,
    private val mDialogsManager: DialogsManager,
    private val mDialogsEventBus: DialogsEventBus
) : QuestionsListViewMvc.Listener, FetchLastActiveQuestionsUseCase.Listener,
    DialogsEventBus.Listener {
    enum class ScreenState {
        IDLE, FETCHING_QUESTIONS, QUESTIONS_LIST_SHOWN, NETWORK_ERROR
    }

    private var mViewMvc: QuestionsListViewMvc? = null
    private var mScreenState =
        ScreenState.IDLE

    fun bindView(viewMvc: QuestionsListViewMvc?) {
        mViewMvc = viewMvc
    }

    val savedState: SavedState
        get() = SavedState(mScreenState)

    fun restoreSavedState(savedState: SavedState) {
        mScreenState = savedState.mScreenState
    }

    fun onStart() {
        mViewMvc!!.registerListener(this)
        mFetchLastActiveQuestionsUseCase.registerListener(this)
        mDialogsEventBus.registerListener(this)
        if (mScreenState != ScreenState.NETWORK_ERROR) {
            fetchQuestionsAndNotify()
        }
    }

    fun onStop() {
        mViewMvc!!.unregisterListener(this)
        mFetchLastActiveQuestionsUseCase.unregisterListener(this)
        mDialogsEventBus.unregisterListener(this)
    }

    private fun fetchQuestionsAndNotify() {
        mScreenState = ScreenState.FETCHING_QUESTIONS
        mViewMvc!!.showProgressIndication()
        mFetchLastActiveQuestionsUseCase.fetchLastActiveQuestionsAndNotify()
    }

    override fun onQuestionClicked(question: Question) {
        mScreensNavigator.toQuestionDetails(question.id)
    }

    override fun onLastActiveQuestionsFetched(questions: List<Question>) {
        mScreenState = ScreenState.QUESTIONS_LIST_SHOWN
        mViewMvc!!.hideProgressIndication()
        mViewMvc!!.bindQuestions(questions)
    }

    override fun onLastActiveQuestionsFetchFailed() {
        mScreenState = ScreenState.NETWORK_ERROR
        mViewMvc!!.hideProgressIndication()
        mDialogsManager.showUseCaseErrorDialog(null)
    }

    override fun onDialogEvent(event: Any?) {
        if (event is PromptDialogEvent) {
            when (event.clickedButton) {
                PromptDialogEvent.Button.POSITIVE -> fetchQuestionsAndNotify()
                PromptDialogEvent.Button.NEGATIVE -> mScreenState =
                    ScreenState.IDLE
            }
        }
    }

    class SavedState(val mScreenState: ScreenState) :
        Serializable

}
