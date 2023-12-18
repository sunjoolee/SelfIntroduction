package com.sunjoolee.sparta_week4_selfintroductionapp

import android.view.View
import android.widget.TextView

//TextView 확장 함수
fun TextView.setVisible() {
    visibility = View.VISIBLE
}

fun TextView.setInvisible() {
    visibility = View.INVISIBLE
}