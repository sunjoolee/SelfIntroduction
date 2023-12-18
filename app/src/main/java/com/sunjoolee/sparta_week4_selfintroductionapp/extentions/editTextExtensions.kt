package com.sunjoolee.sparta_week4_selfintroductionapp.extentions

import android.widget.EditText
import com.sunjoolee.sparta_week4_selfintroductionapp.PasswordCode
import com.sunjoolee.sparta_week4_selfintroductionapp.PasswordInputManager

//EditText 확장 함수
fun EditText.isTextNull(): Boolean = text.isNullOrBlank()
fun EditText.isNameValid(): Boolean = true
fun EditText.isEmailValid(): Boolean = true
fun EditText.isPasswordValid(): PasswordCode {
    return PasswordInputManager.getInstance().isValid(text.toString())
}