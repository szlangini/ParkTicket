package com.szlangini.example.myapplication.Controller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.szlangini.example.myapplication.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun loginBtnClicked(view: View){
        val addTicketIntent = Intent(this, AddTicketActivity::class.java)
        startActivity(addTicketIntent)
    }
}


