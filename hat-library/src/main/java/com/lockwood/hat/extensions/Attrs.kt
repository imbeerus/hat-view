package com.lockwood.hat.extensions

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.getResourceIdOrThrow

internal inline fun fetchAttrs(
    context: Context,
    attrs: IntArray,
    set: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0,
    fetch: TypedArray .() -> Unit = {}
) {
    val typedArray = context.theme.obtainStyledAttributes(
        set,
        attrs,
        defStyleAttr,
        defStyleRes
    )
    with(typedArray) {
        try {
            fetch()
        } finally {
            recycle()
        }
    }
}

internal inline fun fetchAndroidAttrs(
    context: Context,
    vararg attrs: Int,
    set: AttributeSet? = null,
    fetch: TypedArray .() -> Unit = {}
) {
    val typedArray = context.obtainStyledAttributes(set, attrs)
    with(typedArray) {
        try {
            fetch()
        } finally {
            recycle()
        }
    }
}

internal fun TypedArray.getCompatDrawable(
    context: Context,
    drawableIndex: Int
): Drawable? {
    return try {
        val drawableResId = getResourceIdOrThrow(drawableIndex)
        AppCompatResources.getDrawable(context, drawableResId)
    } catch (e: Exception) {
        Log.e("TypedArray", e.message.toString())
        null
    }
}