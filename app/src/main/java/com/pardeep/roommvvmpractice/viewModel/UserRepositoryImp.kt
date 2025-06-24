package com.pardeep.roommvvmpractice.viewModel

import android.util.Log
import com.pardeep.roommvvmpractice.data.dataBase.AppDataBase
import com.pardeep.roommvvmpractice.data.model.UserDataModel
import com.pardeep.roommvvmpractice.repository.UserRepository

class UserRepositoryImp(
    val appDataBase: AppDataBase
) : UserRepository {
    override suspend fun getUserData(): List<UserDataModel> {
        return appDataBase.roomDao().getAllUserData()
    }

    override suspend fun insertUserData(user: UserDataModel) {
        appDataBase.roomDao().InsertUser(user)
    }

    override suspend fun deleteUserData(index: Int) {
        appDataBase.roomDao().DeleteUser(index)
    }

    override suspend fun updateUserData(index: Int, newData: UserDataModel) {
        appDataBase.roomDao().updateUser(
            index,
            name = newData.userName,
            gender = newData.gender,
            email = newData.email,
        )
    }

    override suspend fun getMaleData(option: String): List<UserDataModel> {
        return appDataBase.roomDao().getGenderData(option)
    }

    override suspend fun getFemaleData(option: String): List<UserDataModel> {
        return appDataBase.roomDao().getGenderData(option)
    }

    override suspend fun getFilterData(query: String,gender : String): List<UserDataModel> {
        Log.d("UserRepoImpl", "getFilterData: data that return :${appDataBase.roomDao().searchUser(query,gender)}")
        return appDataBase.roomDao().searchUser(query,gender)
    }

}