package com.pardeep.roommvvmpractice.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// data model for local storage so it can be annotated as the entity



@Entity(tableName = "UserTable")
data class UserDataModel(
    @PrimaryKey(autoGenerate = true)
    var userId : Int = 0,
    var userName : String,
    var email : String,
    var gender : String
)
