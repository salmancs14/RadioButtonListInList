package com.repo.github.expandablelistviewtest.viewmodel

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.repo.github.expandablelistviewtest.entity.Variants
import com.repo.github.expandablelistviewtest.repository.VariantRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class VariantCategoryViewModel : ViewModel() {

    var progressState: ObservableField<Boolean> = ObservableField(false)
    var noResultState: ObservableField<Boolean> = ObservableField(false)
    var noResultString: ObservableField<String> = ObservableField()

    fun handleSuccessResponse() {
        progressState.set(false)
    }

    fun handleFailedResponse() {
        progressState.set(false)
        noResultState.set(true)
        noResultString.set("Api failed")
    }

    fun handleNoInternetResponse() {
        progressState.set(false)
        noResultState.set(true)
        noResultString.set("No Internet Connected")
    }

    fun loadData(variantRepository: VariantRepository) : Single<Variants> {
        progressState.set(true)
        return variantRepository.getVariantRepository().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
