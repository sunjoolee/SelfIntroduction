package com.sunjoolee.sparta_week4_selfintroductionapp

import android.widget.EditText
import com.sunjoolee.sparta_week4_selfintroductionapp.managers.PasswordCode
import com.sunjoolee.sparta_week4_selfintroductionapp.managers.PasswordInputManager

//EditText 확장 함수
fun EditText.isTextNull(): Boolean = text.isNullOrBlank()
fun EditText.isNameValid(): Boolean = true
fun EditText.isEmailValid(): Boolean = true
fun EditText.isPasswordValid(): PasswordCode {
    return PasswordInputManager.getInstance().isValid(text.toString())
}