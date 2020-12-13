package com.postzeew.bubblenavigationbar

import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.appcompat.app.AppCompatActivity
import com.postzeew.bubblenavigationbar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bubbleView = BubbleView(this).apply {
            layoutParams = ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            bind(
                data = BubbleView.Data(
                    titleResId = R.string.menu_home,
                    titleColorResId = R.color.menu_color,
                    iconResId = R.drawable.ic_home,
                    iconColorResId = R.color.menu_color
                )
            )

            onClickListener = {

            }
        }

        binding.rootFrameLayout.addView(bubbleView)
    }
}