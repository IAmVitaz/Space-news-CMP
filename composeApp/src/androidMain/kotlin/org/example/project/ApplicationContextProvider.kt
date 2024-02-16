package org.example.project

import android.app.Application
import android.content.Context

class ApplicationContextProvider : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: ApplicationContextProvider? = null

        val context: Context
            get() = instance!!.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
    }
}
