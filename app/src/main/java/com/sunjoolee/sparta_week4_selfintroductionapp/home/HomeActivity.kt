package com.sunjoolee.sparta_week4_selfintroductionapp.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.sunjoolee.sparta_week4_selfintroductionapp.R
import com.sunjoolee.sparta_week4_selfintroductionapp.sign_in.SignInManager

class HomeActivity : AppCompatActivity() {

    private val TAG = "HomeActivity"

    private val nameTextView:TextView by lazy{findViewById(R.id.tv_home_name)}
    private val emailTextView:TextView by lazy {findViewById(R.id.tv_home_email)}

    private val exitButton: Button by lazy{findViewById(R.id.btn_exit)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val name = intent.getStringExtra("name")!!
        val password = intent.getStringExtra("password")!!
        val email = SignInManager.getInstance().findUserEmail(name, password)

        nameTextView.text = resources.getString(R.string.home_name, name)
        emailTextView.text = resources.getString(R.string.home_email, email)

        exitButton.setOnClickListener {
            finish()
        }
    }
}