package com.codearmy.todolistapp.presentation.screens.addedittask

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codearmy.todolistapp.data.TaskDataSource

data class AddEditTaskUiState(
    var taskName: String = "",
    val taskDescription: String = "",
    val taskDueDate: String = "No due date",
    val isTaskFinished: Boolean = false,
    val taskId : String = "",
    val isLoading: Boolean = false,
    val isTaskSaved: Boolean = false,
    val onEditTask: Boolean = false,
    val viewAsMarkdown: Boolean = false
)

class AddEditTaskViewModel : ViewModel() {

    var addEditTaskUiState by mutableStateOf(AddEditTaskUiState())
        private set

    fun updateName(newName: String) {
        addEditTaskUiState = addEditTaskUiState.copy(taskName = newName)
    }

    fun updateDescription(newDescription: String) {
        addEditTaskUiState = addEditTaskUiState.copy(taskDescription = newDescription)
    }

    fun updateDueDate(newDate: String) {
        addEditTaskUiState = addEditTaskUiState.copy(taskDueDate = newDate)
    }

    fun updateTaskId(taskId: String) {
        addEditTaskUiState = addEditTaskUiState.copy(taskId = taskId)
    }

    fun updateDescriptionView() {
        addEditTaskUiState = addEditTaskUiState.copy(viewAsMarkdown = !addEditTaskUiState.viewAsMarkdown)
    }

    fun saveTask() {
        TaskDataSource.saveTask(
            addEditTaskUiState.taskName,
            addEditTaskUiState.taskDescription,
            addEditTaskUiState.taskDueDate,
            addEditTaskUiState.isTaskFinished,
            addEditTaskUiState.taskId
        )
    }

    fun getTask(taskId : String) {
        TaskDataSource.getTask(
            taskId,
            onError = {}
        ) {
            if (it != null) {
                addEditTaskUiState =
                    it.taskName?.let { it1 -> addEditTaskUiState.copy(taskName = it1) }!!
                addEditTaskUiState =
                    it.taskDescription?.let { it1 -> addEditTaskUiState.copy(taskDescription = it1) }!!
                addEditTaskUiState =
                    it.taskDueDate?.let { it1 -> addEditTaskUiState.copy(taskDueDate = it1) }!!
                addEditTaskUiState =
                    it.taskID?.let { it1 -> addEditTaskUiState.copy(taskId = it1) }!!
                addEditTaskUiState =
                    it.taskIsFinished?.let { it1 -> addEditTaskUiState.copy(isTaskFinished = it1) }!!
            }
        }
    }

    fun resetState() {
        addEditTaskUiState = AddEditTaskUiState()
    }

}