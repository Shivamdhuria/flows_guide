package com.example.flows.extensions

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import kotlin.coroutines.CoroutineContext

fun ViewGroup.inflate(@LayoutRes layout: Int): View {
    return LayoutInflater.from(context).inflate(layout, this, false)
}

/**
 * Helps log information about coroutines.
 */

fun logCoroutine(methodName: String, coroutineContext: CoroutineContext) {
    Log.e(
        "TestCoroutine", "Thread for $methodName is: ${Thread.currentThread().name}" +
                "and the context is $coroutineContext"
    )
}

fun logCoroutineThreadNameOnly(methodName: String) {
    Log.e(
        "TestCoroutine", "Thread for $methodName is: ${Thread.currentThread().name}"
    )
}