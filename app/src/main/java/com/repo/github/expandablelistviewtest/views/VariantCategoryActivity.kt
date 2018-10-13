package com.repo.github.expandablelistviewtest.views

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.RadioButton
import com.repo.github.expandablelistviewtest.ConnectionUtils
import com.repo.github.expandablelistviewtest.R
import com.repo.github.expandablelistviewtest.VariantCategoryApplication
import com.repo.github.expandablelistviewtest.api.VariantApis
import com.repo.github.expandablelistviewtest.databinding.ActivityVariantCatoryBinding
import com.repo.github.expandablelistviewtest.interfaces.GetView
import com.repo.github.expandablelistviewtest.repository.VariantRepository
import com.repo.github.expandablelistviewtest.viewmodel.VariantCategoryViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class VariantCategoryActivity : AppCompatActivity(), GetView {

    private lateinit var variantCategoryAdapter: VariantCategoryAdapter
    private lateinit var variantCategoryViewModel: VariantCategoryViewModel
    private lateinit var repository: VariantRepository
    private lateinit var activityVariantCategoryBinding: ActivityVariantCatoryBinding
    private val disposable = CompositeDisposable()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        variantCategoryViewModel = ViewModelProviders.of(this).get(VariantCategoryViewModel::class.java)

        activityVariantCategoryBinding = DataBindingUtil.setContentView(this, R.layout.activity_variant_catory)
        activityVariantCategoryBinding.viewModel = variantCategoryViewModel
        repository = VariantRepository(VariantCategoryApplication.getRetrofitInstance().create(VariantApis::class.java))
        variantCategoryAdapter = VariantCategoryAdapter(this)
        activityVariantCategoryBinding.recyclerView.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false)
        activityVariantCategoryBinding.recyclerView.adapter = variantCategoryAdapter
        getVariants()
    }


    private fun getVariants() {
        if (ConnectionUtils.isNetConnected(this)) {
            disposable.add(variantCategoryViewModel.loadData(repository)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    {
                                        variantCategoryViewModel.handleSuccessResponse()
                                        variantCategoryAdapter.setItems(it.variants)
                                    },
                                    {
                                        variantCategoryViewModel.handleFailedResponse()
                                        variantCategoryViewModel.noResultState.set(true)
                                    }
                            )
            )
        } else {
            variantCategoryViewModel.handleNoInternetResponse()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    override fun getViewByTag(id: String): RadioButton? =
            activityVariantCategoryBinding.recyclerView.findViewWithTag(id)

}
