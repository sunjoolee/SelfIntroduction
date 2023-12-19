package com.sunjoolee.sparta_week4_selfintroductionapp.sign_in

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import com.sunjoolee.sparta_week4_selfintroductionapp.ActivityCode
import com.sunjoolee.sparta_week4_selfintroductionapp.home.HomeActivity
import com.sunjoolee.sparta_week4_selfintroductionapp.R
import com.sunjoolee.sparta_week4_selfintroductionapp.user_info.UserInfoManager
import com.sunjoolee.sparta_week4_selfintroductionapp.sign_up.SignUpActivity
import com.sunjoolee.sparta_week4_selfintroductionapp.extentions.setInvisible
import com.sunjoolee.sparta_week4_selfintroductionapp.extentions.setVisible
import com.sunjoolee.sparta_week4_selfintroductionapp.text_watchers.NameTextWatcher
import com.sunjoolee.sparta_week4_selfintroductionapp.text_watchers.PasswordTextWatcher

class SignInActivity : AppCompatActivity() {
    private val TAG = "SignInActivity"

    private val nameEditText by lazy { findViewById<EditText>(R.id.et_name) }
    private val passwordEditText by lazy { findViewById<EditText>(R.id.et_password) }

    private val nameWarningTextView by lazy { findViewById<TextView>(R.id.tv_name_warning) }
    private val passwordWarningTextView by lazy { findViewById<TextView>(R.id.tv_password_warning) }

    private val signInWarningTextView by lazy { findViewById<TextView>(R.id.tv_sign_in_warning) }
    private val signInButton by lazy { findViewById<Button>(R.id.btn_sign_in) }
    private val signUpButton by lazy { findViewById<Button>(R.id.btn_sign_up) }

    companion object {
        var isNameValid = false
        var isPasswordValid = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)


        nameEditText.addTextChangedListener(
            NameTextWatcher(nameWarningTextView, ActivityCode.SIGN_IN)
        )
        passwordEditText.addTextChangedListener(
            PasswordTextWatcher(passwordWarningTextView, ActivityCode.SIGN_IN)
        )

        signInButton.setOnClickListener(signInOnClickListener)

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

        signUpButton.setOnClickListener {
            val intent = Intent(applicationContext, SignUpActivity::class.java)
            startForResultLauncher.launch(intent)
        }
    }

    private val signInOnClickListener = object : OnClickListener {
        override fun onClick(p0: View?) {
            //로그인 하기 위해 모든 입력 유효한지 확인
            if (!isNameValid) {
                Log.d(TAG, "sign in button) name is empty")
                signInWarningTextView.apply {
                    text = resources.getText(R.string.empty_name_warning)
                    setVisible()
                }
                return
            }
            if (!isPasswordValid) {
                Log.d(TAG, "sign in button) password is empty")
                signInWarningTextView.apply {
                    text = resources.getText(R.string.empty_password_warning)
                    setVisible()
                }
                return
            }

            //로그인
            val name = nameEditText.text.toString()
            val password = passwordEditText.text.toString()
            if (UserInfoManager.getInstance().signIn(name, password)) {
                Log.d(TAG, "sign in button) sign in success")
                signInWarningTextView.setInvisible()

                intent.putExtra("name", name)
                intent.putExtra("password", password)
                startActivity(intent)
            } else {
                Log.d(TAG, "sign in button) sign in fail")
                signInWarningTextView.apply {
                    text = resources.getText(R.string.sign_in_fail_warning)
                    setVisible()
                }
            }
        }
    }
}