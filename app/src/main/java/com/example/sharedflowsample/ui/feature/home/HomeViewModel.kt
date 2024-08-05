package com.example.sharedflowsample.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharedflowsample.data.GitHubRepository
import com.example.sharedflowsample.model.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val repositories: List<Repository> = emptyList(),
)

sealed class HomeUiEvent {
    data class Search(val query: String) : HomeUiEvent()
    data class Open(val url: String) : HomeUiEvent()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val gitHubRepository: GitHubRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<HomeUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun search(query: String) {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch {
            gitHubRepository.searchRepositories(query)
                .onSuccess { repositories ->
                    _uiState.update {
                        it.copy(repositories = repositories, isLoading = false)
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(errorMessage = exception.message, isLoading = false)
                    }
                }
        }
    }

    fun onEvent(event: HomeUiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }
}
