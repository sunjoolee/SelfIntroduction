package com.sunjoolee.sparta_week4_selfintroductionapp.text_watchers

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import com.sunjoolee.sparta_week4_selfintroductionapp.ActivityCode
import com.sunjoolee.sparta_week4_selfintroductionapp.R
import com.sunjoolee.sparta_week4_selfintroductionapp.extentions.setInvisible
import com.sunjoolee.sparta_week4_selfintroductionapp.extentions.setVisible
import com.sunjoolee.sparta_week4_selfintroductionapp.sign_in.SignInActivity
import com.sunjoolee.sparta_week4_selfintroductionapp.sign_up.SignUpActivity

class PasswordTextWatcher(warningTextView: TextView, activityCode: ActivityCode) : TextWatcher {
    private var warningTextView: TextView
    private var activityCode: ActivityCode

    init {
        this.warningTextView = warningTextView
        this.activityCode = activityCode
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        when (activityCode) {
            ActivityCode.SIGN_IN -> {
                //로그인하는 경우, 비밀번호 비어있는 지만 확인
                if (p0.isNullOrBlank()) {
                    warningTextView.apply {
                        text = resources.getString(R.string.empty_password_warning)
                        setTextColor(resources.getColor(R.color.warning_color))
                        setVisible()
                    }
                    SignInActivity.isPasswordValid = false
                } else {
                    warningTextView.setInvisible()
                    SignInActivity.isPasswordValid = true
                }
            }

            ActivityCode.SIGN_UP -> {
                //회원가입하는 경우,
                //(1) 비어있는지 확인
                if (p0.isNullOrBlank()) {
                    warningTextView.apply {
                        text = resources.getString(R.string.empty_password_warning)
                        setTextColor(resources.getColor(R.color.warning_color))
                        setVisible()
                        SignUpActivity.isPasswordValid = false
                    }
                }
                //(2) 10자리 이상인지 확인
                else if (!isLongEnough(p0.toString())) {
                    warningTextView.apply {
                        text = resources.getString(R.string.password_length_information)
                        setTextColor(resources.getColor(R.color.information_color))
                        setVisible()
                        SignUpActivity.isPasswordValid = false
                    }
                }
                //(3) 대문자 포함하는지 확인
                else if (!containsUppercase(p0.toString())) {
                    warningTextView.apply {
                        text = resources.getString(R.string.password_uppercase_information)
                        setTextColor(resources.getColor(R.color.information_color))
                        setVisible()
                        SignUpActivity.isPasswordValid = false
                    }
                }
                //(4) 특수문자 포함하는지 확인
                else if (!containsSpecialChar(p0.toString())) {
                    warningTextView.apply {
                        text = resources.getString(R.string.password_special_char_information)
                        setTextColor(resources.getColor(R.color.information_color))
                        setVisible()
                        SignUpActivity.isPasswordValid = false
                    }
                }
                else warningTextView.setInvisible()
                SignUpActivity.isPasswordValid = true
            }
        }

    }

    override fun afterTextChanged(p0: Editable?) = Unit

    private fun isLongEnough(password: String): Boolean = (password.length >= 10)
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
}
