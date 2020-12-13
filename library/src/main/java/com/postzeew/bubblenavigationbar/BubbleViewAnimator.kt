package com.postzeew.bubblenavigationbar

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.core.view.updateLayoutParams
import com.postzeew.bubblenavigationbar.BubbleViewAnimationState.*
import com.postzeew.bubblenavigationbar.BubbleViewAnimatorType.COLLAPSE
import com.postzeew.bubblenavigationbar.BubbleViewAnimatorType.EXPAND

private const val ANIMATION_DURATION = 300L

internal class BubbleViewAnimator(
    private val titleTextView: TextView
) {
    private val expandedTitleWidth: Int by lazy {
        calculateTitleExpandedWidth()
    }

    private var state = COLLAPSED
    private var currentTitleWidth = 0

    private var expandAnimator: ValueAnimator? = null
    private var collapseAnimator: ValueAnimator? = null

    fun switchState() {
        when (state) {
            COLLAPSING, COLLAPSED -> expand()
            EXPANDING, EXPANDED -> collapse()
        }
    }

    private fun collapse() {
        expandAnimator?.cancel()

        collapseAnimator = createAnimator(animatorType = COLLAPSE).also { animator ->
            animator.start()
        }
    }

    private fun expand() {
        collapseAnimator?.cancel()

        expandAnimator = createAnimator(animatorType = EXPAND).also { animator ->
            animator.start()
        }
    }

    private fun createAnimator(animatorType: BubbleViewAnimatorType): ValueAnimator {
        val endWidth = when (animatorType) {
            EXPAND -> expandedTitleWidth
            COLLAPSE -> 0
        }

        val currentTitleWidthProportion = currentTitleWidth.toFloat() / expandedTitleWidth

        val targetDuration = when (animatorType) {
            EXPAND -> (1 - currentTitleWidthProportion) * ANIMATION_DURATION
            COLLAPSE -> currentTitleWidthProportion * ANIMATION_DURATION
        }.toLong()

        return ValueAnimator.ofInt(currentTitleWidth, endWidth).apply {
            addUpdateListener {
                titleTextView.updateLayoutParams {
                    currentTitleWidth = animatedValue as Int
                    width = currentTitleWidth
                }
                interpolator = LinearInterpolator()
                duration = targetDuration
            }

            val animatorListener = BubbleViewAnimatorListener(animatorType) { state ->
                this@BubbleViewAnimator.state = state
            }
            addListener(animatorListener)
        }
    }

    private fun calculateTitleExpandedWidth(): Int {
        val view = BubbleView(titleTextView.context)

        with(view.titleTextView) {
            text = titleTextView.text
            measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        }

        return view.titleTextView.measuredWidth
    }
}