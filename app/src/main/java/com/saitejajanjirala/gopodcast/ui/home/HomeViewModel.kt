package com.saitejajanjirala.gopodcast.ui.home

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saitejajanjirala.gopodcast.domain.remote.Genre
import com.saitejajanjirala.gopodcast.domain.repo.PodCastRepository
import com.saitejajanjirala.gopodcast.domain.remote.PodCastResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.saitejajanjirala.gopodcast.domain.util.Result

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: PodCastRepository): ViewModel() {

    private var _genres = MutableStateFlow<Result<List<Genre>>>(Result.Loading(false))
    val genres : StateFlow<Result<List<Genre>>>
        get() = _genres

    init {
        loadGenres()
    }



    private var _podCasts = MutableStateFlow<Result<List<PodCastResult>>>(Result.Loading(false))
    val podCasts : StateFlow<Result<List<PodCastResult>>>
        get() = _podCasts

    private var _selectedGenrePos = mutableIntStateOf(0)

    private fun loadGenres() {
        viewModelScope.launch {
            repo.getGenres().collect{
                when(it){
                    is Result.Success->{
                        _genres.value=it
                        onGenreSelected(_selectedGenrePos.intValue)
                    }
                    else->{
                        _genres.value = it
                    }
                }
            }
        }
    }

    private fun getPodCasts(s: String) {
        viewModelScope.launch {
            repo.getPodCasts(s).collect{
                _podCasts.value = it
            }
        }
    }

    fun onGenreSelected(pos:Int){
        _selectedGenrePos.intValue = pos
        _genres.value = when (val currentValue = genres.value) {
            is Result.Success -> {
                val updatedData = currentValue.data.mapIndexed { index, genre ->
                    genre.copy(isSelected = index == pos)
                }
                currentValue.copy(data = updatedData)
            }
            else -> currentValue
        }
        getSelectedGenrePodCasts()
    }

    fun getSelectedGenrePodCasts(){
        if(genres.value is Result.Success){
            val data = (genres.value as Result.Success).data
            if(data.isNotEmpty()){
                getPodCasts(data[_selectedGenrePos.intValue].name?:"Entertainment")
            }
        }
    }


}