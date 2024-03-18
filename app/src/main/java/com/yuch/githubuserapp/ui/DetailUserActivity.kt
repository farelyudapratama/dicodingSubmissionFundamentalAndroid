package com.yuch.githubuserapp.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.yuch.githubuserapp.databinding.ActivityDetailUserBinding

class  DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel
    private var username: String? = null

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        savedInstanceState?.getString(EXTRA_USERNAME)?.let {
            username = it
        } ?: run {
            username = intent.getStringExtra(EXTRA_USERNAME)
        }

        val bundle = Bundle().apply {
            putString(EXTRA_USERNAME, username)
        }

        showLoading(true)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailUserViewModel::class.java]

        viewModel.setUserDetail(username)
        viewModel.getUserDetail().observe(this) { user ->
            if (user != null) {
                binding.apply {
                    dName.text = user.name
                    dUsername.text = user.login
                    tvRepo.text = user.repo.toString()
                    tvGist.text = user.gists.toString()
                    Glide.with(this@DetailUserActivity)
                        .load(user.avatarUrl)
                        .centerCrop()
                        .into(dImgAvatar)

                    val followersCount = user.followers ?: 0
                    val followingCount = user.following ?: 0
                    val sectionPagerAdapter = SectionPagerAdapter(this@DetailUserActivity, followersCount, followingCount, bundle)

                    viewPager.adapter = sectionPagerAdapter
                    TabLayoutMediator(table, viewPager) { tab, position ->
                        tab.text = sectionPagerAdapter.getPageTitle(position)
                    }.attach()
                    supportActionBar?.elevation = 0f

                    showLoading(false)
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        username?.let {
            outState.putString(EXTRA_USERNAME, it)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        username = savedInstanceState.getString(EXTRA_USERNAME)
    }

    private fun showLoading(state: Boolean){
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}
