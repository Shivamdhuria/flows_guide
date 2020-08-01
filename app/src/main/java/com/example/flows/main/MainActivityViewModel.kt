package com.example.flows.main

import androidx.lifecycle.*
import com.example.flows.main.data.Dog
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    private val parentJob = SupervisorJob()
    private val _dogList = MutableLiveData<List<Dog>>()
    private val _snackbar = MutableLiveData<String?>()
    private val _status = MutableLiveData<String?>()
    private val _inputLiveData = MutableLiveData<Int>()
    private val _clearCacheLiveData = MutableLiveData<Int>()
    private val _spinner = MutableLiveData(false)
    private val _topDogsAsync = MutableLiveData<List<Dog>>()
    private val atomicInteger = AtomicInteger()

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

    @ExperimentalCoroutinesApi
    private val searchChanel = ConflatedBroadcastChannel<String>()

    @ExperimentalCoroutinesApi
    fun setSearchQuery(search: String) {
        searchChanel.offer(search)
    }

    @ExperimentalCoroutinesApi
    val dogListLiveData = searchChanel.asFlow()
        .flatMapLatest { search ->
            // We use flatMapLatest as we don't want flows of flows and we only want to query the latest searched string in case user types
            // in a new query before the earlier one is finished processing.
            mainActivityRepository.getSearchedDogs(search)
        }
        .catch { throwable ->
            _snackbar.value = throwable.message
        }.asLiveData()

    val liveDateFetch = _inputLiveData.switchMap {
        liveData {
            emit(mainActivityRepository.tryFetchAndUpdate())
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
}
