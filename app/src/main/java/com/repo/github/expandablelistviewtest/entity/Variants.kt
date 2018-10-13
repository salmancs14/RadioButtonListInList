package com.repo.github.expandablelistviewtest.entity

import com.google.gson.annotations.SerializedName

data class Variants(
        @SerializedName("variants")
        var variants: VariantEntity? = null
)
