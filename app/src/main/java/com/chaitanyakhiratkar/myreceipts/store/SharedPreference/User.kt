package com.chaitanyakhiratkar.myreceipts.store.SharedPreference

import android.content.Context
import com.chaitanyakhiratkar.myreceipts.constants.SharePreferenceStores
import com.chaitanyakhiratkar.myreceipts.utils.SharedPreferencesUtils

fun userStore(context: Context): SharedPreferencesUtils {
    return SharedPreferencesUtils(context, SharePreferenceStores.USER)
}
