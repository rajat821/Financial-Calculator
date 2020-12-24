package com.care.financialcalculator.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_details")
data class UserEntity (
    @PrimaryKey val user_name : String,
    @ColumnInfo(name = "basic_salary") val basicSalary : String,
    @ColumnInfo(name = "ta") val ta : String,
    @ColumnInfo(name = "da") val da : String,
    @ColumnInfo(name = "allowances") val allowances : String,
    @ColumnInfo(name = "pf") val pf : String,
    @ColumnInfo(name = "expenses") val expenses : String
    )
