package id.unlink.newsapi.retrofit

import id.unlink.newsapi.model.NewsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    //https://newsapi.org/v2/top-headlines?category=sport&apiKey=ee66808b71ea4342a4b6c1491e41a01e
    //sources, q, language, country, category
    @GET("v2/top-headlines")
    fun getTopHeadlinesByCatergory(@Query("category")category: String,@Query("apiKey")apiKey:String):Call<NewsData>
    @GET("v2/top-headlines")
    fun getTopHeadlinesByQuery(@Query("q")q:String,@Query("apiKey")apiKey: String):Call<NewsData>
    @GET("v2/top-headlines")
    fun getTopHeadlinesbyCountry(@Query("country")country:String,@Query("apiKey")apiKey: String):Call<NewsData>
}