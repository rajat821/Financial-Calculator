package com.care.financialcalculator

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.text.DecimalFormat

class Expense : AppCompatActivity() {

    lateinit var back: ImageView
    lateinit var name: EditText
    lateinit var basic: EditText
    lateinit var expense: EditText
    lateinit var calc: Button

    @SuppressLint("SetTextI18n")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense)

        back = findViewById(R.id.back)
        name = findViewById(R.id.edName)
        basic = findViewById(R.id.edBasic)
        expense = findViewById(R.id.edExpenses)
        calc = findViewById(R.id.calc)



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
                val formatter = DecimalFormat("##,##,###.00")
                val bs = basic.text.toString().toFloat()
                val ex = expense.text.toString().toFloat()

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
}