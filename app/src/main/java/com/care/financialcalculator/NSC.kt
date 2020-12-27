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

class NSC : AppCompatActivity() {

    lateinit var deposit : EditText
    lateinit var calculate : Button
    lateinit var reset : Button
    lateinit var interest : TextView
    lateinit var maturity : TextView
    lateinit var rl3 : RelativeLayout
    lateinit var rl4 : RelativeLayout
    lateinit var year1 : TextView
    lateinit var year2 : TextView
    lateinit var year3 : TextView
    lateinit var year4 : TextView
    lateinit var year5 : TextView
    lateinit var back : ImageView
    var lst = arrayListOf<Double>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_n_s_c)

        back = findViewById(R.id.back)
        deposit = findViewById(R.id.deposit)
        calculate = findViewById(R.id.calculate)
        reset = findViewById(R.id.reset)
        interest = findViewById(R.id.interest)
        maturity =  findViewById(R.id.maturity)
        rl3 = findViewById(R.id.rl3)
        rl4 = findViewById(R.id.rl4)
        year1 = findViewById(R.id.first)
        year2 = findViewById(R.id.second)
        year3 = findViewById(R.id.third)
        year4 = findViewById(R.id.fourth)
        year5 = findViewById(R.id.fifth)


        val formatter = DecimalFormat("##,##,###.00")

        back.setOnClickListener {
            onBackPressed()
        }

        calculate.setOnClickListener {
            if (deposit.text.isEmpty()){
                Toast.makeText(this@NSC,"Please Enter your deposit Amount",Toast.LENGTH_SHORT).show()
            }
            else{
                var d = deposit.text.toString().toDouble()
                for(i in 0..4){
                    lst.add(d+(d*0.068))
                    d += (d * 0.068)
                 }
                year1.text = "Rs. "+ formatter.format(lst[0].toFloat()).toString()
                year2.text = "Rs. "+ formatter.format(lst[1].toFloat()).toString()
                year3.text = "Rs. "+ formatter.format(lst[2].toFloat()).toString()
                year4.text = "Rs. "+ formatter.format(lst[3].toFloat()).toString()
                year5.text = "Rs. "+ formatter.format(lst[4].toFloat()).toString()
                interest.text = "Rs. "+ formatter.format((lst[4].toFloat())-(deposit.text.toString().toFloat())).toString()
                maturity.text = "Rs. "+ formatter.format(lst[4]).toString()
                rl3.visibility = View.VISIBLE
                rl4.visibility = View.VISIBLE
                hideKeyboard()
            }
        }

        reset.setOnClickListener {
            rl3.visibility = View.GONE
            rl4.visibility = View.GONE
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