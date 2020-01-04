/*
 * Copyright (C) 2020 Ivan Zinovyev (https://github.com/lndmflngs)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lockwood.hat

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.res.getResourceIdOrThrow
import androidx.core.widget.addTextChangedListener

@SuppressLint("Recycle")
open class HatTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    var hat: Drawable? = null
        set(value) {
            field = value
            updateHat()
        }

    var hatPadding = 0
        set(value) {
            field = value
            updateHat()
        }

    private var textStartPadding =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            paddingStart
        } else {
            paddingLeft
        }

    private var textEndPadding =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            paddingEnd
        } else {
            paddingRight
        }

    private var initPaddingTop = 0
    private var hatToDraw: HatDrawable? = null

    init {
        val set = intArrayOf(
            android.R.attr.paddingTop
        )
        context.obtainStyledAttributes(attrs, set).apply {
            try {
                initPaddingTop = getDimensionPixelSize(0, 0)
            } finally {
                recycle()
            }
        }

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.HatTextView,
            defStyleAttr,
            0
        ).apply {
            try {
                hat = getCompatDrawable(R.styleable.HatTextView_hat)
                hatPadding = getDimensionPixelSize(R.styleable.HatTextView_hatPadding, 0)
            } finally {
                recycle()
            }
        }
        updateHat()
        addTextChangedListener { updateHat() }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hatToDraw?.draw(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        hatToDraw?.setBounds(textStartPadding, initPaddingTop, w, h)
    }

    fun updateHat() {
        if (!text.isNullOrEmpty() && hat != null) {
            hatToDraw = HatDrawable.Builder().padding(hatPadding).build(this, hat!!)
            setPadding(
                textStartPadding,
                initPaddingTop + hatToDraw!!.intrinsicHeight,
                textEndPadding,
                paddingBottom
            )
            invalidate()
            requestLayout()
            onSizeChanged(width, height, 0, 0)
        }
    }

    private fun TypedArray.getCompatDrawable(drawableIndex: Int): Drawable? {
        return try {
            val drawableResId = getResourceIdOrThrow(drawableIndex)
            AppCompatResources.getDrawable(context, drawableResId)
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            null
        }
    }

    companion object {

        val TAG: String = HatTextView::class.java.simpleName
    }

}



