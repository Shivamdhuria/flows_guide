package com.example.flows.main

import androidx.lifecycle.*
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val mainActivityRepository: MainActivityRepository) : ViewModel() {
    private val _inputLiveData = MutableLiveData<Int>()
    private val atomicInteger = AtomicInteger()

    @ExperimentalCoroutinesApi
    val dogListLiveData = mainActivityRepository.dogListFlow.asLiveData()

    val liveDateFetch = _inputLiveData.switchMap {
        liveData {
            emit(mainActivityRepository.tryFetchAndUpdate())
        }
    }

    fun fetchDogsFlow() {
        _inputLiveData.value = atomicInteger.incrementAndGet()
    }
}
