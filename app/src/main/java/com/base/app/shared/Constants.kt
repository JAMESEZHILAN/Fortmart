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

    val phoneNumberPattern = Regex("[0-9a-zA-Z]+@[0-9a-zA-Z]+\\.+[0-9a-zA-Z]+$")
    val emailPattern = Regex("^[0-9]{1,15}")

    var token: String? = ""
    var temp = "bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIzbUdNdVJkQ3hUNFJReHJDdFZXZE1SIiwiaWQiOjE5LCJyb2xlSWQiOjEwLCJmaXJzdE5hbWUiOiJ0ZXN0IiwibGFzdE5hbWUiOiJ0ZXN0IiwiaWF0IjoxNTgyMTIwNDE0fQ.m13DWMc-QfIAXuVYGD_R7mRToYSHnOuEvu2Yad9aGiE"
}