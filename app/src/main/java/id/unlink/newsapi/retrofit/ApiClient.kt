package id.unlink.newsapi.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private var retrofit:Retrofit? = null
    fun getClient(baseUrl:String):Retrofit{
        // if anything error just run below code
        /*val logging:HttpLoggingInterceptor = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient:OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)*/
        if (retrofit==null){
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
     //           .client(httpClient.build())
                .build()
        }
        return retrofit!!
    }
}