package com.sunjoolee.sparta_week4_selfintroductionapp.sign_in

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sunjoolee.sparta_week4_selfintroductionapp.user_info.UserInfoManager

enum class SignInValidity {
    NAME_EMPTY,
    PASSWORD_EMPTY,
    FAIL,
    SUCCESS
}

class SignInViewModel : ViewModel() {
    private val _isNameValid: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val isNameValid: LiveData<Boolean> get() = _isNameValid

    private val _isPasswordValid: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val isPasswordValid: LiveData<Boolean> get() = _isPasswordValid

    private val _isSignInValid: MutableLiveData<SignInValidity> = MutableLiveData<SignInValidity>()
    val isSignInValid: LiveData<SignInValidity> get() = _isSignInValid

    fun checkIfNameValid(text: Editable?) {
        _isNameValid.value = !(text.isNullOrBlank())
    }

    fun checkIfPasswordValid(text: Editable?) {
        _isPasswordValid.value = !(text.isNullOrBlank())
    }

    fun checkIfSignInValid(name: Editable?, password: Editable?) {
        if (name.isNullOrBlank()) {
            _isSignInValid.value = SignInValidity.NAME_EMPTY
            return
        }
        if (password.isNullOrBlank()) {
            _isSignInValid.value = SignInValidity.PASSWORD_EMPTY
            return
        }

        val userInfoManager = UserInfoManager.getInstance()
        _isSignInValid.value =
            if (userInfoManager.signIn(name.toString(), password.toString())) SignInValidity.SUCCESS
            else SignInValidity.FAIL
    }
}