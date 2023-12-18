package com.sunjoolee.sparta_week4_selfintroductionapp.text_watchers

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.TextView
import com.sunjoolee.sparta_week4_selfintroductionapp.ActivityCode
import com.sunjoolee.sparta_week4_selfintroductionapp.R
import com.sunjoolee.sparta_week4_selfintroductionapp.extentions.setInvisible
import com.sunjoolee.sparta_week4_selfintroductionapp.extentions.setVisible
import com.sunjoolee.sparta_week4_selfintroductionapp.sign_in.SignInActivity
import com.sunjoolee.sparta_week4_selfintroductionapp.sign_up.SignUpActivity
import com.sunjoolee.sparta_week4_selfintroductionapp.user_info.UserInfoManager

class NameTextWatcher(warningTextView: TextView, activityCode: ActivityCode): TextWatcher{
    private var warningTextView:TextView
    private var activityCode:ActivityCode

    init{
        this.warningTextView = warningTextView
        this.activityCode = activityCode
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        when(activityCode) {
            ActivityCode.SIGN_IN -> {
                //로그인하는 경우, 이름이 비어있는지만 확인
                if (p0.isNullOrBlank()){
                    warningTextView.apply{
                        text = resources.getText(R.string.empty_name_warning)
                        setVisible()
                    }
                    SignInActivity.isNameValid = false
                }
                else{
                    warningTextView.setInvisible()
                    SignInActivity.isNameValid = true
                }
            }
            ActivityCode.SIGN_UP -> {
                val userInfoManager = UserInfoManager.getInstance()
                //회원가입하는 경우,
                //(1) 이름이 비어있는지 확인
                if(p0.isNullOrBlank()){
                    warningTextView.apply{
                        text = resources.getText(R.string.empty_name_warning)
                        setVisible()
                    }
                    SignUpActivity.isNameValid = false
                }
                //(2) 이미 존재하는 이름인지 확인
                else if(userInfoManager.findSameName(p0.toString())){
                    warningTextView.apply{
                        text = resources.getText(R.string.same_name_warning)
                        setVisible()
                    }
                    SignUpActivity.isNameValid = false
                }
                else{
                    warningTextView.setInvisible()
                    SignUpActivity.isNameValid = true
                }
            }
        }
    }

    override fun afterTextChanged(p0: Editable?) = Unit
}