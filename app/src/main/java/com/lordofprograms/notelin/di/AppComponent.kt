package com.lordofprograms.notelin.di

import com.lordofprograms.notelin.mvp.presenters.MainPresenter
import com.lordofprograms.notelin.mvp.presenters.NotePresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(NoteDaoModule::class))
interface AppComponent {
    fun inject(mainPresenter: MainPresenter)

    fun inject(notePresenter: NotePresenter)
}