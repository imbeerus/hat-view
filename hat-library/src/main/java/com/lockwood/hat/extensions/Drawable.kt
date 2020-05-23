package com.lockwood.hat.extensions

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.DrawableCompat

internal fun Drawable.tint(@ColorInt tint: Int): Drawable {
    val wrappedDrawable = DrawableCompat.wrap(this)
    val mutableDrawable = wrappedDrawable.mutate()
    DrawableCompat.setTint(mutableDrawable, tint)
    return mutableDrawable
}