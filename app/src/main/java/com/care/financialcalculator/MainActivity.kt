package com.care.financialcalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {

    lateinit var exit : ImageView
    lateinit var expenseCard : CardView
    lateinit var kvp : CardView
    lateinit var nsc : CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        exit = findViewById(R.id.exit)
        expenseCard = findViewById(R.id.card1)
        kvp = findViewById(R.id.card7)
        nsc = findViewById(R.id.card8)

        exit.setOnClickListener {
            onBackPressed()
            finish()
        }

        expenseCard.setOnClickListener {
            val intent = Intent(this@MainActivity, Expense::class.java)
            startActivity(intent)
        }

        kvp.setOnClickListener {
            val intent = Intent(this@MainActivity, KVP::class.java)
            startActivity(intent)
        }

        nsc.setOnClickListener {
            val intent = Intent(this@MainActivity, NSC::class.java)
            startActivity(intent)
        }
    }
}