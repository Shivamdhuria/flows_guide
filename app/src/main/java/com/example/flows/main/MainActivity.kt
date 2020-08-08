package com.example.flows.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.flows.R
import com.example.flows.error.ResultWrapper
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.LazyThreadSafetyMode.NONE

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    internal companion object {
        operator fun invoke(context: Context) = Intent(context, MainActivity::class.java)
    }

    private val viewModel: MainActivityViewModel by viewModels()
    private val adapter by lazy(NONE) { RecyclerAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler.adapter = adapter
        subscribeObservers()
        initListeners()
        viewModel.setSearchQuery("")
    }

    private fun initListeners() {

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.setSearchQuery(it) }
                return true
            }
        }
        )
        loadMore.setOnClickListener {
            viewModel.fetchDogsFlow()
        }
    }

    @ExperimentalCoroutinesApi
    private fun subscribeObservers() {
        viewModel.snackbar.observe(this, Observer { text ->
            text?.let {
                Snackbar.make(root_layout, text, Snackbar.LENGTH_SHORT).show()
                viewModel.onSnackbarShown()
            }
        })

        viewModel.dogListLiveData.observe(this, Observer {
            adapter.submitList(it)
        })

        viewModel.liveDateFetch.observe(this, Observer {
            when (it) {
                is ResultWrapper.Loading -> showLoading(it.isLoading)
                is ResultWrapper.NetworkError -> showError()
                is ResultWrapper.Success<*> -> {
                    animation_loading.visibility = View.INVISIBLE
                    scroll_root.fullScroll(View.FOCUS_DOWN)
                }
            }
        })
    }

    private fun showError() {
        showAnimation(R.raw.error_animation)
    }

    private fun showLoading(isLoading: Boolean) {
        when (isLoading) {
            true -> showAnimation(R.raw.loading_animation)
        }
    }

    private fun showAnimation(animationResource: Int) {
        animation_loading.visibility = View.VISIBLE
        animation_loading.setAnimation(animationResource)
        animation_loading.playAnimation()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchDogsFlow()
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater = menuInflater
//        inflater.inflate(R.menu.menu_home, menu)
//
//        // return true so that the menu pop up is opened
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.clearAll -> {
//                viewModel.clearCache()
//                true
//            }
//
//            R.id.configOne -> {
//                viewModel.setSearch("Dachshund")
//                true
//            }
//            R.id.configTwo -> {
//                viewModel.setSearch("zz")
//                true
//            }
//            else -> super.onContextItemSelected(item)
//        }
//    }
}
