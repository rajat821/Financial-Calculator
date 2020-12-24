package com.care.financialcalculator.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    fun insertUser(userEntity: UserEntity)

    @Delete
    fun deleteUser(userEntity: UserEntity)

    @Query("SELECT * FROM user_details")
    fun getAllUsers(): List<UserEntity>

    @Query("SELECT * FROM user_details WHERE user_name = :userName")
    fun getUserByName(userName: String) : UserEntity
}