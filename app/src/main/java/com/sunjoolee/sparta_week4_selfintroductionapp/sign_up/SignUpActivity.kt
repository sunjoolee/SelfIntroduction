package com.sunjoolee.sparta_week4_selfintroductionapp.sign_up

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.sunjoolee.sparta_week4_selfintroductionapp.ErrorMsg
import com.sunjoolee.sparta_week4_selfintroductionapp.R
import com.sunjoolee.sparta_week4_selfintroductionapp.sign_in.SignInActivity
import com.sunjoolee.sparta_week4_selfintroductionapp.user_info.UserInfoManager

class SignUpActivity : AppCompatActivity() {
    private val TAG = "SignUpActivity"

    private val nameEditText by lazy { findViewById<EditText>(R.id.et_name) }
    private val nameWarningTextView by lazy { findViewById<TextView>(R.id.tv_name_warning) }

    private val emailEditText by lazy { findViewById<EditText>(R.id.et_email) }
    private val emailProviderEditText by lazy { findViewById<EditText>(R.id.et_email_provider) }
    private val emailProviderSpinner by lazy { findViewById<Spinner>(R.id.spinner_email_provider) }
    private val emailWarningTextView by lazy { findViewById<TextView>(R.id.tv_email_warning) }

    private val passwordEditText by lazy { findViewById<EditText>(R.id.et_password) }
    private val passwordWarningTextView by lazy { findViewById<TextView>(R.id.tv_password_warning) }

    private val passwordCheckEditText by lazy { findViewById<EditText>(R.id.et_password_check) }
    private val passwordCheckWarningTextView by lazy { findViewById<TextView>(R.id.tv_password_check_warning) }

    private val signUpWarningTextView by lazy { findViewById<TextView>(R.id.tv_sign_up_warning) }
    private val signUpButton by lazy { findViewById<Button>(R.id.btn_sign_up) }

    private val userInfoManager = UserInfoManager.getInstance()

    private var isNameValid = false
    private var isEmailValid = false
    private var isPasswordValid = false
    private var isPasswordCheckValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        initNameEditText()
        initEmailEditText()
        initPasswordEditText()
        initPasswordCheckEditText()

        initEmailProviderSpinner()

        initSignUpButton()
    }

    private fun initNameEditText() {
        nameEditText.addTextChangedListener { text ->
            nameWarningTextView.setText(ErrorMsg.PASS.message)
            isNameValid = true

            if (text.isNullOrBlank()) {
                nameWarningTextView.setText(ErrorMsg.NAME_EMPTY.message)
                isNameValid = false
            }
            else if (userInfoManager.findSameName(text.toString())) {
                nameWarningTextView.setText(ErrorMsg.NAME_SAME.message)
                isNameValid = false
            }
        }
    }

private fun initEmailEditText() {
    emailEditText.addTextChangedListener { text ->
        nameWarningTextView.setText(ErrorMsg.PASS.message)
        isNameValid = true

        if(text.isNullOrBlank()) {
            nameWarningTextView.setText(ErrorMsg.EMAIL_EMPTY.message)
            isNameValid = false
        }
    }
}

private fun initPasswordEditText() {
    passwordEditText.addTextChangedListener { text ->
        passwordWarningTextView.run {
            setText(ErrorMsg.PASS.message)
            setTextColor(resources.getColor(R.color.information_color))
            isPasswordValid = true

            //(1) 비어있는지 확인
            if (text.isNullOrBlank()) {
                setText(ErrorMsg.PWD_EMPTY.message)
                setTextColor(resources.getColor(R.color.warning_color))
                isPasswordValid = false
            }
            //(2) 10자리 이상인지 확인
            else if (text.length < 10) {
                setText(ErrorMsg.PWD_LENGTH.message)
                isPasswordValid = false

            }
            //(3) 대문자 포함하는지 확인
            else if (!containsUppercase(text.toString())) {
                setText(ErrorMsg.PWD_UPPERCASE.message)
                isPasswordValid = false
            }
            //(4) 특수문자 포함하는지 확인
            else if (!containsSpecialChar(text.toString())) {
                setText(ErrorMsg.PWD_SPECIAL_CHAR.message)
                isPasswordValid = false
            }
        }
    }
}

private fun containsUppercase(password: String): Boolean {
    for (ch in password) {
        if (('A'.code <= ch.code) && (ch.code <= 'Z'.code)) return true
    }
    return false
}

private fun containsSpecialChar(password: String): Boolean {
    val possibleSpecialChars =
        listOf('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '+', '=')
    for (ch in password) {
        if (possibleSpecialChars.contains(ch)) return true
    }
    return false
}

private fun initPasswordCheckEditText() {
    passwordCheckEditText.addTextChangedListener { text ->
        passwordCheckWarningTextView.run {
            setText(ErrorMsg.PASS.message)
            isPasswordCheckValid = true

            //비어있는지 확인
            if (text.isNullOrBlank()) {
                setText(ErrorMsg.PWD_EMPTY.message)
                isPasswordCheckValid = false
            }
            //비밀번호와 일치하는지 확인
            else if (text.toString() != passwordEditText.text.toString()) {
                setText(ErrorMsg.PWD_CHECK_DIFF.message)
                isPasswordCheckValid = false
            }
        }
    }
}

private fun initEmailProviderSpinner() {
    ArrayAdapter.createFromResource(
        this,
        R.array.email_providers_array,
        android.R.layout.simple_spinner_item
    ).also { adapter ->
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        emailProviderSpinner.adapter = adapter
    }
    emailProviderSpinner.onItemSelectedListener = spinnerOnItemSelectListener
}

private val spinnerOnItemSelectListener = object : AdapterView.OnItemSelectedListener {
    override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.d(TAG, "email provider spinner) onItemSelected")
        if (position == emailProviderSpinner.adapter.count - 1) {
            emailProviderSpinner.visibility = View.GONE
            emailProviderEditText.visibility = View.VISIBLE
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        Log.d(TAG, "email provider spinner) onNothingSelected")
    }
}

private fun initSignUpButton() {
    signUpButton.setOnClickListener {
        //회원가입하기 위해 모든 입력 유효한지 확인
        if (isNameValid && isEmailValid && isPasswordValid && isPasswordCheckValid) {
            Log.d(TAG, "sign up button) sign up success")
            signUpWarningTextView.setText(ErrorMsg.PASS.message)

            //회원가입
            val name = nameEditText.text.toString()
            val password = passwordEditText.text.toString()
            var email = emailEditText.text.toString()
            //email 서비스 제공자 붙이기
            email += if (emailProviderSpinner.visibility == View.VISIBLE) {
                emailProviderSpinner.selectedItem.toString()
            } else {
                emailProviderEditText.text?.toString() ?: ""
            }

            val userInfoManager = UserInfoManager.getInstance()
            userInfoManager.signUp(name, email, password)

            finishSignUpActivity(name, password)
        } else {
            Log.d(TAG, "sign up button) sign up fail")
            signUpWarningTextView.setText(ErrorMsg.SIGN_UP_FAIL.message)
        }
    }
}

private fun finishSignUpActivity(name: String, password: String) {
    setResult(Activity.RESULT_OK, SignInActivity.getResultIntent(name, password))
    finish()
}
}