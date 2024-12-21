package com.chaitanyakhiratkar.myreceipts.services;

import java.security.MessageDigest

// USER DATA

data class UserModel(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val token: String?,
    val memberCode: String?,
    val memberPin: String,
    val storeId: String
)

data class StoreModel(
    val id: String,
    val name: String,
    val fullAddress: String,
    val city: String,
    val pincode: String,
    val phoneNumber: String?,
    val gstIn: String,
    val adminId: String,
    val rootPassword: String, // Store hashed password
    val token: String?, // unique random key stored at frontend
)

data class APIResponse(
    val data: Any?,
    val status: Int,
    val message: String,
)

fun hashPassword(password: String): String {
    val digest = MessageDigest.getInstance("SHA-256")
    val hashBytes = digest.digest(password.toByteArray())
    return hashBytes.joinToString("") { "%02x".format(it) } // Convert bytes to hex
}

// DB DATA

val STORE_LIST: MutableList<StoreModel> = mutableListOf(
    StoreModel(
        id = "s1",
        name = "Starbucks",
        fullAddress = "123 Main St",
        city = "New York",
        pincode = "10001",
        phoneNumber = "7972581488",
        gstIn = "GST123456789",
        adminId = "u1",
        rootPassword = hashPassword("password"),
        token = "token123"
    ), StoreModel(
        id = "s2",
        name = "Burger King",
        fullAddress = "456 Elm St",
        city = "Los Angeles",
        pincode = "90001",
        phoneNumber = "9876543210",
        gstIn = "GST987654321",
        adminId = "u3",
        rootPassword = hashPassword("password3"),
        token = "token456"
    ), StoreModel(
        id = "s3",
        name = "Dominos",
        fullAddress = "789 Oak St",
        city = "Chicago",
        pincode = "60601",
        phoneNumber = "1122334455",
        gstIn = "GST112233445",
        adminId = "u5",
        rootPassword = hashPassword("password2"),
        token = "token789"
    )
)

val USER_LIST: MutableList<UserModel> = mutableListOf(
    UserModel("u1", "User A", "7057868690", "tokenA", "codeA", "1234", "s1"),
    UserModel("u2", "User B", "5559876543", "tokenB", "codeB", "5678", "s1"),
    UserModel("u3", "User C", "5555555555", "tokenC", "codeC", "91011", "s2"),
    UserModel("u4", "User D", "5554444444", "tokenD", "codeD", "1213", "s3"),
    UserModel("u5", "User E", "5553333333", "tokenE", "codeE", "1415", "s3")
)