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

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewmodel: MainActivityViewModel by viewModels()
    private var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewmodel.articles.observe(this, Observer { articles ->
            // TODO:
            //  hide progress bar

            if (articles.isNotEmpty()) {
                val pagerAdapter = ImagePagerAdapter(this)
                binding.imagePager.adapter = pagerAdapter

                createHeadlines()
                startTimer()
            }
        })

        binding.imagePager.isUserInputEnabled = false
        binding.imagePager.offscreenPageLimit = 4
    }

    override fun onPause() {
        super.onPause()
        timer?.cancel()
        timer = null
    }

    private fun createHeadlines() {
        viewmodel.articles.value?.let {articles ->
            for (article in articles) {
                val headline = Headline(this)
                headline.setHeadline(article.title)

                if (articles.indexOf(article) == articles.lastIndex)
                    headline.hideDivider()

                binding.headlines.addView(headline)
            }
        }

        (binding.headlines.children.elementAt(0) as Headline).addHighlight()
    }

    private fun startTimer() {
        timer = Timer().also {
            it.schedule(ChangeImageTask(), TIMER_INTERVAL, TIMER_INTERVAL)
        }
    }

    private inner class ChangeImageTask() : TimerTask() {
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