package com.care.financialcalculator

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.care.financialcalculator.Database.UserDatabase
import com.care.financialcalculator.Database.UserEntity

class UserList : AppCompatActivity() {

    lateinit var recycler: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var adapter: UserAdapter
    lateinit var noUser: TextView
    lateinit var home : ImageView
    lateinit var back : ImageView

    var userList = arrayListOf<UserEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        recycler = findViewById(R.id.recycler)
        noUser = findViewById(R.id.noUser)
        back = findViewById(R.id.back)
        home = findViewById(R.id.home)

        layoutManager = LinearLayoutManager(this)

        userList = RetrieveUser(this@UserList).execute().get() as ArrayList<UserEntity>
        adapter = UserAdapter(this@UserList, userList,noUser)
        recycler.adapter = adapter
        recycler.layoutManager = layoutManager

        back.setOnClickListener {
            onBackPressed()
        }

        home.setOnClickListener {
            val intent = Intent(this@UserList, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        if (checkData(this@UserList).execute().get()) {
            noUser.visibility = View.VISIBLE
        }
    }

    class RetrieveUser(val context: Context) : AsyncTask<Void, Void, List<UserEntity>>() {
        override fun doInBackground(vararg params: Void?): List<UserEntity> {
            val db = Room.databaseBuilder(context, UserDatabase::class.java, "user-db").build()
            return db.userDao().getAllUsers()
        }
    }

    class checkData(val context: Context) : AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {
            val db = Room.databaseBuilder(context, UserDatabase::class.java, "user-db").build()
            val i = db.userDao().checkUser()
            if (i == 0) {
                return true
            }
            return false
        }
    }
}