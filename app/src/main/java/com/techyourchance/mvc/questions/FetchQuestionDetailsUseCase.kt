package com.techyourchance.mvc.questions

import com.techyourchance.mvc.common.BaseObservable
import com.techyourchance.mvc.networking.StackoverflowApi
import com.techyourchance.mvc.networking.questions.QuestionDetailsResponseSchema
import com.techyourchance.mvc.networking.questions.QuestionSchema
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FetchQuestionDetailsUseCase(private val stackoverflowApi: StackoverflowApi) :
    BaseObservable<FetchQuestionDetailsUseCase.Listener?>() {
    interface Listener {
        fun onQuestionDetailsFetched(questionDetails: QuestionDetails?)
        fun onQuestionDetailsFetchFailed()
    }

    fun fetchQuestionDetailsAndNotify(questionId: String?) {
        stackoverflowApi.fetchQuestionDetails(questionId)
            .enqueue(object : Callback<QuestionDetailsResponseSchema> {
                override fun onResponse(
                    call: Call<QuestionDetailsResponseSchema>,
                    response: Response<QuestionDetailsResponseSchema>
                ) {
                    if (response.isSuccessful) {
                        notifySuccess(response.body()!!.question)
                    } else {
                        notifyFailure()
                    }
                }

                override fun onFailure(call: Call<QuestionDetailsResponseSchema>, t: Throwable) {
                    notifyFailure()
                }
            })
    }

    private fun notifyFailure() {
        for (listener in listeners) {
            listener?.onQuestionDetailsFetchFailed()
        }
    }

    private fun notifySuccess(questionSchema: QuestionSchema) {
        for (listener in listeners) {
            listener?.onQuestionDetailsFetched(
                QuestionDetails(
                    questionSchema.id,
                    questionSchema.title,
                    questionSchema.body
                )
            )
        }
    }

}
