package com.postzeew.bubblenavigationbar

import androidx.annotation.ColorRes

class BubbleNavigationBarMenuItemMapper(
    @ColorRes private val itemTitleColorResId: Int,
    @ColorRes private val itemIconColorResId: Int
) {
    fun mapToBubbleViewItem(menuItem: BubbleNavigationBarMenuItem): BubbleViewItem {
        return with(menuItem) {
            BubbleViewItem(
                isActive = isActive,
                titleResId = titleResId,
                titleColorResId = itemTitleColorResId,
                iconResId = iconResId,
                iconColorResId = itemIconColorResId
            )
        }
    }
}