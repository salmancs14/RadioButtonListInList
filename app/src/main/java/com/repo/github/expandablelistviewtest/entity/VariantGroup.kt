package com.repo.github.expandablelistviewtest.entity

import com.google.gson.annotations.SerializedName

import java.util.ArrayList

data class VariantGroup(

        @SerializedName("group_id")
        var groupId: String? = null,
        @SerializedName("name")
        var name: String? = null,
        @SerializedName("variations")
        var variations: ArrayList<Variations> = ArrayList()
)
