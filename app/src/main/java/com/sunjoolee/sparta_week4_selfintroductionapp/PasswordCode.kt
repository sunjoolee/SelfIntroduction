package com.sunjoolee.sparta_week4_selfintroductionapp

enum class PasswordCode(val code:Int){
    VALID(1),
    NOT_ENOUGH_CHAR(2),
    NO_UPPERCASE_CHAR(3),
    NO_SPECIAL_CHAR(4)
}