package com.example.sharedflowsample.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("total_count")
    val totalCount: Int,
    val items: List<Repository>,
)

data class Repository(
    val id: String,
    @SerializedName("full_name")
    val name: String,
    val description: String?,
    val owner: Owner,
    @SerializedName("html_url")
    val url: String,
)

data class Owner(
    @SerializedName("avatar_url")
    val avatar: String,
)
