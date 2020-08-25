package com.example.flows.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.res.use

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

@ColorInt
@SuppressLint("Recycle")
fun Context.themeColor(
    @AttrRes themeAttrId: Int
): Int {
    return obtainStyledAttributes(
        intArrayOf(themeAttrId)
    ).use {
        it.getColor(0, Color.MAGENTA)
    }
}

// fun View.getActivity(): AppCompatActivity? {
//     var context = this.context
//     while (context is ContextWrapper) {
//         if (context is AppCompatActivity) {
//             return context
//         }
//         context = context.baseContext
//     }
//     return null
// }
//
// fun SearchView.showKeyboard() {
//     if (requestFocus()) {
//         (getActivity()?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
//             .showSoftInput(this, SHOW_IMPLICIT)
//     }
// }