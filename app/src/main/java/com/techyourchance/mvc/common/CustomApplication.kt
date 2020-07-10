package com.techyourchance.mvc.common

import android.app.Application
import com.techyourchance.mvc.common.dependencyinjection.CompositionRoot

class CustomApplication : Application() {
    var compositionRoot: CompositionRoot? = null
        private set

    override fun onCreate() {
        super.onCreate()
        compositionRoot = CompositionRoot()
    }

}
