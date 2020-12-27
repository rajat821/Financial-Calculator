package com.care.financialcalculator

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import org.w3c.dom.Text
import java.text.DecimalFormat

class KVP : AppCompatActivity() {

    lateinit var deposit : EditText
    lateinit var calculate : Button
    lateinit var reset : Button
    lateinit var interest : TextView
    lateinit var maturity : TextView
    lateinit var rl3 : RelativeLayout
    lateinit var back : ImageView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_k_v_p)

        back = findViewById(R.id.back)
        deposit = findViewById(R.id.deposit)
        calculate = findViewById(R.id.calculate)
        reset = findViewById(R.id.reset)
        interest = findViewById(R.id.interest)
        maturity =  findViewById(R.id.maturity)
        rl3 = findViewById(R.id.rl3)
        val formatter = DecimalFormat("##,##,###.00")

        back.setOnClickListener {
            onBackPressed()
        }

        calculate.setOnClickListener {

            if (deposit.text.isEmpty()){
                Toast.makeText(this@KVP,"Please Enter your deposit Amount",Toast.LENGTH_SHORT).show()
            }
            else{
                interest.text = "Rs. "+ formatter.format(deposit.text.toString().toFloat()).toString()
                maturity.text = "Rs. "+ formatter.format(2.0*(deposit.text.toString().toFloat())).toString()
                rl3.visibility = View.VISIBLE
                hideKeyboard()
            }
        }

        reset.setOnClickListener {
            rl3.visibility = View.GONE
            deposit.text.clear()
        }


    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}