package com.lockwood.hat.extensions

import android.view.View

internal fun View.updateView() {
    invalidate()
    requestLayout()
}