package com.chaitanyakhiratkar.myreceipts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import com.chaitanyakhiownerPhoneNumberratkar.myreceipts.services.authApi
import com.chaitanyakhiratkar.myreceipts.navigation.MainNavigation
import com.chaitanyakhiratkar.myreceipts.store.SharedPreference.userStore


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        val storeToken:String = userStore(context = this).getSharedPreferenceData("storeToken","")
        val userToken:String = userStore(context = this).getSharedPreferenceData("userToken","")
        authApi.setTokensInit(storeToken,userToken)
        val initStack = authApi.getStartStack()

        super.onCreate(savedInstanceState)
        setContent {
            MainNavigation(initStack)
        }
    }
}
