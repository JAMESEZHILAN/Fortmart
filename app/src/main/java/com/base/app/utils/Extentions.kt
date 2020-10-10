package com.base.app.utils

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.base.app.MainActivity
import com.base.app.enums.RetrofitStatus
import java.text.DecimalFormat
import kotlin.math.roundToInt

open class EventWrapper <out T> (val data: T){

    var isEventHandled = false

    fun nullifyIfHandled(): T?{
        return if(isEventHandled){
            null
        }else{
            isEventHandled = true
            data
        }
    }
}

fun View.hide(){
    visibility = View.GONE
}

fun View.show(){
    visibility = View.VISIBLE
}
fun View.hideAndKeep() {
    visibility = View.INVISIBLE
}

fun toSingleDecimal(double: Double?): String {
    return DecimalFormat("#.#").format(String.format("%.1f", double ?: 0.0).toDouble())
}
@BindingAdapter("text")
fun setText(view: TextView, text: String?) {
    view.text = text
}


fun Fragment.setLoader(status: RetrofitStatus) = (activity as MainActivity).showOrHideLoader(status)