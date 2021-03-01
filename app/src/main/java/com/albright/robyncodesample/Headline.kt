package com.albright.robyncodesample

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class Headline(context: Context) : ConstraintLayout(context) {
    init {
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.headline, this)
    }

    fun setHeadline(headline: String) {
        findViewById<TextView>(R.id.headlineText).text = headline
    }

    fun addHighlight() {
        findViewById<TextView>(R.id.headlineText).setTextColor(Color.RED)
        findViewById<TextView>(R.id.headlineText).setTypeface(null, Typeface.BOLD)
    }

    fun removeHighlight() {
        findViewById<TextView>(R.id.headlineText).setTextColor(Color.BLACK)
        findViewById<TextView>(R.id.headlineText).setTypeface(null, Typeface.NORMAL)
    }

    fun hideDivider() {
        findViewById<View>(R.id.divider).visibility = View.GONE
    }
}