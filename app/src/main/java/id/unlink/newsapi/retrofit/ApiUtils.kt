package id.unlink.newsapi.retrofit

import id.unlink.newsapi.MainActivity.Companion.BASE_URL

object ApiUtils {
    val apiService: ApiService
    get() {
        return ApiClient.getClient(BASE_URL).create(ApiService::class.java)
    }
}