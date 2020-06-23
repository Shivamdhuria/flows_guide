package com.example.flows.main

import androidx.lifecycle.*
import com.example.flows.main.data.Dog
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val mainActivityRepository: MainActivityRepository) : ViewModel() {
    private companion object {
        private const val DELAY_BETWEEN_DOGS_IN_MS = 10000L
    }

    private val parentJob = SupervisorJob()
    private val _dogList = MutableLiveData<List<Dog>>()
    private val _snackbar = MutableLiveData<String?>()
    private val _status = MutableLiveData<String?>()
    private val _inputLiveData = MutableLiveData<Int>()
    private val _clearCacheLiveData = MutableLiveData<Int>()
    private val _spinner = MutableLiveData(false)
    private val _topDogsAsync = MutableLiveData<List<Dog>>()
    private val atomicInteger = AtomicInteger()

    init {
//        loadTopTwoDogsAsync()
    }

    val dogList: LiveData<List<Dog>>
        get() = _dogList
    val snackbar: LiveData<String?>
        get() = _snackbar
    val spinner: LiveData<Boolean>
        get() = _spinner
    val status: LiveData<String?>
        get() = _status
    val topDogsAsync: LiveData<List<Dog>>
        get() = _topDogsAsync

    fun onSnackbarShown() {
        _snackbar.value = null
    }


    //without channel
//    @ExperimentalCoroutinesApi
//    val dogListLiveData = mainActivityRepository.dogListFlow.asLiveData()

    //with channel
    private val searchChanel = ConflatedBroadcastChannel<String>()

    val dogListLiveData = searchChanel.asFlow()
        .flatMapLatest { search ->
            //use this as we don't want flows of flows

            mainActivityRepository.getSearchedDogs(search)
        }
//        .onEach {
//            _spinner.value = false
//        }
        .catch { throwable ->
            _snackbar.value = throwable.message
        }.asLiveData()

    fun setSearch(search: String) {
        searchChanel.offer(search)
    }


    val liveDateFetch = _inputLiveData.switchMap {
        liveData {
            emit(mainActivityRepository.tryFetchAndUpdate())
        }
    }

    private fun loadData(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _spinner.value = true
                block()
            } catch (error: Throwable) {
                _snackbar.value = error.message
            } finally {
                _spinner.value = false
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        parentJob.cancelChildren()
    }

    fun fetchDogsFlow() {
        _inputLiveData.value = atomicInteger.incrementAndGet()
    }

    fun clearCache() {
        viewModelScope.launch {
            mainActivityRepository.clearCacheData()
        }
    }


//Connect Using Channel


}



