package com.base.app.model

import com.base.app.shared.Constants.DEFAULT_INT
import com.base.app.shared.Constants.DEFAULT_STR

data class ErrorResponse(
    var error: Error = Error()
)

data class Error(
    var statusCode: Int = DEFAULT_INT,
    var name: String = DEFAULT_STR,
    var message: String = DEFAULT_STR
)