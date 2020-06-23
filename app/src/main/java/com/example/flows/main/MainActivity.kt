package com.example.flows.main

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.flows.R
import com.example.flows.error.ResultWrapper
import com.example.flows.extensions.showToast
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import kotlin.LazyThreadSafetyMode.NONE


class MainActivity : DaggerAppCompatActivity() {

    @Inject
    internal lateinit var factory: ViewModelProvider.Factory

    private val viewModel by lazy(NONE) {
        ViewModelProvider(this, factory).get(MainActivityViewModel::class.java)
    }
    private val adapter by lazy(NONE) { RecyclerAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler.adapter = adapter
        subscribeObservers()
        initListeners()
    }

    private fun initListeners() {
//        toggleButtonGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
//            if (isChecked) {
//                when (checkedId) {
//                    R.id.buttonAsync -> fetchImagesAsynchronously()
//                    R.id.buttonSync -> fetchImagesSynchronously()
//                }
//            }
//        }

        loadMore.setOnClickListener {
            viewModel.fetchDogsFlow()
        }
    }

//    private fun fetchImagesSynchronously() {
//        viewModel.loadDogListSynchronously()
//    }
//
//    private fun fetchImagesAsynchronously() {
//        viewModel.loadDogListAsynchronously()
//    }

    @ExperimentalCoroutinesApi
    private fun subscribeObservers() {
//        viewModel.spinner.observe(this, Observer { show ->
//            spinner.visibility = if (show) VISIBLE else GONE
//        })
//
//        viewModel.status.observe(this, Observer { it ->
//            time.text = it
//        })


        viewModel.snackbar.observe(this, Observer { text ->
            text?.let {
                Snackbar.make(root_layout, text, Snackbar.LENGTH_SHORT).show()
                viewModel.onSnackbarShown()
            }
        })


//        viewModel.dogList.observe(this, Observer {
//            val list = it as List<Dog>
//            adapter.submitList(list)
//        })

//        viewModel.topTwoDogs.observe(this, Observer {
//            when (it) {
//                is GeneralResult.Progress -> {
//                }
//                is GeneralResult.SuccessGeneric<*> -> {
//                    updateTopTwoDogs(it.data as List<Dog>)
//                }
//                is GeneralResult.ErrorType -> {
//                }
//            }
//        })


        viewModel.dogListLiveData.observe(this, Observer {
            Log.e("liveDataResult......", it.toString())
            val dogList = mutableListOf<String>()
            it.forEach {
                dogList.add(it.breed)
            }
            Log.e("dog list", dogList.toString())
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_home, menu)

        // return true so that the menu pop up is opened
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.clearAll -> {
                viewModel.clearCache()
                true
            }

            R.id.configOne ->
                true
            else -> super.onContextItemSelected(item)
        }
    }
}

