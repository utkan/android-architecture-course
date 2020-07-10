package com.techyourchance.mvc.screens.common.views

import java.util.*

abstract class BaseObservableViewMvc<ListenerType> : BaseViewMvc(), ObservableViewMvc<ListenerType> {
    private val mListeners: MutableSet<ListenerType> = HashSet()
    override fun registerListener(listener: ListenerType) {
        mListeners.add(listener)
    }

    override fun unregisterListener(listener: ListenerType) {
        mListeners.remove(listener)
    }

    protected val listeners: Set<ListenerType>
        protected get() = Collections.unmodifiableSet(mListeners)
}
