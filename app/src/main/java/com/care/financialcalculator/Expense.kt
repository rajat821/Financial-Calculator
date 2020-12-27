package com.care.financialcalculator

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.room.Room
import com.care.financialcalculator.Database.UserDatabase
import com.care.financialcalculator.Database.UserEntity
import java.text.DecimalFormat

class Expense : AppCompatActivity() {

    lateinit var back: ImageView
    lateinit var name: EditText
    lateinit var basic: EditText
    lateinit var expense: EditText
    lateinit var calc: Button
    lateinit var history : TextView

    @SuppressLint("SetTextI18n")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense)

        back = findViewById(R.id.back)
        name = findViewById(R.id.edName)
        basic = findViewById(R.id.edBasic)
        expense = findViewById(R.id.edExpenses)
        calc = findViewById(R.id.calc)
        history = findViewById(R.id.history)


        history.setOnClickListener {
            val intent = Intent(this@Expense,UserList::class.java)
            startActivity(intent)
        }



        back.setOnClickListener {
            onBackPressed()
        }


        calc.setOnClickListener {

            if (name.text.toString().isEmpty() || basic.text.toString()
                    .isEmpty() || expense.text.toString().isEmpty()
            ) {
                Toast.makeText(
                    this@Expense,
                    "Please enter all the required details",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                val customDialog: Dialog = Dialog(this@Expense)
                customDialog.setContentView(R.layout.dialog)
                val userName: TextView = customDialog.findViewById(R.id.userName)
                val basicSalary: TextView = customDialog.findViewById(R.id.basicSalary)
                val ta: TextView = customDialog.findViewById(R.id.ta)
                val da: TextView = customDialog.findViewById(R.id.da)
                val allow: TextView = customDialog.findViewById(R.id.allow)
                val savings: TextView = customDialog.findViewById(R.id.savings)
                val pf: TextView = customDialog.findViewById(R.id.pf)
                val pe: TextView = customDialog.findViewById(R.id.pe)
                val close: Button = customDialog.findViewById(R.id.close)
                val msg: TextView = customDialog.findViewById(R.id.msg)
                val saveOrxtra: TextView = customDialog.findViewById(R.id.saveOrxtra)
                val save : TextView = customDialog.findViewById(R.id.save)
                val formatter = DecimalFormat("##,##,###.00")
                val bs = basic.text.toString().toFloat()
                val ex = expense.text.toString().toFloat()

                val userEntity = UserEntity(
                    name.text.toString(),
                    bs,
                    (bs*0.1).toFloat(),
                    (bs * 0.15).toFloat(),
                    (bs * 0.20).toFloat(),
                    (bs * 0.12).toFloat(),
                    ex
                )

                save.setOnClickListener{

                    if(!DBAysctask(applicationContext,userEntity,1).execute().get()){

                        val async = DBAysctask(applicationContext, userEntity, 2).execute()
                        val rslt = async.get()
                        if(rslt){
                            Toast.makeText(this@Expense,"Saved Successfully",Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(this@Expense,"Some Error Occured",Toast.LENGTH_SHORT).show()
                        }

                        basic.text.clear()
                        expense.text.clear()
                        name.text.clear()
                    }
                    else{
                        Toast.makeText(this,"User Name already present",Toast.LENGTH_SHORT).show()
                    }
                    customDialog.onBackPressed()
                }

                close.setOnClickListener {
                    customDialog.onBackPressed()
                    basic.text.clear()
                    expense.text.clear()
                    name.text.clear()
                }

                userName.text = name.text.toString()

                basicSalary.text = "Rs. "+ formatter.format(bs).toString()
                ta.text = "Rs. "+ formatter.format(bs * 0.1).toString()
                da.text = "Rs. "+ formatter.format(bs * 0.15).toString()
                allow.text = "Rs. "+ formatter.format(bs * 0.20).toString()

                pf.text = "Rs. "+ formatter.format(bs * 0.12).toString()
                pe.text = "Rs. "+ formatter.format(ex).toString()

                val net = bs + (bs * 0.1) + (bs * 0.15) + (bs * 0.20) - (bs * 0.12)

                if (ex > net) {
                    msg.text = "Warning !! You Spent a lot"
                    saveOrxtra.text = "Extra : "
                    saveOrxtra.setTextColor(Color.RED)
                    savings.setTextColor(Color.RED)
                    savings.text = "Rs. "+ formatter.format(ex - net).toString()
                } else {
                    msg.text = "Hurray !! You saved this month"
                    saveOrxtra.setTextColor(Color.GREEN)
                    savings.setTextColor(Color.GREEN)
                    savings.text = "Rs. "+ formatter.format(net - ex).toString()
                }
                customDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                customDialog.show()
            }
        }


    }

    class DBAysctask(val context : Context, val userEntity: UserEntity, val op : Int) : AsyncTask<Void,Void,Boolean>(){

        val db = Room.databaseBuilder(context, UserDatabase::class.java,"user-db").build()

        override fun doInBackground(vararg params: Void?): Boolean {

            when(op){

                1->{
                    val user : UserEntity? = db.userDao().getUserByName(userEntity.user_name.toString())
                    db.close()
                    return user != null
                }

                2->{
                    db.userDao().insertUser(userEntity)
                    db.close()
                    return true
                }
                3->{
                    db.userDao().deleteUser(userEntity)
                    db.close()
                    return true
                }
            }
            return false
        }

    }
}