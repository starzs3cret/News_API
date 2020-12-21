package id.unlink.newsapi.ui.newslist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.unlink.newsapi.model.Article
import id.unlink.newsapi.retrofit.ApiService
import id.unlink.newsapi.retrofit.ApiUtils
import id.unlink.newsapi.MainActivity.Companion.CATEGORY_GENERAL
import id.unlink.newsapi.MainActivity.Companion.API_KEY
import id.unlink.newsapi.MainActivity.Companion.Q_COVID
import id.unlink.newsapi.MainActivity.Companion.TAG
import id.unlink.newsapi.model.NewsData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsListViewModel : ViewModel() {
    val listArticle = MutableLiveData<ArrayList<Article>>()
    private lateinit var mApiService: ApiService

    fun setNews() {
        loadNews()
    }

    fun getNews(): LiveData<ArrayList<Article>> {
        return listArticle
    }
    fun searchNews(q:String){
        loadNews(q)
    }


    private fun loadNews(q:String= Q_COVID) {
        mApiService = ApiUtils.apiService
        mApiService.getTopHeadlinesByQuery(q, API_KEY)
            .enqueue(object : Callback<NewsData> {
                override fun onResponse(call: Call<NewsData>, response: Response<NewsData>) {
                    if (response.code() == 200) {
                        response.body()?.let { buildResponse(it) }
                    } else {
                        Log.d(TAG, "onResponse: " + response.code() +"\n"+response.errorBody()?.string())
                    }
                }

                override fun onFailure(call: Call<NewsData>, t: Throwable) {
                    Log.e(TAG, "onFailure: ", t)
                }

            })
    }

    private fun buildResponse(newsResponse: NewsData) {
        val list = ArrayList<Article>()
        for (article in newsResponse.articles) {
            list.add(article)
        }
        // post
        listArticle.postValue(list)
    }
}