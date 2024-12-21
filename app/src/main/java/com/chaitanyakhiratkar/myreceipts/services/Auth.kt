package com.chaitanyakhiownerPhoneNumberratkar.myreceipts.services

import androidx.navigation.NavHostController
import com.chaitanyakhiratkar.myreceipts.constants.Routes
import com.chaitanyakhiratkar.myreceipts.constants.Stacks
import com.chaitanyakhiratkar.myreceipts.services.APIResponse
import com.chaitanyakhiratkar.myreceipts.services.STORE_LIST
import com.chaitanyakhiratkar.myreceipts.services.StoreModel
import com.chaitanyakhiratkar.myreceipts.services.USER_LIST
import com.chaitanyakhiratkar.myreceipts.services.UserModel
import com.chaitanyakhiratkar.myreceipts.services.hashPassword
import com.chaitanyakhiratkar.myreceipts.store.SharedPreference.userStore
import com.chaitanyakhiratkar.myreceipts.utils.isValidPhoneNumberString
import com.chaitanyakhiratkar.myreceipts.utils.otpUtils
import com.chaitanyakhiratkar.myreceipts.utils.showShortToast

private fun logInAPI(phoneNumber: String, password: String): APIResponse {
    val admin = USER_LIST.find { it.phoneNumber == phoneNumber }
    val store = STORE_LIST.find { it.id == admin?.storeId ?: "" }

    return if (store != null && store.rootPassword == hashPassword(password)) {

        if (admin != null) {
            return APIResponse(
                data = mapOf(
                    "userToken" to admin.token, "storeToken" to store.token, "store" to store
                ),
                200,
                "Sign In Succesfull ! Welcome Back !",
            )
        } else {

            return APIResponse(
                null,
                400,
                "Sign In Failed , No Admin Found",
            )

        }
    } else {
        return APIResponse(
            null, 200, "Invalid phone number or password."
        )
    }
}

fun verifyAdminAPI(store: StoreModel, admin: UserModel): APIResponse {
    // Check if admin with the same phone number already exists
    val existingAdmin = USER_LIST.find { it.phoneNumber == admin.phoneNumber }

    return if (existingAdmin != null) {
        APIResponse(
            data = null,
            status = 400,
            message = "User Already Exist"
        )
    } else {
        APIResponse(
            data = null,
            status = 200,
            message = "Ok"
        )
    }
}


fun signInAPI(store: StoreModel, admin: UserModel): APIResponse {
    // Check if admin with the same phone number already exists
    val existingAdmin = USER_LIST.find { it.phoneNumber == admin.phoneNumber }

    return if (existingAdmin != null) {
        APIResponse(
            data = null,
            status = 200,
            message = "User Already Exist"
        )
    } else {
        try {
            // Add the store and admin to respective lists
            STORE_LIST.add(store)
            USER_LIST.add(admin)

            APIResponse(
                data = mapOf("store" to store, "admin" to admin),
                status = 201,
                message = "Sign Up Successful"
            )
        } catch (e: Exception) {
            APIResponse(
                data = null,
                status = 500,
                message = "Sign Up Failed"
            )
        }
    }
}

class AuthService {
    private var storeToken: String = ""
    private var userToken: String = ""

    // Sets the tokens in init
    fun setTokensInit(storeTokenInit: String, userTokeninit: String) {
        storeToken = storeTokenInit
        userToken = userTokeninit
    }

    fun getStartStack(): String {
        if (storeToken == "" || userToken == "") {
            return Stacks.ON_BOARD
        }
        return Stacks.MAIN
    }

    // Function to verify phone number and password
    fun handleLogin(
        phoneNumber: String,
        password: String,
        navController: NavHostController,
        context: android.content.Context
    ) {
        if (isValidPhoneNumberString(phoneNumber)) {

            val response: APIResponse = logInAPI(phoneNumber = phoneNumber, password = password)
            val data = response.data as? Map<String, Any> // Ensure proper casting

            if (data != null && response.status == 200) {


                userToken = (data["userToken"] as? String)?.takeIf { it.isNotEmpty() } ?: ""
                storeToken = (data["storeToken"] as? String)?.takeIf { it.isNotEmpty() } ?: ""

                if (userToken == "" || storeToken == "") {
                    showShortToast(context, "Login Failed")
                } else {
                    userStore(context).editSharedPreferenceData("userToken", userToken)
                    userStore(context).editSharedPreferenceData("storeToken", storeToken)

                    // Navigate to Main stack and clear previous stack
                    navController.navigate(Stacks.MAIN) {
                        popUpTo(Stacks.ON_BOARD) { inclusive = true }
                    }
                }


            } else {
                showShortToast(context, response.message)
            }

        } else {
            showShortToast(context, "Invalid phone number")
        }
    }


    fun handleLogOut(
        navController: NavHostController,
        context: android.content.Context
    ) {
        userStore(context).editSharedPreferenceData("userToken", "")
        userStore(context).editSharedPreferenceData("storeToken", "")
        userToken = ""
        storeToken = ""
        navController.navigate(Stacks.ON_BOARD) {
            popUpTo(Stacks.MAIN) { inclusive = true }
        }
    }


    fun handleRegistration(
        navController: NavHostController,
        context: android.content.Context,
        store: StoreModel?,
        admin: UserModel?,
    ) {
        // create User
        if (admin != null && store != null) {
            val response: APIResponse = verifyAdminAPI(store = store, admin = admin)
            if (response.status == 200) {
                otpUtils.sendOTP(
                    phoneNumber = "7057868697", // TOD0
                    onFailure = {
                        showShortToast(context, "OTP couldn't be send !")
                    },
                    onCodeSent = {
                        showShortToast(context, "OTP has been send to you phone number !")
                    },
                    onVerificationCompleted = {
                        showShortToast(context, "OTP has been send to you phone number")
                    }
                )
            } else {
                showShortToast(context, response.message)
            }
        } else {
            showShortToast(context, "Invalid Details")
        }


    }
}

val authApi = AuthService()