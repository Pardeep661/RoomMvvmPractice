package com.pardeep.roommvvmpractice.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pardeep.roommvvmpractice.data.dataBase.AppDataBase

class HomeScreenViewModelFactory(
    val userRepositoryImp: UserRepositoryImp
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeScreenViewModel::class.java)) {
            return HomeScreenViewModel(userRepositoryImp = userRepositoryImp) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}