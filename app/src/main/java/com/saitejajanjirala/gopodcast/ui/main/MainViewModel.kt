package com.saitejajanjirala.gopodcast.ui.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.saitejajanjirala.gopodcast.domain.remote.PodCastResult
import com.saitejajanjirala.gopodcast.domain.repo.PodCastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: PodCastRepository) : ViewModel() {

    private val _miniPlayer = MutableStateFlow<PodCastResult?>(null)
    val miniPlayer: StateFlow<PodCastResult?>
        get() = _miniPlayer

    private val _isExpanded = MutableStateFlow(false)
    val isExpanded: StateFlow<Boolean>
        get() = _isExpanded

    private val _player: ExoPlayer = repository.getExoplayer()
    private val _currentPosition = MutableStateFlow(0L)
    private val _duration = MutableStateFlow(0L)
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    val currentPosition: StateFlow<Long> = _currentPosition
    val duration: StateFlow<Long> = _duration



    init {
        observePlayerState()
    }

    private fun observePlayerState() {
        viewModelScope.launch {
            while (true) {
                _currentPosition.value = _player.currentPosition
                _duration.value = _player.duration
                _isPlaying.value = _player.isPlaying
                delay(500)
            }
        }
    }

    fun setMediaItem() {
        _player.setMediaItem(MediaItem.fromUri(miniPlayer.value?.audio?:""))
        _player.prepare()
    }


    fun play() {
        _player.playWhenReady = true
    }

    fun pause() {
        _player.playWhenReady = false
    }

    fun seekTo(position: Long) {
        _player.seekTo(position)
    }

    fun seekBy(offset: Long) {
        _player.seekTo((_player.currentPosition + offset).coerceIn(0, _duration.value))
    }

    override fun onCleared() {
        super.onCleared()
        _player.release()
    }

    fun setUpMiniBar(podCastResult: PodCastResult) {
        _miniPlayer.value = podCastResult
        setMediaItem()
    }

    fun setExpanded(b: Boolean) {
        _isExpanded.value = b
    }
}