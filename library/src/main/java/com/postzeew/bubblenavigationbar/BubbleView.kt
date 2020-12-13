package com.postzeew.bubblenavigationbar

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.postzeew.bubblenavigationbar.databinding.BubbleViewBinding

class BubbleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding = BubbleViewBinding.inflate(LayoutInflater.from(context), this)
    private val animator = BubbleViewAnimator(binding.titleTextView)

    val titleTextView: TextView
        get() = binding.titleTextView

    lateinit var onClickListener: (View) -> Unit

    init {
        background = ContextCompat.getDrawable(context, R.drawable.bg_bubble_view)
        gravity = Gravity.CENTER_VERTICAL
        orientation = HORIZONTAL

        val padding = resources.getDimensionPixelSize(R.dimen.padding_regular)
        setPadding(padding, padding, 0, padding)

        setOnClickListener {
            onClickListener.invoke(this)
            animator.switchState()
        }
    }

    fun bind(data: Data) {
        with(binding.titleTextView) {
            text = context.getString(data.titleResId)
            setTextColor(ContextCompat.getColor(context, data.titleColorResId))
        }

        with(binding.iconImageView) {
            setImageDrawable(ContextCompat.getDrawable(context, data.iconResId))
            setColorFilter(ContextCompat.getColor(context, data.iconColorResId))
        }
    }

    data class Data(
        @StringRes val titleResId: Int,
        @ColorRes val titleColorResId: Int,
        @DrawableRes val iconResId: Int,
        @ColorRes val iconColorResId: Int
    )
}