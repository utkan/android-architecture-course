package com.techyourchance.mvc.screens.questionslist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.techyourchance.mvc.questions.Question
import com.techyourchance.mvc.screens.common.ViewMvcFactory
import com.techyourchance.mvc.screens.questionslist.QuestionsRecyclerAdapter.MyViewHolder
import com.techyourchance.mvc.screens.questionslist.questionslistitem.QuestionsListItemViewMvc
import java.util.*

class QuestionsRecyclerAdapter(
    private val mListener: Listener,
    private val mViewMvcFactory: ViewMvcFactory
) : RecyclerView.Adapter<MyViewHolder>(), QuestionsListItemViewMvc.Listener {
    interface Listener {
        fun onQuestionClicked(question: Question)
    }

    class MyViewHolder(val mViewMvc: QuestionsListItemViewMvc) : RecyclerView.ViewHolder(mViewMvc.rootView!!)

    private var mQuestions: List<Question> = ArrayList()
    fun bindQuestions(questions: List<Question>?) {
        mQuestions = ArrayList(questions!!)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val viewMvc = mViewMvcFactory.getQuestionsListItemViewMvc(parent)
        viewMvc.registerListener(this)
        return MyViewHolder(viewMvc)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.mViewMvc.bindQuestion(mQuestions[position])
    }

    override fun getItemCount(): Int {
        return mQuestions.size
    }

    override fun onQuestionClicked(question: Question) {
        mListener.onQuestionClicked(question)
    }

}
