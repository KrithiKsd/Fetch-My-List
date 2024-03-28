package com.example.fetchmylist

import android.app.Application
import com.example.fetchmylist.di.AppContainer

class MyApplication: Application()  {
    lateinit var appContainer: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()

        appContainer= AppContainer()
    }
}
