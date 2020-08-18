package com.example.flows.dogList

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.flows.R
import com.example.flows.dogList.data.Dog
import com.example.flows.error.ResultWrapper
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dog_list_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.LazyThreadSafetyMode.NONE

@AndroidEntryPoint
class DogListFragment : Fragment(R.layout.dog_list_fragment), RecyclerAdapter.RecyclerViewClickListener {

    private val viewModel: DogListViewModel by viewModels()
    private val adapter by lazy(NONE) { RecyclerAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

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
        viewModel.snackbar.observe(viewLifecycleOwner, Observer { text ->
            text?.let {
                Snackbar.make(root_layout, text, Snackbar.LENGTH_SHORT).show()
                viewModel.onSnackbarShown()
            }
        })

        viewModel.dogListLiveData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.liveDateFetch.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultWrapper.Loading -> showLoading(it.isLoading)
                is ResultWrapper.NetworkError -> showError()
                is ResultWrapper.Success<*> -> {
                    animation_loading.visibility = View.INVISIBLE
                }
            }
        })
    }

    private fun showError() {
        showAnimation(R.raw.error_dog)
    }

    private fun showLoading(isLoading: Boolean) {
        when (isLoading) {
            true -> showAnimation(R.raw.loading)
        }
    }

    private fun showAnimation(animationResource: Int) {
        animation_loading.visibility = View.VISIBLE
        animation_loading.setAnimation(animationResource)
        animation_loading.playAnimation()
    }

    override fun onItemClicked(view: View, dog: Dog) {

        exitTransition = Hold().apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }

        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.motion_duration_small).toLong()
        }
        val toDogDetailsFragment = DogListFragmentDirections.actionDogListFragmentToDogDetailFragment(dog.imageUrl.toString(), dog.breed)
        val extras = FragmentNavigatorExtras(view to dog.imageUrl.toString())
        navigate(toDogDetailsFragment, extras)
    }

    private fun navigate(destination: NavDirections, extraInfo: FragmentNavigator.Extras) = with(findNavController()) {
        currentDestination?.getAction(destination.actionId)?.let { navigate(destination, extraInfo) }
    }
}