package com.jason.rickandmorty.ui

import android.app.Application
import android.content.Context
import com.jason.rickandmorty.data.DI.AppComponent
import com.jason.rickandmorty.data.DI.DaggerAppComponent

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent.create()

    }

    companion object{
        private lateinit var instance: MyApplication
        lateinit var appComponent: AppComponent

        fun getAppContext(): Context{
            return instance.applicationContext
        }

        fun getInstance(): MyApplication{
            return instance
        }
    }
}