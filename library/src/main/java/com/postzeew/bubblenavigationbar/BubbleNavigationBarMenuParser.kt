package com.postzeew.bubblenavigationbar

import android.annotation.SuppressLint
import android.content.Context
import android.util.Xml
import androidx.annotation.MenuRes
import androidx.core.content.res.getResourceIdOrThrow
import org.xmlpull.v1.XmlPullParser.END_DOCUMENT
import org.xmlpull.v1.XmlPullParser.START_TAG

private const val MENU_TAG = "item"

internal class BubbleNavigationBarMenuParser(
    private val context: Context
) {
    fun parse(@MenuRes menuResId: Int): List<BubbleNavigationBarMenuItem> {
        @SuppressLint("ResourceType")
        val parser = context.resources.getLayout(menuResId)
        val attrs = Xml.asAttributeSet(parser)

        val menuItems = mutableListOf<BubbleNavigationBarMenuItem>()

        var hasNextTag = true
        while (hasNextTag) {
            if (parser.eventType == START_TAG && parser.name == MENU_TAG) {
                val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BubbleView)

                val id = typedArray.getResourceIdOrThrow(R.styleable.BubbleView_android_id)
                val titleResId = typedArray.getResourceIdOrThrow(R.styleable.BubbleView_android_title)
                val iconResId = typedArray.getResourceIdOrThrow(R.styleable.BubbleView_android_icon)
                val isActive = typedArray.getBoolean(R.styleable.BubbleView_android_checked, false)

                val menuItem = BubbleNavigationBarMenuItem(id, titleResId, iconResId, isActive)
                menuItems.add(menuItem)

                typedArray.recycle()
            }

            hasNextTag = parser.next() != END_DOCUMENT
        }
        return menuItems
    }
}