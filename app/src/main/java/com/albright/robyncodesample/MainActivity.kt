package com.albright.robyncodesample

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.albright.robyncodesample.databinding.ActivityMainBinding
import com.albright.robyncodesample.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewmodel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewmodel.articles.observe(this, Observer { response ->
            // TODO:
            //  hide progress bar
            //  start timer

            response?.let {
                if (it.status == "OK" && it.numResults > 0) {
                    val pagerAdapter = ImagePagerAdapter(this)
                    binding.imagePager.adapter = pagerAdapter
                }
            }
        })
    }

    private inner class ImagePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = viewmodel.articles.value?.numResults ?: 0

        override fun createFragment(position: Int): Fragment = ImageFragment.newInstance(
                viewmodel.articles.value?.results?.get(position)?.media?.firstOrNull()?.mediaMetadata?.getOrNull(0)?.url ?: ""
        )
    }
}