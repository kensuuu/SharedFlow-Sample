package com.example.sharedflowsample.ui.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import com.example.sharedflowsample.ui.feature.home.component.RepositoryItemList
import com.example.sharedflowsample.ui.feature.home.component.SearchBar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(lifecycleOwner, viewModel) {
        viewModel.uiEvent
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .onEach { event ->
                when (event) {
                    is HomeUiEvent.Search -> {
                        keyboardController?.hide()
                        viewModel.search(event.query)
                    }

                    is HomeUiEvent.Open -> {
                        navController.navigate("webView/?url=${event.url}")
                    }
                }
            }
            .launchIn(this)
    }

    HomeScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "GitHub Search") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            HomeHeader(
                onEvent = onEvent,
            )
            HomeContent(
                uiState = uiState,
                onEvent = onEvent,
            )
        }
    }
}

@Composable
private fun HomeHeader(
    onEvent: (HomeUiEvent) -> Unit,
) {
    SearchBar(
        onSearch = { onEvent(HomeUiEvent.Search(it)) },
    )
}

@Composable
private fun HomeContent(
    uiState: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
            }

            uiState.errorMessage != null -> {
                Text(text = uiState.errorMessage)
            }

            else -> {
                RepositoryItemList(
                    repositories = uiState.repositories,
                    onClick = { onEvent(HomeUiEvent.Open(it)) },
                )
            }
        }
    }
}
