package com.gasparaiciukas.owncommunity.presentation.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import com.gasparaiciukas.owncommunity.presentation.main.MainActivity

val Context.activity: Activity get() {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("No activity found...?")
}

val Context.componentActivity: ComponentActivity
    get() {
        var context = this
        while (context is ContextWrapper) {
            if (context is ComponentActivity) return context
            context = context.baseContext
        }
        throw IllegalStateException("No activity found...?")
    }

val Context.mainActivity: MainActivity
    get() {
        var context = this
        while (context is ContextWrapper) {
            if (context is MainActivity) return context
            context = context.baseContext
        }
        throw IllegalStateException("No activity found...?")
    }
