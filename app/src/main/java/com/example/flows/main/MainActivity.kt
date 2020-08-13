package com.example.flows.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.flows.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    internal companion object {
        operator fun invoke(context: Context) = Intent(context, MainActivity::class.java)
    }

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }
    private val appBarConfiguration by lazy {
        AppBarConfiguration(topLevelDestinationIds =
        setOf(
                R.id.dogListFragment,
                R.id.favoritesFragment
        )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setupNavigation() {
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)
        bottom_navigation.setupWithNavController(navController)
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            if (destination.id in arrayOf(R.id.aboutFragment)) {
//                fab.hide()
//            } else {
// //                fab.show()
//            }

//            if (destination.id == R.id.presentationFragment) {
//                toolbar.visibility = View.GONE
//            } else {
//                toolbar.visibility = View.VISIBLE
//            }
//        }
    }

//
//    private fun initListeners() {
//
//        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
//                androidx.appcompat.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                newText?.let { viewModel.setSearchQuery(it) }
//                return true
//            }
//        }
//        )
//        loadMore.setOnClickListener {
//            viewModel.fetchDogsFlow()
//        }
//    }
//
//    @ExperimentalCoroutinesApi
//    private fun subscribeObservers() {
//        viewModel.snackbar.observe(this, Observer { text ->
//            text?.let {
//                Snackbar.make(root_layout, text, Snackbar.LENGTH_SHORT).show()
//                viewModel.onSnackbarShown()
//            }
//        })
//
//        viewModel.dogListLiveData.observe(this, Observer {
//            adapter.submitList(it)
//        })
//
//        viewModel.liveDateFetch.observe(this, Observer {
//            when (it) {
//                is ResultWrapper.Loading -> showLoading(it.isLoading)
//                is ResultWrapper.NetworkError -> showError()
//                is ResultWrapper.Success<*> -> {
//                    animation_loading.visibility = View.INVISIBLE
//                    scroll_root.fullScroll(View.FOCUS_DOWN)
//                }
//            }
//        })
//    }
//
//    private fun showError() {
//        showAnimation(R.raw.error_dog)
//    }
//
//    private fun showLoading(isLoading: Boolean) {
//        when (isLoading) {
//            true -> showAnimation(R.raw.loading)
//        }
//    }
//
//    private fun showAnimation(animationResource: Int) {
//        animation_loading.visibility = View.VISIBLE
//        animation_loading.setAnimation(animationResource)
//        animation_loading.playAnimation()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        viewModel.fetchDogsFlow()
//    }

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
