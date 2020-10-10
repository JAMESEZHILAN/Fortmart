package com.base.app.utils

import android.content.Context
import android.content.DialogInterface
import android.os.Build
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.base.app.R
import com.base.app.model.ErrorResponse

val defaultAlertButtonAction = { d: DialogInterface, _: Int -> d.dismiss() }

fun showAlert(
    context: Context,
    message: String = "Something went wrong",
    positiveLabel: String = "Ok",
    negativeLabel: String = "",
    positiveButtonColor: Int = R.color.colorBlue,
    negativeButtonColor: Int = R.color.colorRed,
    positiveButtonAction: (DialogInterface, Int) -> Unit = defaultAlertButtonAction,
    negativeButtonAction: (DialogInterface, Int) -> Unit = defaultAlertButtonAction
) {
    val builder = AlertDialog.Builder(context)
    val alert = builder.setMessage(message)
        .setPositiveButton(positiveLabel, positiveButtonAction)
        .setNegativeButton(negativeLabel, negativeButtonAction)
        .create()
    alert.setOnShowListener {
        alert.getButton(AlertDialog.BUTTON_POSITIVE).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                setTextAppearance(R.style.AlertDialogButton)
            }
            setTextColor(ContextCompat.getColor(context, positiveButtonColor))
        }
        alert.getButton(AlertDialog.BUTTON_NEGATIVE).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                setTextAppearance(R.style.AlertDialogButton)
            }
            setTextColor(ContextCompat.getColor(context, negativeButtonColor))
        }
    }
    alert.show()
}

fun errorAlertObserver(
    context: Context,
    positiveAction: (DialogInterface, Int) -> Unit = defaultAlertButtonAction,
    negativeAction: (DialogInterface, Int) -> Unit = defaultAlertButtonAction
): Observer<EventWrapper<ErrorResponse?>> {
    return Observer<EventWrapper<ErrorResponse?>> {
        it.nullifyIfHandled()?.let { errorResponse ->
            showAlert(
                context = context,
                message = errorResponse.error.message,
                positiveButtonAction = positiveAction,
                negativeButtonAction = negativeAction
            )
        }
    }
}