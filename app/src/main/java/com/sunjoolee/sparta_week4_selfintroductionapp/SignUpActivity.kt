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

    //TextView 확장함수 정의
    private fun TextView.setVisible() {
        visibility = View.VISIBLE
    }
    private fun TextView.setInvisible() {
        visibility = View.INVISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        nameEditText.addTextChangedListener {
            if(it.isNullOrBlank()) nameWarningTextView.setVisible()
            else nameWarningTextView.setInvisible()
        }

        emailEditText.addTextChangedListener {
            if(it.isNullOrBlank()) emailWarningTextView.setVisible()
            else emailWarningTextView.setInvisible()
        }

        passwordEditText.addTextChangedListener {
            if(passwordEditText.text.isNullOrBlank()){
                passwordWarningTextView.apply {
                    setTextColor(resources.getColor(R.color.warning_color))
                    text = resources.getText(R.string.empty_password_warning)
                    setVisible()
                }
            }
            else {
                //비밀번호 유효성 체크
                val passwordValidResult = isPasswordValid()
                //유효한 비밀번호
                if(passwordValidResult == 0){
                    passwordWarningTextView.setInvisible()
                    return@addTextChangedListener
                }
                //유효하지 않은 비밀번호
                passwordWarningTextView.apply{
                    setTextColor(resources.getColor(R.color.warning_color))
                    text = when(isPasswordValid()){
                        1 -> resources.getText(R.string.password_length_information)
                        2-> resources.getText(R.string.password_uppercase_information)
                        3 -> resources.getText(R.string.password_special_char_information)
                        else -> ""
                    }
                    setVisible()
                }
            }
        }

        passwordCheckEditText.addTextChangedListener {
            if(passwordEditText.text.isNullOrBlank()) {
                passwordWarningTextView.apply {
                    text = resources.getText(R.string.empty_password_warning)
                    setVisible()
                }
            }
            else {
                passwordWarningTextView.apply {
                    if (isPasswordCheckValid()){
                        setInvisible()
                    }
                    else{
                        text = resources.getText(R.string.password_check_different_warning)
                        setVisible()
                    }
                }
            }
        }

        //회원가입 버튼 클릭 이벤트 처리
        signUpButton.setOnClickListener {
            if(isAllInputValid()) {
                Log.d(TAG, "회원가입 버튼) 회원가입 성공")    
                //finish()
            }
            else{
                Log.d(TAG, "회원가입 버튼) 회원가입 실패")
            }
        }
    }
    private fun isPasswordValid():Int{
        val passwordInput = passwordEditText.text
        //(1) 10자리 이상인지 확인
        if(passwordInput.length < 10) return 1
        //(2) 대문자 포함인지 확인
        var containsUppercase = false
        for(ch in passwordInput){
            if(('A'.code <= ch.code) && (ch.code <= 'Z'.code)){
                containsUppercase = true
                 break
            }
        }
        if(!containsUppercase) return 2
        //(3) 특수문자 포함인지 확인
        val possibleSpecialChars = listOf('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '+', '=')
        var containsSpecialChar = false
        for(ch in passwordInput){
            if(possibleSpecialChars.contains(ch)){
                containsSpecialChar = true
                break
            }
        }
        if(!containsSpecialChar) return 3

        //유효한 비밀번호인 경우
        return 0
    }

    private fun isPasswordCheckValid(): Boolean {
        return passwordCheckEditText.text == passwordEditText.text
    }
    private fun isAllInputValid():Boolean{
        if(nameEditText.text.isNullOrBlank()) return false
        if(emailEditText.text.isNullOrBlank()) return false
        if((passwordEditText.text.isNullOrBlank()) && (isPasswordValid() != 0)) return false
        if(passwordCheckEditText.text.isNullOrBlank() && !isPasswordCheckValid()) return false
        return true
    }
}