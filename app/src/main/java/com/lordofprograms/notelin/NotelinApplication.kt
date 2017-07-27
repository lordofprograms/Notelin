package com.lordofprograms.notelin

import android.content.Context
import com.activeandroid.app.Application
import com.lordofprograms.notelin.di.AppComponent
import com.lordofprograms.notelin.di.DaggerAppComponent
import com.lordofprograms.notelin.di.NoteDaoModule
import com.lordofprograms.notelin.utils.initPrefs

class NotelinApplication : Application() {

    companion object {
        lateinit var graph: AppComponent
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()

        initPrefs(this)

        context = this
        graph = DaggerAppComponent.builder().noteDaoModule(NoteDaoModule()).build()
    }

}
