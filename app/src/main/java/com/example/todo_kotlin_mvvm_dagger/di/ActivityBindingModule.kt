package com.example.todo_kotlin_mvvm_dagger.di

import com.example.todo_kotlin_mvvm_dagger.data.PerActivity
import com.example.todo_kotlin_mvvm_dagger.ui.tasks.TasksActivity
import com.example.todo_kotlin_mvvm_dagger.ui.tasks.TasksModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector(modules = [TasksModule::class])
    @PerActivity
    abstract fun tasksActivity(): TasksActivity
}