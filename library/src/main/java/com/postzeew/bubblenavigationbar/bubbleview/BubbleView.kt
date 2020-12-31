package com.postzeew.bubblenavigationbar.bubbleview

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.postzeew.bubblenavigationbar.R
import com.postzeew.bubblenavigationbar.databinding.BubbleViewBinding

private const val DISAPPEARING_ANIMATION_DURATION = 150L

internal class BubbleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding = BubbleViewBinding.inflate(LayoutInflater.from(context), this)
    private val animator = BubbleViewAnimator(binding.titleTextView) {
        resetBackground(withAnimation = true)
    }

    private val translucentColor = ContextCompat.getColor(context, R.color.translucent_color)
    private val transparentColor = ContextCompat.getColor(context, R.color.transparent_color)

    private val disappearingAnimator = ValueAnimator.ofObject(ArgbEvaluator(), translucentColor, transparentColor).apply {
        duration = DISAPPEARING_ANIMATION_DURATION
        addUpdateListener { animator -> changeBackgroundColor(animator.animatedValue as Int) }
    }

    val titleTextView: TextView
        get() = binding.titleTextView

    lateinit var onClickListener: (BubbleView) -> Unit

    init {
        background = ContextCompat.getDrawable(context, R.drawable.bg_bubble_view)
        gravity = Gravity.CENTER_VERTICAL
        orientation = HORIZONTAL

        val padding = resources.getDimensionPixelSize(R.dimen.padding_regular)
        setPadding(padding, padding, 0, padding)

        setOnClickListener {
            onClickListener.invoke(this)
        }
    }

    fun bind(item: BubbleViewItem) {
        with(binding.titleTextView) {
            text = context.getString(item.titleResId)
            setTextColor(ContextCompat.getColor(context, item.titleColorResId))
        }

        with(binding.iconImageView) {
            setImageDrawable(ContextCompat.getDrawable(context, item.iconResId))
            setColorFilter(ContextCompat.getColor(context, item.iconColorResId))
        }

        if (item.isActive) {
            animator.expandWithoutAnimation()
            setBackground()
        } else {
            resetBackground(withAnimation = false)
        }
    }

    fun switchState(isActive: Boolean) {
        disappearingAnimator.cancel()

        if (!isActive) {
            setBackground()
        }
        animator.switchState()
    }

    private fun setBackground() {
        changeBackgroundColor(translucentColor)
    }

    private fun resetBackground(withAnimation: Boolean) {
        if (withAnimation) {
            disappearingAnimator.start()
        } else {
            changeBackgroundColor(transparentColor)
        }
    }

    private fun changeBackgroundColor(@ColorInt color: Int) {
        background = (background as GradientDrawable).apply {
            setColor(color)
        }
    }
}