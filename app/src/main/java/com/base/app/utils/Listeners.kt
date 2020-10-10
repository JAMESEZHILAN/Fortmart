package com.base.app.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.base.app.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.net.InetSocketAddress
import java.net.Socket


class OtpTextChangeListeners(private val current: EditText) : TextWatcher {

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (s != null && s.length == 1) try {
            current.focusSearch(View.FOCUS_RIGHT).requestFocus()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun afterTextChanged(s: Editable?) {}
}


val otpKeyListener = View.OnKeyListener { view, keyCode, event ->
    val current = view as EditText
    try {
        if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && current.text.isEmpty()) {
            val prev = current.focusSearch(View.FOCUS_LEFT) as EditText
            prev.setText("")
            prev.requestFocus()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    false
}


class PasswordTouchListener(val context: Context) : View.OnTouchListener {
    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        view?.performClick()
        if (event?.action == MotionEvent.ACTION_UP) {
            val textLocation = IntArray(2)
            view?.getLocationOnScreen(textLocation)
            if (event.rawX >= textLocation[0] + (view?.width
                    ?: 0) - ((view as EditText?)?.totalPaddingRight ?: 0)
            ) {
                if (view?.transformationMethod == null) {
                    view?.transformationMethod = PasswordTransformationMethod()
                    view?.setCompoundDrawablesWithIntrinsicBounds(
                        android.R.drawable.ic_delete,
                        0,
                        android.R.drawable.ic_delete,
                        0
                    )
                } else {
                    view?.transformationMethod = null
                    view?.setCompoundDrawablesWithIntrinsicBounds(
                        android.R.drawable.ic_delete,
                        0,
                        android.R.drawable.ic_delete,
                        0
                    )
                }
                view?.setSelection(view.length())
            } else {
                view?.requestFocus()
                ContextCompat.getSystemService(
                    context,
                    InputMethodManager::class.java
                )?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            }
        }
        return true
    }
}

class FieldSubmitListener(private val submitAction: ()->Unit): TextView.OnEditorActionListener {
    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        return when (actionId) {
            EditorInfo.IME_ACTION_DONE -> {
                submitAction.invoke()
                true
            }
            else -> false
        }
    }
}


fun checkConnectionAsync(coroutineScope: CoroutineScope): Deferred<Boolean> {
    return coroutineScope.async(Dispatchers.IO) {
        return@async try {
            val sock = Socket()
            sock.connect(InetSocketAddress("8.8.8.8", 53), 2700)
            sock.close()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}

class ListScrollListener(
    private var linearLayoutManager: LinearLayoutManager,
    private val loadMore: () -> Unit
) :
    RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy <= 0) return
        if (linearLayoutManager.findFirstVisibleItemPosition() + linearLayoutManager.childCount >= linearLayoutManager.itemCount) {
            loadMore.invoke()
        }
    }
}


object ConnectionListener : BroadcastReceiver() {

    var mConnectivityListener : ConnectivityListener? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        if(mConnectivityListener!=null)
            mConnectivityListener?.onConnectionChange(isConnectedOrConnecting(context!!))
    }

    private fun isConnectedOrConnecting(context : Context) : Boolean{
        val mNetworkInfo = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        return mNetworkInfo!=null && mNetworkInfo.isConnectedOrConnecting
    }

    interface ConnectivityListener{
        fun onConnectionChange(isConnected : Boolean)
    }
}