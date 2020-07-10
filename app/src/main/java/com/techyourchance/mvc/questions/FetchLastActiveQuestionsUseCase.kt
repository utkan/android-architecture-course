package com.techyourchance.mvc.questions

import com.techyourchance.mvc.common.BaseObservable
import com.techyourchance.mvc.common.Constants
import com.techyourchance.mvc.networking.StackoverflowApi
import com.techyourchance.mvc.networking.questions.QuestionSchema
import com.techyourchance.mvc.networking.questions.QuestionsListResponseSchema
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class FetchLastActiveQuestionsUseCase(
    private val stackoverflowApi: StackoverflowApi
) :
    BaseObservable<FetchLastActiveQuestionsUseCase.Listener?>() {

    interface Listener {
        fun onLastActiveQuestionsFetched(questions: List<Question>)
        fun onLastActiveQuestionsFetchFailed()
    }

    fun fetchLastActiveQuestionsAndNotify() {
        stackoverflowApi.fetchLastActiveQuestions(Constants.QUESTIONS_LIST_PAGE_SIZE)
            .enqueue(object : Callback<QuestionsListResponseSchema> {
                override fun onResponse(
                    call: Call<QuestionsListResponseSchema>,
                    response: Response<QuestionsListResponseSchema>
                ) {
                    if (response.isSuccessful) {
                        notifySuccess(response.body()!!.questions)
                    } else {
                        notifyFailure()
                    }
                }

                override fun onFailure(call: Call<QuestionsListResponseSchema>, t: Throwable) {
                    t.printStackTrace()
                    notifyFailure()
                }
            })
    }

    private fun notifyFailure() {
        for (listener in listeners) {
            listener?.onLastActiveQuestionsFetchFailed()
        }
    }

    private fun notifySuccess(questionSchemas: List<QuestionSchema>) {
        val questions: MutableList<Question> = ArrayList(questionSchemas.size)
        for (questionSchema in questionSchemas) {
            questions.add(Question(questionSchema.id, questionSchema.title))
        }
        for (listener in listeners) {
            listener?.onLastActiveQuestionsFetched(questions)
        }
    }

}
