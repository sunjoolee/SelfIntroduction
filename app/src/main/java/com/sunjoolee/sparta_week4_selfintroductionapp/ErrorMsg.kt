package com.sunjoolee.sparta_week4_selfintroductionapp

import androidx.annotation.StringRes

enum class ErrorMsg(@StringRes val message:Int) {
    NAME_EMPTY(R.string.empty_name_warning),
    NAME_SAME(R.string.same_name_warning),

    EMAIL_EMPTY(R.string.empty_name_warning),

    PWD_EMPTY(R.string.empty_password_warning),
    PWD_LENGTH(R.string.password_length_information),
    PWD_UPPERCASE(R.string.password_uppercase_information),
    PWD_SPECIAL_CHAR(R.string.password_special_char_information),

    PWD_CHECK_DIFF(R.string.password_different_warning),

    PASS(R.string.no_message),
    SIGN_IN_FAIL(R.string.sign_in_fail_warning),
    SIGN_UP_FAIL(R.string.sign_up_fail_warning)
}