package com.chaitanyakhiratkar.myreceipts.utils

import android.widget.Toast

fun showShortToast(context: android.content.Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}