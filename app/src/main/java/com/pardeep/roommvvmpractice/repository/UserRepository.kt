package com.pardeep.roommvvmpractice.repository

import com.pardeep.roommvvmpractice.data.model.UserDataModel

interface UserRepository {

    suspend fun getUserData(): List<UserDataModel>
    suspend fun insertUserData(user: UserDataModel)
    suspend fun deleteUserData(index: Int)
    suspend fun updateUserData(index: Int, newData: UserDataModel)
    suspend fun getMaleData(option: String): List<UserDataModel>
    suspend fun getFemaleData(option: String): List<UserDataModel>
    suspend fun getFilterData(query: String, gender: String): List<UserDataModel>
}