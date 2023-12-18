package com.sunjoolee.sparta_week4_selfintroductionapp.managers

class LoginManager private constructor() {
    private var userInfoArray = arrayOf<UserInfo>()

    companion object{
        private var instance:LoginManager? = null

        fun getInstance():LoginManager{
            if(instance == null) instance = LoginManager()
            return instance!!
        }
    }

    fun signUp(name:String, email:String, password: String):Boolean{
        //동일한 아이디 및 비밀번호를 갖는 UserInfo가 존재하는 경우
        //회원가입 실패
        for(user in userInfoArray) {
            if((user.name == name) && (user.password == password)) return false
        }
        //새 UserInfo 생성, 회원가입 성공
        userInfoArray += UserInfo(name, email, password)
        return true
    }

    fun signIn(name: String, password: String):Boolean{
        //입력한 아이디 및 비밀번호를 갖는 UserInfo가 존재하는 경우
        //로그인 성공
        for(user in userInfoArray) {
            if((user.name == name) && (user.password == password)) return true
        }
        //로그인 실패
        return false
    }
}