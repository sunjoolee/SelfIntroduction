package com.sunjoolee.sparta_week4_selfintroductionapp.sign_in

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
import com.sunjoolee.sparta_week4_selfintroductionapp.home.HomeActivity
import com.sunjoolee.sparta_week4_selfintroductionapp.R
import com.sunjoolee.sparta_week4_selfintroductionapp.sign_up.SignUpActivity
import com.sunjoolee.sparta_week4_selfintroductionapp.extentions.isTextNull
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        nameEditText.addTextChangedListener(NameTextWatcher(nameWarningTextView))
        passwordEditText.addTextChangedListener(PasswordTextWatcher(passwordWarningTextView))

        signInButton.setOnClickListener(signInOnClickListener)
        signUpButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private val signInOnClickListener = object: OnClickListener{
        override fun onClick(p0: View?) {
            if (nameEditText.isTextNull()) {
                Log.d(TAG, "sign in button) name is empty")
                signInWarningTextView.apply{
                    text = resources.getText(R.string.empty_name_warning)
                    setVisible()
                }
                return
            }
            if (passwordEditText.isTextNull()) {
                Log.d(TAG, "sign in button) password is empty")
                signInWarningTextView.apply{
                    text = resources.getText(R.string.empty_password_warning)
                    setVisible()
                }
                return
            }

            val name = nameEditText.text.toString()
            val password = passwordEditText.text.toString()
            if (SignInManager.getInstance().signIn(name, password)) {
                Log.d(TAG, "sign in button) sign in success")
                signInWarningTextView.setInvisible()

                val intent = Intent(applicationContext, HomeActivity::class.java)
                intent.putExtra("name", name)
                intent.putExtra("password", password)
                startActivity(intent)
            } else {
                Log.d(TAG, "sign in button) sign in fail")
                signInWarningTextView.apply{
                    text = resources.getText(R.string.sign_in_fail_warning)
                    setVisible()
                }
            }
        }
    }
}