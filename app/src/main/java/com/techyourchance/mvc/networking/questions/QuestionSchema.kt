package com.techyourchance.mvc.networking.questions

import com.google.gson.annotations.SerializedName
import com.techyourchance.mvc.networking.users.UserSchema

class QuestionSchema(
    @field:SerializedName("title") val title: String,
    @field:SerializedName("question_id") val id: String,
    @field:SerializedName("body") val body: String,
    @field:SerializedName("owner") val owner: UserSchema
)
