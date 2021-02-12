package com.applaudo.android.applaudoscodechallenge.utils

import android.app.Application

class MyApp : Application() {

    companion object {
        lateinit var instance: MyApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}