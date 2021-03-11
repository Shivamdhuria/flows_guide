package com.elixer.paws.search

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.elixer.paws.R
import com.elixer.paws.dogList.RecyclerAdapter
import com.elixer.paws.dogList.data.Dog
import com.google.android.material.transition.MaterialFadeThrough
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dog_list_fragment.recycler
import kotlinx.android.synthetic.main.search_dog_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class SearchDogFragment : Fragment(R.layout.search_dog_fragment),
    RecyclerAdapter.RecyclerViewClickListener {

    private val viewModel: SearchDogViewModel by viewModels()
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { RecyclerAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough().apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }
    }

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
        TODO("Not yet implemented")
    }
}
