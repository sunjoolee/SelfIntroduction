package com.sunjoolee.sparta_week4_selfintroductionapp.text_watchers

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.TextView
import com.sunjoolee.sparta_week4_selfintroductionapp.extentions.isTextNull
import com.sunjoolee.sparta_week4_selfintroductionapp.extentions.setInvisible
import com.sunjoolee.sparta_week4_selfintroductionapp.extentions.setVisible

class PasswordTextWatcher(warningTextView: TextView) : TextWatcher{
    private var warningTextView:TextView
    init{
        this.warningTextView = warningTextView
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (p0.isNullOrBlank()) warningTextView.setVisible()
        else warningTextView.setInvisible()

    }
    override fun afterTextChanged(p0: Editable?) = Unit
}