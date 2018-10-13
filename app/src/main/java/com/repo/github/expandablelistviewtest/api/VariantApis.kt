package com.repo.github.expandablelistviewtest.api

import com.repo.github.expandablelistviewtest.entity.Variants
import io.reactivex.Single
import retrofit2.http.GET

interface VariantApis {

    @GET("bins/3b0u2")
    fun getVariantApis(): Single<Variants>
}
