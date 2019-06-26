package com.sunil.kumar.util

import com.google.gson.Gson

fun String?.isNotEmpty(): Boolean {
    return this?.length ?: 0 > 0
}

fun Any.getJson(): String? {
    return Gson().toJson(this)
}

fun <T> String.toClassData(classz: Class<T>): T? {
    return Gson().fromJson(this, classz)
}

fun Boolean?.isTrue(): Boolean {
    return this == true
}

fun Boolean?.isFalse(): Boolean {
    return this == null || this == false
}

fun <T1 : Any, T2 : Any, R : Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}
