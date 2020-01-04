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

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.annotation.Px

class HatDrawable private constructor(
    private val drawable: Drawable,
    private val width: Int,
    private val height: Int,
    private val padding: Int
) : Drawable() {

    override fun draw(canvas: Canvas) {
        drawable.draw(canvas)
    }

    override fun onBoundsChange(bounds: Rect) {
        val left = bounds.left
        val top = bounds.top
        val bottom = top + height
        val right = left + intrinsicWidth
        val drawableBounds = Rect(left, top, right, bottom)
        drawable.bounds = drawableBounds
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        drawable.colorFilter = colorFilter
    }

    override fun setAlpha(alpha: Int) {
        drawable.alpha = alpha
    }

    override fun getIntrinsicWidth(): Int = width

    override fun getIntrinsicHeight(): Int = (height shr 1) + padding

    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

    data class Builder(
        var textView: TextView? = null,
        var width: Int? = null,
        var height: Int? = null,
        var padding: Int = 0
    ) {
        fun padding(@Px padding: Int) = apply { this.padding = padding }

        fun build(textView: TextView, drawable: Drawable): HatDrawable {
            val firstChar = textView.text?.firstOrNull()
            if (firstChar != null) {
                val dWidth = drawable.intrinsicWidth.toFloat()
                val dHeight = drawable.intrinsicHeight.toFloat()
                val aspectRatio =  dHeight / dWidth
                width = textView.measureCharWidth(firstChar.toString())
                height = (width!! * aspectRatio).toInt()
            } else {
                width = drawable.intrinsicWidth
                height = drawable.intrinsicHeight
            }
            return HatDrawable(drawable, width!!, height!!, padding)
        }

        // measure char width based on textView textPaint
        private fun TextView.measureCharWidth(char: String): Int {
            val textBounds = Rect()
            val textPaint = paint.apply {
                isAntiAlias = true
                textSize = this.textSize
                typeface = this.typeface
            }
            textPaint.getTextBounds(char, 0, 1, textBounds)
            return textPaint.measureText(char).toInt()
        }
    }

}

