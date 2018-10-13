package com.repo.github.expandablelistviewtest.repository

import com.repo.github.expandablelistviewtest.api.VariantApis

class VariantRepository constructor(val variantApis: VariantApis) {

    public fun getVariantRepository() = variantApis.getVariantApis()
}
