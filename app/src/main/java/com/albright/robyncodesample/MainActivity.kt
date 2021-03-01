package com.albright.robyncodesample

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.albright.robyncodesample.databinding.ActivityMainBinding
import com.albright.robyncodesample.viewmodels.MainActivityViewModel
import java.util.*

private const val TIMER_INTERVAL = 5000L
private const val STARTING_HEADLINE = "starting_headline"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewmodel: MainActivityViewModel by viewModels()
    private var timer: Timer? = null
    private var startingHeadline = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            startingHeadline = savedInstanceState.getInt(STARTING_HEADLINE)
        }

        viewmodel.articles.observe(this, Observer { articles ->
            if (articles.isNotEmpty()) {
                createHeadlines()

                val pagerAdapter = ImagePagerAdapter(this)

                (binding.headlines.children.elementAt(startingHeadline) as Headline).addHighlight()
                binding.imagePager.offscreenPageLimit = articles.size - 1
                binding.imagePager.adapter = pagerAdapter

                startTimer()
            }
        })

        binding.imagePager.isUserInputEnabled = false
    }

    override fun onPause() {
        super.onPause()
        timer?.cancel()
        timer = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(STARTING_HEADLINE, binding.imagePager.currentItem)
    }

    private fun createHeadlines() {
        viewmodel.articles.value?.let {articles ->
            for (article in articles) {
                val headline = Headline(this)

//                val params = headline.layoutParams as ConstraintLayout.LayoutParams
//                params.setMargins(0, 8, 0, 0)
//                headline.layoutParams = params

                headline.setHeadline(article.title)

                if (articles.indexOf(article) == articles.lastIndex)
                    headline.hideDivider()

                binding.headlines.addView(headline)
            }
        }
    }

    private fun startTimer() {
        timer = Timer().also {
            it.schedule(ChangeImageTask(), TIMER_INTERVAL, TIMER_INTERVAL)
        }
    }

    private inner class ChangeImageTask : TimerTask() {
        override fun run() {
            runOnUiThread {
                if (binding.imagePager.currentItem == viewmodel.articles.value?.lastIndex) {
                    binding.imagePager.currentItem = 0
                    (binding.headlines.children.elementAt(binding.headlines.childCount - 1) as Headline).removeHighlight()
                    (binding.headlines.children.elementAt(0) as Headline).addHighlight()
                }
                else {
                    binding.imagePager.currentItem = binding.imagePager.currentItem + 1
                    (binding.headlines.children.elementAt(binding.imagePager.currentItem - 1) as Headline).removeHighlight()
                    (binding.headlines.children.elementAt(binding.imagePager.currentItem) as Headline).addHighlight()
                }
            }
        }
    }

    private inner class ImagePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = viewmodel.articles.value?.size ?: 0

        override fun createFragment(position: Int): Fragment = ImageFragment.newInstance(
                viewmodel.articles.value?.get(position)?.media?.firstOrNull()?.mediaMetadata?.getOrNull(2)?.url ?: ""
        )
    }
}