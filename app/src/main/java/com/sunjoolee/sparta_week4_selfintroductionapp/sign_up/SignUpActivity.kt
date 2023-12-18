package com.sunjoolee.sparta_week4_selfintroductionapp.sign_up

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.sunjoolee.sparta_week4_selfintroductionapp.ActivityCode
import com.sunjoolee.sparta_week4_selfintroductionapp.R
import com.sunjoolee.sparta_week4_selfintroductionapp.text_watchers.EmailTextWatcher
import com.sunjoolee.sparta_week4_selfintroductionapp.text_watchers.NameTextWatcher
import com.sunjoolee.sparta_week4_selfintroductionapp.text_watchers.PasswordTextWatcher
import com.sunjoolee.sparta_week4_selfintroductionapp.user_info.UserInfoManager

class SignUpActivity : AppCompatActivity() {
    private val TAG = "SignUpActivity"

    private val nameEditText by lazy{ findViewById<EditText>(R.id.et_name) }
    private val nameWarningTextView by lazy{ findViewById<TextView>(R.id.tv_name_warning)}

    private val emailEditText by lazy{ findViewById<EditText>(R.id.et_email)}
    private val emailWarningTextView by lazy{ findViewById<TextView>(R.id.tv_email_warning)}

    private val passwordEditText by lazy{ findViewById<EditText>(R.id.et_password)}
    private val passwordWarningTextView by lazy{ findViewById<TextView>(R.id.tv_password_warning)}

    private val passwordCheckEditText by lazy{ findViewById<EditText>(R.id.et_password_check)}
    private val passwordCheckWarningTextView by lazy{ findViewById<TextView>(R.id.tv_password_check_warning)}

    private val signUpButton by lazy{ findViewById<Button>(R.id.btn_sign_up)}

    companion object{
        var isNameValid = false
        var isEmailValid = false
        var isPasswordValid = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        nameEditText.addTextChangedListener(
            NameTextWatcher(nameWarningTextView, ActivityCode.SIGN_UP))
        emailEditText.addTextChangedListener(
            EmailTextWatcher(emailWarningTextView))
        passwordEditText.addTextChangedListener(
            PasswordTextWatcher(passwordWarningTextView, ActivityCode.SIGN_UP))
        passwordCheckEditText.addTextChangedListener{

        }

        signUpButton.setOnClickListener {
            //회원가입하기 위해 모든 입력 유효한지 확인
            if(isNameValid && isEmailValid && isPasswordValid) {
                Log.d(TAG, "sign up button) sign up success")
                //회원가입
                val name = nameEditText.text.toString()
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()

                val userInfoManager = UserInfoManager.getInstance()
                userInfoManager.signUp(name, email, password)
                finish()
            }
            else{
                Log.d(TAG, "sign up button) sign up fail")
            }
        }
    }
}