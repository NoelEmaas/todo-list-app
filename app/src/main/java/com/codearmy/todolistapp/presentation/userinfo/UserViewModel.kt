package com.codearmy.todolistapp.presentation.userinfo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codearmy.todolistapp.data.UserDataSource
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    var userName by mutableStateOf("")
        private set

    var userPicture by mutableStateOf("")
        private set

    init {
        getUserName()
        getUserPicture()
    }

    private fun getUserName() = viewModelScope.launch {
        val userFullName = UserDataSource.getUserName()
        var firstName = ""
        var lastName = ""

        for (i in userFullName.indices) {
            if(userFullName[i] == ' ') break;
            firstName += userFullName[i]
        }

        for (i in userFullName.length - 1 downTo 0) {
            if(userFullName[i] == ' ') break;
            lastName = userFullName[i] + lastName
        }

        userName = "$firstName $lastName"
    }

    private fun getUserPicture() = viewModelScope.launch {
        userPicture = UserDataSource.getUserPicture()
    }

}