package nl.vanwijngaarden.koen.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Network {
    private val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://inhollandbackend.azurewebsites.net/api/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
    private val newsreaderService: NewsreaderService = retrofit.create(NewsreaderService::class.java)

    val apiClient = ApiClient(newsreaderService)
}