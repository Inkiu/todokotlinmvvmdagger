package com.example.todo_kotlin_mvvm_dagger.ui.detail

import androidx.lifecycle.MutableLiveData
import com.example.todo_kotlin_mvvm_dagger.BaseViewModel
import com.example.todo_kotlin_mvvm_dagger.domain.model.Task
import com.example.todo_kotlin_mvvm_dagger.domain.usecase.DeleteTasks
import com.example.todo_kotlin_mvvm_dagger.domain.usecase.GetTask
import com.example.todo_kotlin_mvvm_dagger.domain.usecase.UpdateTask
import com.example.todo_kotlin_mvvm_dagger.events.Event
import io.reactivex.rxkotlin.subscribeBy

class TaskDetailViewModel(
    private val taskId: Long,
    private val getTask: GetTask,
    private val updateTask: UpdateTask,
    private val deleteTasks: DeleteTasks
) :  BaseViewModel() {

    val currentTask = MutableLiveData<Task>()
    val loadingState = MutableLiveData<Boolean>().apply { value = false }

    /* events */
    val navigateEditTask = MutableLiveData<Event<Long>>()
    val navigateBack = MutableLiveData<Event<Unit>>()
    val error = MutableLiveData<Event<ErrorType>>()

    override fun onCreate() {
        getTask(GetTask.Param(taskId))
            .doOnSubscribe { loadingState.value = true }
            .doFinally { loadingState.value = false }
            .subscribeBy {
                if (it is GetTask.Result.Success) {
                    currentTask.value = it.task
                } else {
                    error.value = Event(ErrorType.UNKNOWN_ERROR)
                }
            }
    }

    fun onTaskCompleteChange(complete: Boolean) {
        updateTask(UpdateTask.Param(taskId, complete))
            .subscribeBy(
                onError = { /* TODO */ },
                onComplete = { /* no-op */ }
            )
    }

    fun onTaskEdit() {
        navigateEditTask.value = Event(taskId)
    }

    fun onTaskDelete() {
        deleteTasks(DeleteTasks.Param(listOf(taskId)))
            .subscribeBy(
                onError = { /* TODO */ },
                onComplete = { navigateBack.value = Event(Unit) }
            )
    }

    enum class ErrorType {
        UNKNOWN_ERROR // TODO
    }
}