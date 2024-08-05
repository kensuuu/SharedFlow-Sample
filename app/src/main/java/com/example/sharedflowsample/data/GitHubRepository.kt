package com.example.sharedflowsample.data

import com.example.sharedflowsample.model.Repository
import retrofit2.HttpException
import javax.inject.Inject

class GitHubRepository @Inject constructor(
    private val gitHubApi: GitHubApi,
) {
    suspend fun searchRepositories(query: String): Result<List<Repository>> =
        runCatching {
            gitHubApi.searchRepositories(query).let { response ->
                if (response.isSuccessful) {
                    response.body()?.items ?: throw Exception("Response body is null")
                } else {
                    throw HttpException(response)
                }
            }
        }
}
