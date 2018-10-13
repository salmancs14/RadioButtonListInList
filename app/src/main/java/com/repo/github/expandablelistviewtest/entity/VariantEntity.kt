package com.repo.github.expandablelistviewtest.entity

import com.google.gson.annotations.SerializedName
import java.util.*

data class VariantEntity(

        @SerializedName("variant_groups")
        var variantGroups: ArrayList<VariantGroup> = ArrayList(),

        @SerializedName("exclude_list")
        var excludeList: ArrayList<ArrayList<ExclusionItem>> = ArrayList()
)
