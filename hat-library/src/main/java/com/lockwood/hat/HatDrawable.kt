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
import androidx.annotation.ColorInt
import androidx.annotation.Px
import com.lockwood.hat.extensions.tint

class HatDrawable private constructor(
    private val drawable: Drawable,
    private val width: Int,
    private val height: Int,
    private val padding: Int
) : Drawable() {

    companion object {

        const val PADDING_DEFAULT = 0
        const val TINT_DEFAULT = 0

        private const val DEFAULT_OPACITY = PixelFormat.TRANSLUCENT
    }

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

    override fun getIntrinsicWidth(): Int {
        return width
    }

    override fun getIntrinsicHeight(): Int {
        val halfHeight = height / 2
        return (halfHeight) + padding
    }

    override fun getOpacity(): Int {
        return DEFAULT_OPACITY
    }

    data class Builder(
        var textView: TextView? = null,
        var width: Int? = null,
        var height: Int? = null,
        var padding: Int = PADDING_DEFAULT,
        var tint: Int = TINT_DEFAULT
    ) {

        fun padding(
            @Px paddingToSet: Int
        ): Builder {
            return apply { padding = paddingToSet }
        }

        fun tint(
            @ColorInt tintToSet: Int
        ): Builder {
            return apply { tint = tintToSet }
        }

        fun build(
            textView: TextView,
            drawable: Drawable
        ): HatDrawable {

            val firstChar = textView.text?.firstOrNull()

            if (firstChar != null) {

                with(drawable) {
                    val intrinsicWidth = intrinsicWidth.toFloat()
                    val intrinsicHeight = intrinsicHeight.toFloat()

                    val aspectRatio = intrinsicHeight / intrinsicWidth

                    val charToMeasure = firstChar.toString()
                    val firstCharWidth = measureCharWidth(textView, charToMeasure)

                    width = firstCharWidth
                    height = (requireNotNull(width) * aspectRatio).toInt()
                }
            } else {

                with(drawable) {

                    width = intrinsicWidth
                    height = intrinsicHeight
                }
            }

            val tintDrawable = if (tint != TINT_DEFAULT) {
                drawable.tint(tint)
            } else {
                drawable
            }

            return HatDrawable(
                drawable = tintDrawable,
                width = requireNotNull(width),
                height = requireNotNull(height),
                padding = padding
            )
        }

        // measure char width based on textView textPaint
        private fun measureCharWidth(
            textView: TextView,
            char: String
        ): Int {
            val textBounds = Rect()
            val textPaint = textView.paint
            textPaint.getTextBounds(char, 0, 1, textBounds)

            val charWidth = textPaint.measureText(char)
            return charWidth.toInt()
        }
    }

}

