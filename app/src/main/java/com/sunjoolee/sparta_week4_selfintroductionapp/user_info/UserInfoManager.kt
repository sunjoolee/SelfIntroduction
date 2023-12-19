package com.sunjoolee.sparta_week4_selfintroductionapp.user_info

class UserInfoManager private constructor() {
    private var userInfoArray = arrayOf<UserInfo>()

    companion object{
        private var instance: UserInfoManager? = null

        fun getInstance(): UserInfoManager {
            if(instance == null) instance = UserInfoManager()
            return instance!!
        }
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

    fun findUserEmail(name: String, password: String): String {
        for(user in userInfoArray) {
            if((user.name == name) && (user.password == password)) return user.email
        }
        return ""
    }

    fun findSameName(name:String):Boolean{
        for(user in userInfoArray){
            if(user.name == name) return true
        }
        return false
    }

    fun signUp(name:String, email:String, password: String){
        userInfoArray += UserInfo(name, email, password)
    }
}