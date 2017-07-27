package com.lordofprograms.notelin.di

import com.lordofprograms.notelin.mvp.models.NoteDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NoteDaoModule {

    @Provides
    @Singleton
    fun provideNoteDao(): NoteDao = NoteDao()

}