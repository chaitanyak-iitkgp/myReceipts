package com.chaitanyakhiratkar.myreceipts.utils

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.auth.PhoneAuthProvider.getCredential
import com.google.firebase.auth.PhoneAuthProvider.verifyPhoneNumber
import java.util.concurrent.TimeUnit

class OTPUtils(private val auth: FirebaseAuth) {

    private var verificationId: String? = null

    fun sendOTP(
        phoneNumber: String,
        onCodeSent: (String) -> Unit,
        onVerificationCompleted: (PhoneAuthCredential) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val callbacks = object : OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                onVerificationCompleted(credential)
            }

            override fun onVerificationFailed(exception: FirebaseException) {
                onFailure(exception)
            }

            override fun onCodeSent(
                verificationId: String, token: ForceResendingToken
            ) {
                this@OTPUtils.verificationId = verificationId
                onCodeSent(verificationId)
            }
        }

        val options = PhoneAuthOptions
            .newBuilder(auth)
            .setPhoneNumber("+91$phoneNumber")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setCallbacks(callbacks)
            .build()

        verifyPhoneNumber(options)
    }

    fun verifyCode(
        code: String,
        onSuccess: (FirebaseUser?) -> Unit,
        onInvalidOTP: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val storedVerificationId = verificationId
        if (storedVerificationId.isNullOrEmpty()) {
            onFailure(IllegalStateException("Verification ID is null or empty."))
            return
        }

        val credential = getCredential(storedVerificationId, code)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess(task.result?.user)
            } else {
                val exception = task.exception
                if (exception is FirebaseAuthInvalidCredentialsException) {
                    onInvalidOTP()
                } else {
                    onFailure(exception ?: Exception("Unknown error occurred"))
                }
            }
        }
    }
}

val otpUtils = OTPUtils(FirebaseAuth.getInstance())
