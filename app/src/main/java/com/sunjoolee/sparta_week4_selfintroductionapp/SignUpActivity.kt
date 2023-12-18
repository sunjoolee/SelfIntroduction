package com.sunjoolee.sparta_week4_selfintroductionapp

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener

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