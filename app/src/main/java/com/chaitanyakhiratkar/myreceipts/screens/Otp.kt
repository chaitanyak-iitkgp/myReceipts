package com.chaitanyakhiratkar.myreceipts.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.chaitanyakhiratkar.myreceipts.components.common.ButtonCommon
import com.chaitanyakhiratkar.myreceipts.utils.isValidOtpString
import com.chaitanyakhiratkar.myreceipts.utils.otpUtils
import com.chaitanyakhiratkar.myreceipts.utils.showShortToast


@Composable
fun OTP(navController: NavHostController, onSuccess: () -> Unit) {
    // Context for showing Toast messages
    val context = LocalContext.current

    // State variables for OTP input and loading state
    var otp by remember { mutableStateOf("189189") }
    var isLoading by remember { mutableStateOf(false) }

    // Main container for the screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // OTP Input Field
        OTPInputField(otp = otp, onOTPChange = { otp = it })

        Spacer(modifier = Modifier.height(16.dp))

        // Button with Loader to verify OTP
        ButtonCommon(text = "Verify OTP", loading = isLoading, onClick = {
            handleOTPVerification(
                otp = otp, isLoading = { isLoading = it }, onSuccess = onSuccess, context = context
            )
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OTPInputField(otp: String, onOTPChange: (String) -> Unit) {
    TextField(
        value = otp,
        onValueChange = onOTPChange,
        label = { Text("Enter 6 Digit OTP") },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}

private fun handleOTPVerification(
    otp: String,
    isLoading: (Boolean) -> Unit,
    onSuccess: () -> Unit,
    context: android.content.Context
) {
    if (isValidOtpString(otp)) {
        isLoading(true)

        // Simulating OTP verification
        otpUtils.verifyCode(otp, onSuccess = {
            isLoading(false)
            // LoginIn User Here and Set Token
            if (it != null) {
                onSuccess()
            } else {
                showShortToast(context, "Some error occurred. OTP couldn't be verified.")
            }
        }, onFailure = {
            isLoading(false)
            showShortToast(context, "Some error occurred. OTP couldn't be verified.")
        }, onInvalidOTP = {
            isLoading(false)
            showShortToast(context, "Invalid OTP")
        })
    } else {
        showShortToast(context, "Invalid OTP")
    }
}

