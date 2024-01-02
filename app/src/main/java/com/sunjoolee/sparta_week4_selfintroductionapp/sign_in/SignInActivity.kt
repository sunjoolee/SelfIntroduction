package com.sunjoolee.sparta_week4_selfintroductionapp.sign_in

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.sunjoolee.sparta_week4_selfintroductionapp.home.HomeActivity
import com.sunjoolee.sparta_week4_selfintroductionapp.R
import com.sunjoolee.sparta_week4_selfintroductionapp.user_info.UserInfoManager
import com.sunjoolee.sparta_week4_selfintroductionapp.sign_up.SignUpActivity

class SignInActivity : AppCompatActivity() {
    private val TAG = "SignInActivity"

    private val nameEditText by lazy { findViewById<EditText>(R.id.et_name) }
    private val passwordEditText by lazy { findViewById<EditText>(R.id.et_password) }

    private val nameWarningTextView by lazy { findViewById<TextView>(R.id.tv_name_warning) }
    private val passwordWarningTextView by lazy { findViewById<TextView>(R.id.tv_password_warning) }

    private val signInWarningTextView by lazy { findViewById<TextView>(R.id.tv_sign_in_warning) }
    private val signInButton by lazy { findViewById<Button>(R.id.btn_sign_in) }
    private val signUpButton by lazy { findViewById<Button>(R.id.btn_sign_up) }

    private val userInfoManager = UserInfoManager.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        initNameEditText()
        initPasswordEditText()

        initSignInButton()

        val startForResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                Log.d(TAG, "startForResultLauncher) RESULT_OK")
                val intent = result.data

                intent?.let {
                    val signUpName = it.getStringExtra("signUpName") ?: " "
                    val signUpPassword = it.getStringExtra("signUpPassword") ?: " "
                    Log.d(TAG, "startForResultLauncher) $signUpName $signUpPassword")

                    nameEditText.setText(signUpName)
                    passwordEditText.setText(signUpPassword)
                }
            }
        }

        initSignUpButton(startForResultLauncher)
    }

    private fun initNameEditText() {
        nameEditText.addTextChangedListener { text ->
            //로그인하는 경우, 이름이 비어있는지만 확인
            nameWarningTextView.run {
                if (text.isNullOrBlank()) {
                    setText(R.string.empty_name_warning)
                    isVisible = true
                } else {
                    isVisible = false
                }
            }
        }
    }

    private fun initPasswordEditText() {
        passwordEditText.addTextChangedListener { text ->
            //로그인하는 경우, 비밀번호 비어있는 지만 확인
            passwordWarningTextView.run {
                if (text.isNullOrBlank()) {
                    setText(R.string.empty_password_warning)
                    setTextColor(resources.getColor(R.color.warning_color))
                    isVisible = true
                } else {
                    isVisible = false
                }
            }
        }
    }

    private fun isNameValid(): Boolean {
        return !nameEditText.text.isNullOrBlank()
    }

    private fun isPasswordValid(): Boolean {
        return !passwordEditText.text.isNullOrBlank()
    }

    private fun initSignInButton() {
        signInButton.setOnClickListener {
            //로그인 하기 위해 모든 입력 유효한지 확인
            signInWarningTextView.run {
                if (!isNameValid()) {
                    Log.d(TAG, "sign in button) name is empty")
                    text = resources.getText(R.string.empty_name_warning)
                    isVisible = true
                    return@setOnClickListener
                }
                if (!isPasswordValid()) {
                    Log.d(TAG, "sign in button) password is empty")
                    text = resources.getText(R.string.empty_password_warning)
                    isVisible = true
                    return@setOnClickListener
                }
            }

            //로그인 시도하기
            val name = nameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (userInfoManager.signIn(name, password)) {
                //로그인 성공
                Log.d(TAG, "sign in button) sign in success")
                signInWarningTextView.isVisible = false

                val intent = Intent(applicationContext, HomeActivity::class.java).apply {
                    putExtra("name", name)
                    putExtra("password", password)
                }
                startActivity(intent)
            } else {
                //로그인 실패
                Log.d(TAG, "sign in button) sign in fail")
                signInWarningTextView.text = resources.getText(R.string.sign_in_fail_warning)
                signInWarningTextView.isVisible = true

            }
        }
    }

    private fun initSignUpButton(startForResultLauncher:ActivityResultLauncher<Intent>){
        signUpButton.setOnClickListener {
            val intent = Intent(applicationContext, SignUpActivity::class.java)
            startForResultLauncher.launch(intent)
        }
    }

}