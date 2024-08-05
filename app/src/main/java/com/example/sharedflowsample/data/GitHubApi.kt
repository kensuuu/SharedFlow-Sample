package com.example.sharedflowsample.data

import com.example.sharedflowsample.model.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {
    @GET("/search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
    ): Response<SearchResponse>
}
