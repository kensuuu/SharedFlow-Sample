package com.example.sharedflowsample.ui.feature.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    onSearch: (String) -> Unit,
) {
    var query by rememberSaveable { mutableStateOf("") }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        TextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            label = { Text(text = "Search") }
        )
        Button(onClick = { if (query.isNotBlank()) onSearch(query) }) {
            Text(text = "Search")
        }
    }
}
