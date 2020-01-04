package com.lockwood.hatdemo

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.SeekBar
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main), SeekBar.OnSeekBarChangeListener {

    private val hatDrawables = arrayOf(
        R.drawable.ic_cap,
        R.drawable.ic_crown,
        R.drawable.ic_hat_mexico,
        R.drawable.ic_hat_pirate,
        R.drawable.ic_hat_regular,
        R.drawable.ic_hat_rus,
        R.drawable.ic_hat_samurai,
        R.drawable.ic_hat_santa,
        R.drawable.ic_hat_student,
        R.drawable.ic_hat_witch
    )
    private val seekBars = arrayOf(R.id.textSizeBar, R.id.paddingBar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        random.setOnClickListener { setRandomHat() }
        seekBars.forEach { id -> findViewById<SeekBar>(id).setOnSeekBarChangeListener(this) }
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) =
        with(test) {
            when (seekBar.id) {
                R.id.textSizeBar -> textSize = progress.toFloat()
                R.id.paddingBar -> hatPadding = progress
            }
            updateHat()
        }

    override fun onStartTrackingTouch(seekBar: SeekBar) = Unit
    override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit

    private fun Context.drawable(@DrawableRes res: Int): Drawable? =
        ContextCompat.getDrawable(this, res)

    private fun setRandomHat() {
        test.hat = drawable(hatDrawables.random())
    }

}
