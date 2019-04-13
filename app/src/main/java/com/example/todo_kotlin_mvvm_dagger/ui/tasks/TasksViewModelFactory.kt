package com.example.todo_kotlin_mvvm_dagger.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todo_kotlin_mvvm_dagger.domain.usecase.GetTasks
import java.lang.IllegalArgumentException
import javax.inject.Inject

class TasksViewModelFactory @Inject constructor(
    private val getTasks: GetTasks
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TasksViewModel::class.java)) {
            return TasksViewModel(getTasks) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}