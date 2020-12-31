package com.postzeew.bubblenavigationbar

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.content.res.getResourceIdOrThrow

class BubbleNavigationBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private lateinit var activeBubbleView: BubbleView

    lateinit var selectListener: (id: Int) -> Unit
    var reselectListener: ((id: Int) -> Unit)? = null

    init {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.BubbleNavigationBar, 0, 0)

        val menuResId = typedArray.getResourceIdOrThrow(R.styleable.BubbleNavigationBar_menu)
        val itemTitleColorResId = typedArray.getResourceIdOrThrow(R.styleable.BubbleNavigationBar_itemTitleColor)
        val itemIconColorResId = typedArray.getResourceIdOrThrow(R.styleable.BubbleNavigationBar_itemIconColor)

        typedArray.recycle()

        val menuItems = BubbleNavigationBarMenuParser(context).parse(menuResId)
        val menuItemMapper = BubbleNavigationBarMenuItemMapper(itemTitleColorResId, itemIconColorResId)
        val bubbleViewItems = menuItems.map(menuItemMapper::mapToBubbleViewItem)

        bubbleViewItems
            .map(::createBubbleView)
            .map(::wrapIntoFrameLayout)
            .forEach(::addView)
    }

    private fun createBubbleView(item: BubbleViewItem): BubbleView {
        return BubbleView(context).apply {
            layoutParams = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                gravity = Gravity.CENTER
            }
            onClickListener = ::onBubbleClicked
            bind(item = item)
            if (item.isActive) {
                activeBubbleView = this
            }
        }
    }

    private fun wrapIntoFrameLayout(bubbleView: BubbleView): FrameLayout {
        return FrameLayout(context).apply {
            layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                weight = 1F
                gravity = Gravity.CENTER_VERTICAL
            }
            addView(bubbleView)
        }
    }

    private fun onBubbleClicked(clickedBubbleView: BubbleView) {
        if (activeBubbleView == clickedBubbleView) {
            reselectListener?.invoke(clickedBubbleView.id)
        } else {
            activeBubbleView.switchState(isActive = true)
            clickedBubbleView.switchState(isActive = false)
            activeBubbleView = clickedBubbleView
        }
    }
}