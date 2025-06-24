package com.pardeep.roommvvmpractice.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.pardeep.roommvvmpractice.data.model.UserDataModel


/*
This interface having the functionality like add update,
 delete, get transaction etc .
 It act like bridge between the room and ui
 */

@Dao
interface RoomDao {

    // for Insert the userData
    @Insert
    suspend fun InsertUser(userData: UserDataModel)

    // for delete the userData
    @Query("DELETE FROM usertable WHERE userId = :index")
    suspend fun DeleteUser(index: Int)


    //for Update the userData
    @Query("Update usertable SET userName = :name, gender =:gender, email =:email Where userId = :index ")
    suspend fun updateUser(index: Int, name: String, gender: String, email: String)

    //for getAll Data of user
    @Query("SELECT * FROM usertable")
    suspend fun getAllUserData(): List<UserDataModel>

    // for get male Gender Specific data
    @Query("SELECT * FROM usertable WHERE gender In (:userGender)")
   suspend fun getGenderData(userGender: String): List<UserDataModel>

   // for search use case
   @Query("SELECT * FROM usertable WHERE userName Like ('%'|| :query || '%' OR email LIKE '%'|| :query || '%') AND gender = :gender")
   suspend fun searchUser(query: String,gender: String) : List<UserDataModel>


}