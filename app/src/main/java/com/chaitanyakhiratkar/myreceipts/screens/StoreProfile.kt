package com.chaitanyakhiratkar.myreceipts.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.chaitanyakhiownerPhoneNumberratkar.myreceipts.services.authApi
import com.chaitanyakhiratkar.myreceipts.components.common.ButtonCommon

data class User(
    val name: String, val accessType: String
)

@Composable
fun ShowStoreProfile(
    storeName: String,
    contactDetails: String,
    address: String,
    adminUser: String,
    userList: List<User>,
    lastTransactions: Map<String, String>, // Keys: "Month", "Week", "Day"
    onTransactionHistoryClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        // Store Name
        Text(
            text = storeName,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Store Details Section
        StoreDetails(contactDetails, address, adminUser)

        Spacer(modifier = Modifier.height(24.dp))

        // User List Section
        UserList(userList)

        Spacer(modifier = Modifier.height(24.dp))

        // Transactions Summary Section
        TransactionsSummary(lastTransactions)

        Spacer(modifier = Modifier.height(16.dp))

        // Transaction History CTA
        Button(onClick = onTransactionHistoryClick) {
            Text(text = "View Transaction History")
        }
    }
}

@Composable
fun StoreDetails(contactDetails: String, address: String, adminUser: String) {
    Text(
        text = "Store Details",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = "Contact: $contactDetails")
    Text(text = "Address: $address")
    Text(text = "Admin User: $adminUser")
}

@Composable
fun UserList(userList: List<User>) {
    Text(
        text = "User List",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold
    )
    Spacer(modifier = Modifier.height(8.dp))
    LazyColumn(
        modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(userList) { user ->
            Text(text = "${user.name} - ${user.accessType}")
        }
    }
}

@Composable
fun TransactionsSummary(lastTransactions: Map<String, String>) {
    Text(
        text = "Transactions Summary",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold
    )
    Spacer(modifier = Modifier.height(8.dp))
    lastTransactions.forEach { (period, amount) ->
        Text(text = "$period: $amount")
    }
}


@Composable
fun StoreProfile(navController: NavHostController) {
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        ShowStoreProfile(
            storeName = "Chaitanya's Store",
            contactDetails = "+91 70578 68697",
            address = "123 Main Street, Pune, India",
            adminUser = "Chaitanya Khiratkar",
            userList = listOf(
                User("Alice", "Manager"), User("Bob", "Cashier"), User("Eve", "Inventory Specialist")
            ),
            lastTransactions = mapOf(
                "Last Month" to "₹50,000", "Last Week" to "₹12,000", "Last Day" to "₹3,000"
            ),
            onTransactionHistoryClick = {
                navController.navigate("transaction_history")
            }
        )
        ButtonCommon(text = "Logout", loading = isLoading) {
            authApi.handleLogOut(navController, context = context)
        }
    }

}