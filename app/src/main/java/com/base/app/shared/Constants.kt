package com.base.app.shared

object Constants {

    const val USER_CREDENTIALS = "userCredentials"
    const val LOGGED_IN = "isLoggedIn"
    const val TOKEN = "token"
    const val PHONE = "phone"
    const val COUNTRY_CODE = "countryCode"
    const val EMAIL = "email"
    const val USER_DETAILS = "userDetails"
    const val PASSWORD_CREATE = "passwordCreation"
    const val AUTHORIZATION = "Authorization"
    const val CONNECTION = "Connection"
    const val TOKEN_PREFIX = "bearer "

    const val DEFAULT_STR = ""
    const val DEFAULT_INT = 0
    const val DEFAULT_DBL = 0.0
    const val DEFAULT_LNG = 0L

    const val DoubleZeroPadding = "%02d"

    val phoneNumberPattern = Regex("^[0-9]{1,15}")
}