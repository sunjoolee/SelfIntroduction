package com.sunjoolee.sparta_week4_selfintroductionapp.sign_in

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.sunjoolee.sparta_week4_selfintroductionapp.ErrorMsg
import com.sunjoolee.sparta_week4_selfintroductionapp.R
import com.sunjoolee.sparta_week4_selfintroductionapp.home.HomeActivity
import com.sunjoolee.sparta_week4_selfintroductionapp.sign_up.SignUpActivity
import com.sunjoolee.sparta_week4_selfintroductionapp.user_info.UserInfoManager

class SignInActivity : AppCompatActivity() {
    private val TAG = "SignInActivity"

    companion object {
        val EXTRA_SIGN_UP_NAME = "extra_sign_up_name"
        val EXTRA_SIGN_UP_PASSWORD = "extra_sign_up_password"

        fun getResultIntent(signUpName: String, signUpPassword: String): Intent =
            Intent().apply {
                putExtra(EXTRA_SIGN_UP_NAME, signUpName)
                putExtra(EXTRA_SIGN_UP_PASSWORD, signUpPassword)
            }
    }

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
                    val signUpName = it.getStringExtra(EXTRA_SIGN_UP_NAME) ?: " "
                    val signUpPassword = it.getStringExtra(EXTRA_SIGN_UP_PASSWORD) ?: " "
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
            nameWarningTextView.setText(
                if (text.isNullOrBlank()) ErrorMsg.NAME_EMPTY.message
                else ErrorMsg.PASS.message
            )
        }
    }

    private fun initPasswordEditText() {
        passwordEditText.addTextChangedListener { text ->
            //로그인하는 경우, 비밀번호 비어있는 지만 확인
            passwordWarningTextView.setTextColor(resources.getColor(R.color.warning_color))
            passwordWarningTextView.setText(
                if (text.isNullOrBlank()) ErrorMsg.PWD_EMPTY.message
                else ErrorMsg.PASS.message
            )
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
            if (!isNameValid()) {
                Log.d(TAG, "sign in button) name is empty")
                signInWarningTextView.setText(ErrorMsg.NAME_EMPTY.message)
                return@setOnClickListener
            }
            if (!isPasswordValid()) {
                Log.d(TAG, "sign in button) password is empty")
                signInWarningTextView.setText(ErrorMsg.PWD_EMPTY.message)
                return@setOnClickListener
            }


            val name = nameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (userInfoManager.signIn(name, password)) {
                Log.d(TAG, "sign in button) sign in success")
                signInWarningTextView.setText(ErrorMsg.PASS.message)
                startActivity(HomeActivity.newIntent(this, name, password))
            }
            else {
                Log.d(TAG, "sign in button) sign in fail")
                signInWarningTextView.setText(ErrorMsg.SIGN_IN_FAIL.message)
            }
        }
    }

    private fun initSignUpButton(startForResultLauncher: ActivityResultLauncher<Intent>) {
        signUpButton.setOnClickListener {
            val intent = Intent(applicationContext, SignUpActivity::class.java)
            startForResultLauncher.launch(intent)
        }
    }

}