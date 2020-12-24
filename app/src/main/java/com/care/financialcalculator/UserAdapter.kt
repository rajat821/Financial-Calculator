package com.care.financialcalculator

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.care.financialcalculator.Database.UserEntity

class UserAdapter(val context: Context, val userList : List<UserEntity>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(view : View):RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}