package com.example.flows.dogList

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.flows.R
import com.example.flows.error.ResultWrapper
import com.google.android.material.snackbar.Snackbar
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
                    scroll_root.fullScroll(View.FOCUS_DOWN)
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

    override fun onResume() {
        super.onResume()
        viewModel.fetchDogsFlow()
    }

    override fun itemClickedClicked(imageUrl: String, breed: String) {
        val toDogDetailsFragment = DogListFragmentDirections.actionDogListFragmentToDogDetailFragment(imageUrl, breed)
        navigate(toDogDetailsFragment)
    }

//    inner class ActionModeCallback : ActionMode.Callback {
//        var shouldResetRecyclerView = true
//        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
//            when (item?.getItemId()) {
//                R.id.action_delete -> {
//                    shouldResetRecyclerView = false
//                    myAdapter?.deleteSelectedIds()
//                    actionMode?.setTitle("") //remove item count from action mode.
//                    actionMode?.finish()
//                    return true
//                }
//            }
//            return false
//        }
//
//        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
//            val inflater = mode?.getMenuInflater()
//            inflater?.inflate(R.menu.action_mode_menu, menu)
//            return true
//        }
//
//        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
//            menu?.findItem(R.id.action_delete)?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
//            return true
//        }
//
//        override fun onDestroyActionMode(mode: ActionMode?) {
//            if (shouldResetRecyclerView) {
//                adapter?.selectedIds?.clear()
//                adapter?.notifyDataSetChanged()
//            }
//            isMultiSelectOn = false
//            actionMode = null
//            shouldResetRecyclerView = true
//        }
//    }

    private fun navigate(destination: NavDirections) = with(findNavController()) {
        currentDestination?.getAction(destination.actionId)?.let { navigate(destination) }
    }
}