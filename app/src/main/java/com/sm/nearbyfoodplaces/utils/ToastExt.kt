package com.sm.nearbyfoodplaces.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context?.showToast(@StringRes resId: Int) {
    showToast(this?.getString(resId))
}

fun Context?.showToast(message: String?) {
    this?.let { Toast.makeText(it, message, Toast.LENGTH_SHORT).show() }
}