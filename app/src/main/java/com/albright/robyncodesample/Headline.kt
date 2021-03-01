package com.albright.robyncodesample

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.albright.robyncodesample.databinding.HeadlineBinding

class Headline(context: Context) : ConstraintLayout(context) {
    private var binding: HeadlineBinding = HeadlineBinding.inflate(LayoutInflater.from(context), this)

    fun setHeadline(headline: String) {
        binding.headlineText.text = headline
    }

    fun addHighlight() {
        binding.headlineText.setTextColor(Color.RED)
        binding.headlineText.setTypeface(null, Typeface.BOLD)
    }

    fun removeHighlight() {
        binding.headlineText.setTextColor(Color.BLACK)
        binding.headlineText.setTypeface(null, Typeface.NORMAL)
    }

    fun hideDivider() {
        binding.divider.visibility = View.GONE
    }
}