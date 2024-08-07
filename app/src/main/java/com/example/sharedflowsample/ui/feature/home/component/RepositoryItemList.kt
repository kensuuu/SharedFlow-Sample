package com.example.sharedflowsample.ui.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.sharedflowsample.model.Repository

@Composable
fun RepositoryItemList(
    repositories: List<Repository>,
    onClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            items = repositories,
            key = { it.id },
        ) { item ->
            RepositoryItem(
                item = item,
                onClick = onClick,
            )
        }
    }
}

@Composable
fun RepositoryItem(
    item: Repository,
    onClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(8.dp))
            .border(1.dp, MaterialTheme.colorScheme.onBackground, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.owner.avatar)
                    .crossfade(true)
                    .build(),
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
            )
            Text(
                text = item.name,
                modifier = Modifier
                    .clickable { onClick(item.url) },
                color = Color.Blue,
                textDecoration = TextDecoration.Underline,
                style = MaterialTheme.typography.titleSmall,
            )
        }
        Text(
            text = item.description ?: "",
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
