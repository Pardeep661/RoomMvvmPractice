package com.pardeep.roommvvmpractice.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pardeep.roommvvmpractice.data.model.UserDataModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
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

    private var _searchquery = MutableStateFlow("")
    val searchData: StateFlow<String> = _searchquery


    private var _getUserData = MutableStateFlow<List<UserDataModel>>(emptyList())
    val getUserData: StateFlow<List<UserDataModel>> = _getUserData


    fun onQueryDataChange(query: String, genderLabel: String) {
        _searchquery.value = query
        _userGender.value = genderLabel
    }

    private fun observeQuery() {
        viewModelScope.launch {
            combine(_searchquery.debounce(300), _userGender) { query, gender ->
                Pair(query, gender)
            }.collect { (query, gender) ->
                Log.d(
                    "HomeViewModel",
                    "observeQuery: gender value :${gender} and query value is${query}"
                )
                _getUserData.value = when {
                    query.isBlank() && gender == "All" -> userRepositoryImp.getUserData()
                    query.isBlank() -> userRepositoryImp.getFilterData(query, gender)
                    else -> userRepositoryImp.getFilterData(query, gender)
                }
            }
        }
    }

    init {
        observeQuery()
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
            onQueryDataChange("", "All")
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
            onQueryDataChange("", "All")
        }


    }

    fun deleteUserData(index: Int) {
        viewModelScope.launch {
            userRepositoryImp.deleteUserData(index)
            onQueryDataChange("", "All")
        }

    }


}