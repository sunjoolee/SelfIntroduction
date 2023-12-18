package com.sunjoolee.sparta_week4_selfintroductionapp.sign_up

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.sunjoolee.sparta_week4_selfintroductionapp.R

class SignUpActivity : AppCompatActivity() {
    private val TAG = "SignUpActivity"

    private val nameEditText by lazy{ findViewById<EditText>(R.id.etv_name) }
    private val nameWarningTextView by lazy{ findViewById<TextView>(R.id.tv_name_warning)}

    private val emailEditText by lazy{ findViewById<EditText>(R.id.etv_email)}
    private val emailWarningTextView by lazy{ findViewById<TextView>(R.id.tv_email_warning)}

    private val passwordEditText by lazy{ findViewById<EditText>(R.id.etv_password)}
    private val passwordWarningTextView by lazy{ findViewById<TextView>(R.id.tv_password_warning)}

    private val passwordCheckEditText by lazy{ findViewById<EditText>(R.id.etv_password_check)}
    private val passwordCheckWarningTextView by lazy{ findViewById<TextView>(R.id.tv_password_check_warning)}

    private val signUpButton by lazy{ findViewById<Button>(R.id.btn_sign_up)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


    }
}