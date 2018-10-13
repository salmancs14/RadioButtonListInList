package com.repo.github.expandablelistviewtest

import android.app.Application
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class VariantCategoryApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    companion object {
        private const val BASE_URL = "https://api.myjson.com/"
        private lateinit var retrofit: Retrofit

        fun getRetrofitInstance() = retrofit
    }
}
