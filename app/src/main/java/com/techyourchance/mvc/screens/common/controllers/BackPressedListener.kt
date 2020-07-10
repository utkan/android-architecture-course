package com.techyourchance.mvc.screens.common.controllers

interface BackPressedListener {
    /**
     *
     * @return true if the listener handled the back press; false otherwise
     */
    fun onBackPressed(): Boolean
}
