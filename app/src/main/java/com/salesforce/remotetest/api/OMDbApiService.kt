package com.salesforce.remotetest.api

import com.salesforce.remotetest.data.Movie
import com.salesforce.remotetest.data.Response
import com.salesforce.remotetest.util.API_KEY
import com.salesforce.remotetest.util.API_KEY_VALUE
import com.salesforce.remotetest.util.BASE_URL
import com.salesforce.remotetest.util.TIMEOUT_REQUEST
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface OMDbApiService {
    @GET(".")
    fun getMovies(@Query("s") searchMovies: String, @Query(API_KEY) apiKey : String = API_KEY_VALUE): Observable<Response>

    @GET(".")
    fun getMovie(@Query("t") movieTitle: String, @Query(API_KEY) apiKey : String = API_KEY_VALUE): Observable<Movie>

    companion object {
        fun create(): OMDbApiService {
            val httplogging = HttpLoggingInterceptor()
            httplogging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient(httplogging))
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(OMDbApiService::class.java)
        }

        private fun getClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(TIMEOUT_REQUEST, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_REQUEST, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_REQUEST, TimeUnit.SECONDS)
                .build()
        }
}