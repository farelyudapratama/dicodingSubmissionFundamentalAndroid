package com.yuch.githubuserapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yuch.githubuserapp.data.response.DataUser
import com.yuch.githubuserapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ViewModel::class.java]

        if (savedInstanceState == null) {
            viewModel.setSearchUsers("farelyudapratama")
        }

        setupRecyclerView()
        setupSearchButton()
    }

    private fun setupRecyclerView() {
        adapter = UserAdapter().apply {
            setItemClickCallback(object : UserAdapter.OnItemClickCallback{
                override fun onItemClick(data: DataUser) {
                    data.login?.let { navigateToUserDetail(it) }
                }
            })
        }

        binding.user.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = this@MainActivity.adapter
        }

        viewModel.getSearchUsers().observe(this) { users ->
            if (users.isNotEmpty()) {
                adapter.setList(users)
                showLoading(false)
            } else {
                adapter.clearList()
                showLoading(false)
                binding.notFound.visibility = View.VISIBLE
            }
        }

    }

    private fun setupSearchButton() {
        binding.btnSearch.setOnClickListener {
            searchUser()
        }

        binding.etQuery.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                searchUser()
                true
            } else {
                false
            }
        }
    }

    private fun searchUser() {
        val query = binding.etQuery.text.toString()
        if (query.isNotEmpty()) {
            showLoading(true)
            viewModel.setSearchUsers(query)
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
        binding.notFound.visibility = View.GONE
    }

    private fun navigateToUserDetail(username: String) {
        val intent = Intent(this@MainActivity, DetailUserActivity::class.java).apply {
            putExtra(DetailUserActivity.EXTRA_USERNAME, username)
        }
        startActivity(intent)
    }
}
