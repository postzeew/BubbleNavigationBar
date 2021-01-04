package com.postzeew.bubblenavigationbar.bubbleview

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes

internal data class BubbleViewItem(
    @IdRes val id: Int,
    val isActive: Boolean,
    @StringRes val titleResId: Int,
    @ColorRes val titleColorResId: Int,
    @DrawableRes val iconResId: Int,
    @ColorRes val iconColorResId: Int
)