package com.chaitanyakhiratkar.myreceipts.services

class UserService {
    lateinit var user:UserModel

    fun saveUser(newUser :UserModel){
        user = newUser;
    }

    fun getUserDetails():UserModel{
        return user
    }


    fun getUserPhoneNumber():String{
        return user.phoneNumber
    }

}

val userApi = UserService()