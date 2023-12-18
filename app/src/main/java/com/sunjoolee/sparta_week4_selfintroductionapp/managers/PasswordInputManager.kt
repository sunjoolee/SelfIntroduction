package com.sunjoolee.sparta_week4_selfintroductionapp.managers

class PasswordInputManager private constructor(){
    companion object{
        private var instance:PasswordInputManager? = null
        fun getInstance():PasswordInputManager{
            if(instance == null) instance = PasswordInputManager()
            return instance!!
        }
    }

    fun isValid(password:String):PasswordCode{
        //(1) 10자리 이상인지 확인
        if(password.length < 10) return PasswordCode.NOT_ENOUGH_CHAR

        //(2) 대문자 포함인지 확인
        var containsUppercase = false
        for(ch in password){
            if(('A'.code <= ch.code) && (ch.code <= 'Z'.code)){
                containsUppercase = true
                break
            }
        }
        if(!containsUppercase) return PasswordCode.NO_UPPERCASE_CHAR

        //(3) 특수문자 포함인지 확인
        val possibleSpecialChars =
            listOf('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '+', '=')
        var containsSpecialChar = false
        for(ch in password){
            if(possibleSpecialChars.contains(ch)){
                containsSpecialChar = true
                break
            }
        }
        if(!containsSpecialChar) return PasswordCode.NO_SPECIAL_CHAR

        //조건 (1),(2),(3) 만족하면 유효한 비밀번호
        return PasswordCode.VALID
    }
}