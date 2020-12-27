package com.care.financialcalculator

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.care.financialcalculator.Database.UserDatabase
import com.care.financialcalculator.Database.UserEntity
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.DecimalFormat

class UserAdapter(val context: Context, var userList : ArrayList<UserEntity>, val text1 : TextView) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(view : View):RecyclerView.ViewHolder(view){
        val nameUser : TextView = view.findViewById(R.id.nameUser)
        val bscSalry : TextView = view.findViewById(R.id.bscSalry)
        val details : Button = view.findViewById(R.id.details)
        val delete : ImageView = view.findViewById(R.id.delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_layout_recycler,parent,false)
        return UserViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val users = userList[position]
        holder.nameUser.text = users.user_name
        holder.bscSalry.text = users.basicSalary.toString()

        val userEntity = UserEntity(
            users.user_name,
            users.basicSalary,
            users.ta,
            users.da,
            users.allowances,
            users.pf,
            users.expenses
        )

        holder.details.setOnClickListener {

            val customDialog: Dialog = Dialog(context)
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
            save.visibility = View.GONE
            val formatter = DecimalFormat("##,##,###.00")

            userName.text = users.user_name

            basicSalary.text = "Rs. "+ formatter.format(users.basicSalary).toString()
            ta.text = "Rs. "+ formatter.format(users.ta).toString()
            da.text = "Rs. "+ formatter.format(users.da).toString()
            allow.text = "Rs. "+ formatter.format(users.allowances).toString()

            pf.text = "Rs. "+ formatter.format(users.pf).toString()
            pe.text = "Rs. "+ formatter.format(users.expenses).toString()

            val net = users.basicSalary + users.ta + users.da + users.allowances - users.pf

            if (users.expenses > net) {
                msg.text = "Warning !! You Spent a lot"
                saveOrxtra.text = "Extra : "
                saveOrxtra.setTextColor(Color.RED)
                savings.setTextColor(Color.RED)
                savings.text = "Rs. "+ formatter.format(users.expenses - net).toString()
            } else {
                msg.text = "Hurray !! You saved this month"
                saveOrxtra.setTextColor(Color.GREEN)
                savings.setTextColor(Color.GREEN)
                savings.text = "Rs. "+ formatter.format(net - users.expenses).toString()
            }
            customDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            customDialog.show()

            close.setOnClickListener {
                customDialog.onBackPressed()
            }

        }

        holder.delete.setOnClickListener {

            val dialog = AlertDialog.Builder(context)
            dialog.setTitle("Delete")
            dialog.setIcon(R.drawable.ic_delete)
            dialog.setMessage("Do you want to delete record of ${users.user_name} ?")
            dialog.setPositiveButton("Ok") { text, listener ->

                if(Expense.DBAysctask(context,userEntity,1).execute().get()){
                    if(Expense.DBAysctask(context,userEntity,3).execute().get()){
                        Toast.makeText(context,"Deleted Successfully",Toast.LENGTH_SHORT).show()
                        userList.removeAt(position)
                        notifyDataSetChanged()
                        if(userList.isEmpty()) {
                            text1.visibility = View.VISIBLE
                        }
                    }
                    else{
                        Toast.makeText(context, "Some Error Occured", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show()
                }

            }
            dialog.setNegativeButton("Cancel"){text,listener ->
            }
            dialog.create()
            dialog.show()
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}