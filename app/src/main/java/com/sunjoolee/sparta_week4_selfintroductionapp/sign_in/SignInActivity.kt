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
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.observe
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

    private val model: SignInViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        initViewModel()

        initNameEditTextWatcher()
        initPasswordEditTextWatcher()

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

    private fun initViewModel() {
        with(model) {
            isNameValid.observe(this@SignInActivity) { isNameValid ->
                nameWarningTextView.setText(
                    if (isNameValid) ErrorMsg.PASS.message
                    else ErrorMsg.NAME_EMPTY.message
                )
            }

            isPasswordValid.observe(this@SignInActivity) { isPasswordValid ->
                passwordWarningTextView.setText(
                    if (isPasswordValid) ErrorMsg.PWD_EMPTY.message
                    else ErrorMsg.PASS.message
                )
            }

            isSignInValid.observe(this@SignInActivity) { isSignInValid ->
                signInWarningTextView.setText(
                    when (isSignInValid) {
                        SignInValidity.NAME_EMPTY -> ErrorMsg.NAME_EMPTY.message
                        SignInValidity.PASSWORD_EMPTY -> ErrorMsg.PWD_EMPTY.message
                        SignInValidity.SUCCESS -> ErrorMsg.PASS.message
                        SignInValidity.FAIL -> ErrorMsg.SIGN_IN_FAIL.message
                    }
                )

                if(isSignInValid == SignInValidity.SUCCESS)
                    startActivity(HomeActivity.newIntent(this@SignInActivity, nameEditText.text.toString(), passwordEditText.text.toString()))
            }
        }
    }

    private fun initNameEditTextWatcher() {
        nameEditText.addTextChangedListener { text ->
            model.checkIfNameValid(text)
        }
    }
    private fun initPasswordEditTextWatcher() {
        passwordEditText.addTextChangedListener { text ->
            model.checkIfPasswordValid(text)
        }
    }

    private fun initSignInButton() {
        signInButton.setOnClickListener {
            model.checkIfSignInValid(nameEditText.text, passwordEditText.text)
        }
    }
    private fun initSignUpButton(startForResultLauncher: ActivityResultLauncher<Intent>) {
        signUpButton.setOnClickListener {
            val intent = Intent(applicationContext, SignUpActivity::class.java)
            startForResultLauncher.launch(intent)
        }
    }

}