package com.techyourchance.mvc.common.dependencyinjection

import android.content.Context
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.techyourchance.mvc.common.permissions.PermissionsHelper
import com.techyourchance.mvc.networking.StackoverflowApi
import com.techyourchance.mvc.questions.FetchLastActiveQuestionsUseCase
import com.techyourchance.mvc.questions.FetchQuestionDetailsUseCase
import com.techyourchance.mvc.screens.common.ViewMvcFactory
import com.techyourchance.mvc.screens.common.controllers.BackPressDispatcher
import com.techyourchance.mvc.screens.common.dialogs.DialogsEventBus
import com.techyourchance.mvc.screens.common.dialogs.DialogsManager
import com.techyourchance.mvc.screens.common.fragmentframehelper.FragmentFrameHelper
import com.techyourchance.mvc.screens.common.fragmentframehelper.FragmentFrameWrapper
import com.techyourchance.mvc.screens.common.navdrawer.NavDrawerHelper
import com.techyourchance.mvc.screens.common.screensnavigator.ScreensNavigator
import com.techyourchance.mvc.screens.common.toastshelper.ToastsHelper
import com.techyourchance.mvc.screens.questionslist.QuestionsListController

class ControllerCompositionRoot(private val activityCompositionRoot: ActivityCompositionRoot) {
    private val activity: FragmentActivity
        get() = activityCompositionRoot.activity

    private val context: Context
        get() = activity

    private val fragmentManager: FragmentManager
        get() = activity.supportFragmentManager

    private val stackoverflowApi: StackoverflowApi
        get() = activityCompositionRoot.stackoverflowApi

    private val layoutInflater: LayoutInflater
        get() = LayoutInflater.from(context)

    val viewMvcFactory: ViewMvcFactory
        get() = ViewMvcFactory(layoutInflater, navDrawerHelper)

    private val navDrawerHelper: NavDrawerHelper
        get() = activity as NavDrawerHelper

    val fetchQuestionDetailsUseCase: FetchQuestionDetailsUseCase
        get() = FetchQuestionDetailsUseCase(stackoverflowApi)

    val fetchLastActiveQuestionsUseCase: FetchLastActiveQuestionsUseCase
        get() = FetchLastActiveQuestionsUseCase(stackoverflowApi)

    val questionsListController: QuestionsListController
        get() = QuestionsListController(
            fetchLastActiveQuestionsUseCase,
            screensNavigator,
            dialogsManager,
            dialogsEventBus
        )

    val toastsHelper: ToastsHelper
        get() = ToastsHelper(context)

    val screensNavigator: ScreensNavigator
        get() = ScreensNavigator(fragmentFrameHelper)

    private val fragmentFrameHelper: FragmentFrameHelper
        get() = FragmentFrameHelper(activity, fragmentFrameWrapper, fragmentManager)

    private val fragmentFrameWrapper: FragmentFrameWrapper
        get() = activity as FragmentFrameWrapper

    val backPressDispatcher: BackPressDispatcher
        get() = activity as BackPressDispatcher

    val dialogsManager: DialogsManager
        get() = DialogsManager(context, fragmentManager)

    val dialogsEventBus: DialogsEventBus
        get() = activityCompositionRoot.dialogsEventBus

    val permissionsHelper: PermissionsHelper
        get() = activityCompositionRoot.permissionsHelper

}
