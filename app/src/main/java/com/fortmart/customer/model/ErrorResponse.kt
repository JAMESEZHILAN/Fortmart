package com.fortmart.customer.model

import com.fortmart.customer.shared.Constants.DEFAULT_INT
import com.fortmart.customer.shared.Constants.DEFAULT_STR

data class ErrorResponse(
    var error: Error = Error()
)

data class Error(
    var statusCode: Int = DEFAULT_INT,
    var name: String = DEFAULT_STR,
    var message: String = DEFAULT_STR
)