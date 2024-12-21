package com.chaitanyakhiratkar.myreceipts.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.chaitanyakhiownerPhoneNumberratkar.myreceipts.services.authApi
import com.chaitanyakhiratkar.myreceipts.components.common.ButtonCommon
import com.chaitanyakhiratkar.myreceipts.components.common.InputCommon
import com.chaitanyakhiratkar.myreceipts.constants.Routes
import com.chaitanyakhiratkar.myreceipts.constants.Stacks
import com.chaitanyakhiratkar.myreceipts.services.APIResponse
import com.chaitanyakhiratkar.myreceipts.store.SharedPreference.userStore
import com.chaitanyakhiratkar.myreceipts.utils.isValidPhoneNumberString
import com.chaitanyakhiratkar.myreceipts.utils.showShortToast

@Composable
fun Login(navController: NavHostController) {
    val context = LocalContext.current

    // State variables for phone number and loading status
    var phoneNumber by remember { mutableStateOf("7057868697") }
    var password by remember { mutableStateOf("password") }
    var isLoading by remember { mutableStateOf(false) }

    // Main container for the Login screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Header Section
        LoginHeader()

        Spacer(modifier = Modifier.height(16.dp))

        // Phone Number Input Field
        InputCommon(text = phoneNumber, onChange = { phoneNumber = it })
        InputCommon(text = password, onChange = { password = it })

        Spacer(modifier = Modifier.height(16.dp))

        // Button to request OTP
        ButtonCommon(text = "Login", loading = isLoading, onClick = {
            isLoading = true
            authApi.handleLogin(
                phoneNumber, password , navController, context
            )
            isLoading = false
        })

        Spacer(modifier = Modifier.height(16.dp))

        // Register Text Button
        RegisterTextButton(navController)

        // Terms and Conditions Text Button
        TermsAndConditionsTextButton()
    }
}

@Composable
fun LoginHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // App Name
        Text(
            text = "My Receipts",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(10.dp)
        )

        // Login Title
        Text(
            text = "Login",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(10.dp)
        )
    }
}

@Composable
fun RegisterTextButton(navController: NavHostController) {
    TextButton(
        onClick = { navController.navigate(Routes.REGISTER) }, modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "New Here? Register",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun TermsAndConditionsTextButton() {
    TextButton(
        onClick = { /* TODO: Navigate to Terms and Conditions */ },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "** Terms and Conditions",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
    }
}