package com.care.financialcalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {

    lateinit var expenseCard : CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        expenseCard = findViewById(R.id.card1)

        expenseCard.setOnClickListener {
            val intent = Intent(this@MainActivity, Expense::class.java)
            startActivity(intent)
        }
    }
}