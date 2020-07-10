package com.techyourchance.mvc.screens.questiondetails

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.techyourchance.mvc.common.permissions.PermissionsHelper
import com.techyourchance.mvc.questions.FetchQuestionDetailsUseCase
import com.techyourchance.mvc.questions.QuestionDetails
import com.techyourchance.mvc.screens.common.controllers.BaseFragment
import com.techyourchance.mvc.screens.common.dialogs.DialogsEventBus
import com.techyourchance.mvc.screens.common.dialogs.DialogsManager
import com.techyourchance.mvc.screens.common.dialogs.promptdialog.PromptDialogEvent
import com.techyourchance.mvc.screens.common.screensnavigator.ScreensNavigator

class QuestionDetailsFragment : BaseFragment(), FetchQuestionDetailsUseCase.Listener,
    QuestionDetailsViewMvc.Listener, DialogsEventBus.Listener,
    PermissionsHelper.Listener {
    private enum class ScreenState {
        IDLE, QUESTION_DETAILS_SHOWN, NETWORK_ERROR
    }

    private var mFetchQuestionDetailsUseCase: FetchQuestionDetailsUseCase? = null
    private var mScreensNavigator: ScreensNavigator? = null
    private var mDialogsManager: DialogsManager? = null
    private var mDialogsEventBus: DialogsEventBus? = null
    private var mPermissionsHelper: PermissionsHelper? = null
    private var mViewMvc: QuestionDetailsViewMvc? = null
    private var mScreenState: ScreenState? =
        ScreenState.IDLE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            mScreenState =
                savedInstanceState.getSerializable(SAVED_STATE_SCREEN_STATE) as ScreenState?
        }
        mPermissionsHelper = compositionRoot.permissionsHelper
        mFetchQuestionDetailsUseCase = compositionRoot.fetchQuestionDetailsUseCase
        mScreensNavigator = compositionRoot.screensNavigator
        mDialogsManager = compositionRoot.dialogsManager
        mDialogsEventBus = compositionRoot.dialogsEventBus
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mViewMvc = compositionRoot.viewMvcFactory.getQuestionDetailsViewMvc(container)
        return mViewMvc!!.rootView
    }

    override fun onStart() {
        super.onStart()
        mFetchQuestionDetailsUseCase!!.registerListener(this)
        mViewMvc!!.registerListener(this)
        mDialogsEventBus!!.registerListener(this)
        mPermissionsHelper!!.registerListener(this)
        if (mScreenState != ScreenState.NETWORK_ERROR) {
            fetchQuestionDetailsAndNotify()
        }
    }

    override fun onStop() {
        super.onStop()
        mFetchQuestionDetailsUseCase!!.unregisterListener(this)
        mViewMvc!!.unregisterListener(this)
        mDialogsEventBus!!.unregisterListener(this)
        mPermissionsHelper!!.unregisterListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(SAVED_STATE_SCREEN_STATE, mScreenState)
    }

    private fun fetchQuestionDetailsAndNotify() {
        mViewMvc!!.showProgressIndication()
        mFetchQuestionDetailsUseCase!!.fetchQuestionDetailsAndNotify(questionId)
    }

    private val questionId: String?
        private get() = arguments!!.getString(ARG_QUESTION_ID)

    override fun onQuestionDetailsFetched(questionDetails: QuestionDetails?) {
        mScreenState = ScreenState.QUESTION_DETAILS_SHOWN
        mViewMvc!!.hideProgressIndication()
        mViewMvc!!.bindQuestion(questionDetails)
    }

    override fun onQuestionDetailsFetchFailed() {
        mScreenState = ScreenState.NETWORK_ERROR
        mViewMvc!!.hideProgressIndication()
        mDialogsManager!!.showUseCaseErrorDialog(DIALOG_ID_NETWORK_ERROR)
    }

    override fun onDialogEvent(event: Any?) {
        if (event is PromptDialogEvent) {
            when (event.clickedButton) {
                PromptDialogEvent.Button.POSITIVE -> {
                    mScreenState = ScreenState.IDLE
                    fetchQuestionDetailsAndNotify()
                }
                PromptDialogEvent.Button.NEGATIVE -> mScreenState =
                    ScreenState.IDLE
            }
        }
    }

    override fun onLocationRequestClicked() {
        if (mPermissionsHelper!!.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            mDialogsManager!!.showPermissionGrantedDialog(null)
        } else {
            mPermissionsHelper!!.requestPermission(
                Manifest.permission.ACCESS_FINE_LOCATION,
                REQUEST_CODE
            )
        }
    }

    override fun onPermissionGranted(permission: String?, requestCode: Int) {
        if (requestCode == REQUEST_CODE) {
            mDialogsManager!!.showPermissionGrantedDialog(null)
        }
    }

    override fun onPermissionDeclined(permission: String?, requestCode: Int) {
        if (requestCode == REQUEST_CODE) {
            mDialogsManager!!.showDeclinedDialog(null)
        }
    }

    override fun onPermissionDeclinedDontAskAgain(permission: String?, requestCode: Int) {
        if (requestCode == REQUEST_CODE) {
            mDialogsManager!!.showPermissionDeclinedCantAskMoreDialog(null)
        }
    }

    override fun onNavigateUpClicked() {
        mScreensNavigator!!.navigateUp()
    }

    companion object {
        private const val ARG_QUESTION_ID = "ARG_QUESTION_ID"
        private const val DIALOG_ID_NETWORK_ERROR = "DIALOG_ID_NETWORK_ERROR"
        private const val SAVED_STATE_SCREEN_STATE = "SAVED_STATE_SCREEN_STATE"
        const val REQUEST_CODE = 1001
        fun newInstance(questionId: String?): QuestionDetailsFragment {
            val args = Bundle()
            args.putString(ARG_QUESTION_ID, questionId)
            val fragment = QuestionDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
