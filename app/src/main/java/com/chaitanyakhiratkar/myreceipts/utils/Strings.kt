package com.chaitanyakhiratkar.myreceipts.utils


// Validates the phone number is valid or not
fun isValidPhoneNumberString(phoneNumber: String): Boolean {
    // Regex for validating phone numbers (10 digits)
    val phoneNumberPattern = "^\\+?[0-9]{10,15}$"
    return phoneNumber.matches(Regex(phoneNumberPattern))
}

fun isValidOtpString(otp: String): Boolean {
    // Regex for validating a 6-digit OTP
    val otpPattern = "^[0-9]{6}$"
    return otp.matches(Regex(otpPattern))
}