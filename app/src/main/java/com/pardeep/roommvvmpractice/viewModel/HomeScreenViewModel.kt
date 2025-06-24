package com.pardeep.roommvvmpractice.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pardeep.roommvvmpractice.data.model.UserDataModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val userRepositoryImp: UserRepositoryImp
) : ViewModel() {


    private var _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName

    private var _userEmail = MutableStateFlow("")
    val userEmail: StateFlow<String> = _userEmail

    private var _userGender = MutableStateFlow("Male")
    val userGender: StateFlow<String> = _userGender

    private var _searchData = MutableStateFlow("")
    val searchData: StateFlow<String> = _searchData


    private var _getUserData = MutableStateFlow<List<UserDataModel>>(emptyList())
    val getUserData: StateFlow<List<UserDataModel>> = _getUserData


    fun onQueryDataChange(query: String) {
        _searchData.value = query
    }

    init {
        refreshData()

    }

    fun refreshData(type: String? = null) {
        viewModelScope.launch {
            when (type) {
                "All" -> _getUserData.value = userRepositoryImp.getUserData()
                "Male" -> _getUserData.value = userRepositoryImp.getMaleData(type)
                "Female" -> _getUserData.value = userRepositoryImp.getFemaleData(type)
            }

        }
    }

    fun updateUserName(newUserName: String) {
        _userName.value = newUserName
    }

    fun updateEmail(newEmail: String) {
        _userEmail.value = newEmail
    }

    fun updateGender(newGender: String) {
        _userGender.value = newGender
    }

    fun insertUserData(
        name: String,
        gender: String,
        email: String
    ) {
        viewModelScope.launch {
            val user = UserDataModel(
                userName = name,
                gender = gender,
                email = email
            )
            userRepositoryImp.insertUserData(user)
            refreshData("All")
            _userGender.value = "Male"
            _userName.value = ""
            _userEmail.value = ""
        }


    }

    fun updateUserData(
        index: Int,
        name: String,
        gender: String,
        email: String
    ) {
        val user = UserDataModel(
            userName = name,
            gender = gender,
            email = email
        )
        viewModelScope.launch {
            userRepositoryImp.updateUserData(index, user)
            refreshData("All")
        }


    }

    fun deleteUserData(index: Int) {
        viewModelScope.launch {
            userRepositoryImp.deleteUserData(index)
            refreshData("All")
        }

    }


}