package com.techyourchance.mvc.networking.questions

import com.google.gson.annotations.SerializedName

class QuestionDetailsResponseSchema(question: QuestionSchema) {
    @SerializedName("items")
    private val questions: List<QuestionSchema> = listOf(question)
    val question: QuestionSchema
        get() = questions[0]

}
