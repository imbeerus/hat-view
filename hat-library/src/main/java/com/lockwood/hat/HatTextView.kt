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
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.updatePadding
import androidx.core.widget.addTextChangedListener
import com.lockwood.hat.extensions.fetchAndroidAttrs
import com.lockwood.hat.extensions.fetchAttrs
import com.lockwood.hat.extensions.getCompatDrawable
import com.lockwood.hat.extensions.updateView

@SuppressLint("Recycle")
open class HatTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textViewStyle
) : AppCompatTextView(context, attrs, defStyleAttr) {

    companion object {

        val TAG: String = HatTextView::class.java.simpleName

        private const val DEFAULT_HAT_PADDING = 0
        private const val DEFAULT_HAT_LIKE_TEXT_COLOR = false
    }

    var hat: Drawable? = null
        set(value) {
            field = value
            updateHat()
        }

    var hatPadding: Int = DEFAULT_HAT_PADDING
        set(value) {
            field = value
            updateHat()
        }

    private val textStartPadding: Int
        get() {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                paddingStart
            } else {
                paddingLeft
            }
        }

    private val textEndPadding: Int
        get() {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                paddingEnd
            } else {
                paddingRight
            }
        }

    private var initPaddingTop: Int = DEFAULT_HAT_PADDING

    private var hatToDraw: HatDrawable? = null

    private var hatLikeTextColor: Boolean = DEFAULT_HAT_LIKE_TEXT_COLOR

    init {
        fetchAndroidAttrs(context, android.R.attr.paddingTop, set = attrs) {
            initPaddingTop = getDimensionPixelSize(0, 0)
        }

        fetchAttrs(context, R.styleable.HatTextView, set = attrs, defStyleAttr = defStyleAttr) {
            hat = getCompatDrawable(
                context,
                R.styleable.HatTextView_hat
            )
            hatPadding = getDimensionPixelSize(
                R.styleable.HatTextView_hatPadding,
                HatDrawable.PADDING_DEFAULT
            )
            hatLikeTextColor = getBoolean(
                R.styleable.HatTextView_hatLikeTextColor,
                DEFAULT_HAT_LIKE_TEXT_COLOR
            )
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
        val hastText = !text.isNullOrEmpty()
        val hastHat = hat != null

        val tintColor = if (hatLikeTextColor) {
            currentTextColor
        } else {
            HatDrawable.TINT_DEFAULT
        }

        if (hastText && hastHat) {

            hatToDraw = HatDrawable.Builder()
                .padding(hatPadding)
                .tint(tintColor)
                .build(this, requireNotNull(hat))

            val hatPadding = initPaddingTop + requireNotNull(hatToDraw).intrinsicHeight

            updatePadding(
                left = textStartPadding,
                top = hatPadding,
                right = textEndPadding,
                bottom = paddingBottom
            )

            updateView()
            onSizeChanged()
        }
    }

    private fun onSizeChanged() {
        onSizeChanged(width, height, 0, 0)
    }

}



