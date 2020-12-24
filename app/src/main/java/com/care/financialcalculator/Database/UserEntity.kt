package com.care.financialcalculator.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_details")
data class UserEntity (
    @PrimaryKey val user_name : String,
    @ColumnInfo(name = "basic_salary") val basicSalary : Float,
    @ColumnInfo(name = "ta") val ta : Float,
    @ColumnInfo(name = "da") val da : Float,
    @ColumnInfo(name = "allowances") val allowances : Float,
    @ColumnInfo(name = "pf") val pf : Float,
    @ColumnInfo(name = "expenses") val expenses : Float
    )
