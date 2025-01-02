package com.saitejajanjirala.gopodcast.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saitejajanjirala.gopodcast.domain.remote.PodCastResult
import com.saitejajanjirala.gopodcast.domain.repo.PodCastRepository
import com.saitejajanjirala.gopodcast.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: PodCastRepository) : ViewModel() {
    private var _podCasts = MutableStateFlow<Result<List<PodCastResult>>>(Result.Loading(false))
    val podCasts : StateFlow<Result<List<PodCastResult>>>
        get() = _podCasts


     fun getPodCasts(searchText: String) {
        viewModelScope.launch {
            repository.getPodCasts(searchText).collect{
                _podCasts.value = it
            }
        }
    }
}