package com.techyourchance.mvc.screens.common.controllers

interface BackPressDispatcher {
    fun registerListener(listener: BackPressedListener?)
    fun unregisterListener(listener: BackPressedListener?)
}
