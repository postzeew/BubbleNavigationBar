package com.postzeew.bubblenavigationbar

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes

data class BubbleNavigationBarMenuItem(
    @IdRes val id: Int,
    @StringRes val titleResId: Int,
    @DrawableRes val iconResId: Int,
    val isActive: Boolean
)