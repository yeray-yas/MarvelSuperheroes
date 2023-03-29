package com.yerayyas.marvelsuperheroes.core.utls.inline_functions

import android.content.Intent
import android.os.Build
import android.os.Parcelable

/**
 * Created by Yeray YAS on 3/29/2023
 *
 */

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}