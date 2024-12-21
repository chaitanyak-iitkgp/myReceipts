package com.chaitanyakhiratkar.myreceipts.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

data class HomeOption(
    val title: String, val route: String
)

@Composable
fun Home(navController: NavHostController) {
    // JSON-like list of home options
    val homeOptions = listOf(
        HomeOption("View Profile", "profile"),
        HomeOption("View Transactions", "transactions"),
        HomeOption("Add Product", "add_product"),
        HomeOption("Update Product", "update_product"),
        HomeOption("Show Summary", "summary"),
        HomeOption("Start a Bill", "start_bill"),
        HomeOption("Bill History", "bill_history"),
        HomeOption("Add User", "add_user"),
        HomeOption("User List", "user_list")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Welcome Manager",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Dynamically render buttons for each option
        homeOptions.forEach { option ->
            HomeOptionButton(option.title) {
                navController.navigate(option.route)
            }
        }
    }
}

@Composable
fun HomeOptionButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick, modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text(text = text, style = MaterialTheme.typography.bodyLarge)
    }
}
