package com.example.flows.favorite

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi

class FavoriteDogViewModel @ViewModelInject constructor(private val favoriteDogFragmentRepository: FavoriteDogFragmentRepository) : ViewModel() {

    @ExperimentalCoroutinesApi
    val dogListLiveData = favoriteDogFragmentRepository.favoriteDogsFlow.asLiveData()
}
