package com.repo.github.expandablelistviewtest.entity

import com.google.gson.annotations.SerializedName

data class ExclusionItem(

        @SerializedName("group_id")
        var groupId: String? = null,
        @SerializedName("variation_id")
        var variationId: String? = null
)
