package com.postzeew.bubblenavigationbar.bubbleview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter

internal class BubbleViewAnimatorListener(
    private val animatorType: BubbleViewAnimatorType,
    private val animationStateChangeListener: (BubbleViewAnimationState) -> Unit
) : AnimatorListenerAdapter() {
    private var wasAnimationCancelled = false

    override fun onAnimationStart(animation: Animator?) {
        wasAnimationCancelled = false

        val state = when (animatorType) {
            BubbleViewAnimatorType.EXPAND -> BubbleViewAnimationState.EXPANDING
            BubbleViewAnimatorType.COLLAPSE -> BubbleViewAnimationState.COLLAPSING
        }

        animationStateChangeListener.invoke(state)
    }

    override fun onAnimationCancel(animation: Animator?) {
        wasAnimationCancelled = true
    }

    override fun onAnimationEnd(animation: Animator?) {
        if (wasAnimationCancelled) return

        val state = when (animatorType) {
            BubbleViewAnimatorType.EXPAND -> BubbleViewAnimationState.EXPANDED
            BubbleViewAnimatorType.COLLAPSE -> BubbleViewAnimationState.COLLAPSED
        }

        animationStateChangeListener.invoke(state)
    }
}