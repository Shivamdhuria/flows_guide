package com.example.flows.search

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.flows.R
import com.example.flows.dogList.RecyclerAdapter
import com.example.flows.dogList.data.Dog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dog_list_fragment.recycler
import kotlinx.android.synthetic.main.search_dog_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class SearchDogFragment : Fragment(R.layout.search_dog_fragment),
    RecyclerAdapter.RecyclerViewClickListener {

    private val viewModel: SearchDogViewModel by viewModels()
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { RecyclerAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        recycler.adapter = adapter
        subscribeObservers()
        initListeners()
        viewModel.setSearchQuery("")
    }

    @ExperimentalCoroutinesApi
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
        search.requestFocus()
    }

    @ExperimentalCoroutinesApi
    private fun subscribeObservers() {

        viewModel.dogListLiveData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    override fun onItemClicked(view: View, dog: Dog) {
        val toDogDetailsFragment =
            SearchDogFragmentDirections.actionSearchDogFragmentToSearchDogDetailFragment(
                dog.imageUrl.toString(),
                dog.breed
            )
        navigate(toDogDetailsFragment)
    }

    private fun navigate(destination: NavDirections) =
        with(findNavController()) {
            currentDestination?.getAction(destination.actionId)
                ?.let { navigate(destination) }
        }
}
