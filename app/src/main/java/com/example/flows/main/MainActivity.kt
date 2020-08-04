package com.example.flows.main

import android.os.Bundle
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.flows.R
import com.example.flows.error.ResultWrapper
import com.example.flows.extensions.showToast
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import kotlin.LazyThreadSafetyMode.NONE

// Adds this to dagger graph so we can inject dependencies in it
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
// class MainActivity : DaggerAppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    @Inject
    lateinit var random: String

    private val adapter by lazy(NONE) { RecyclerAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler.adapter = adapter
        subscribeObservers()
        initListeners()
        viewModel.setSearchQuery("")
        showToast(random)
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
                is ResultWrapper.Loading -> {
                }
                is ResultWrapper.NetworkError -> {
                    showToast("NO internet")
                }
            }
        })
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
