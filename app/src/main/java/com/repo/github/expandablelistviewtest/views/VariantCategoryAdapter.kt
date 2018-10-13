package com.repo.github.expandablelistviewtest.views

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.repo.github.expandablelistviewtest.R
import com.repo.github.expandablelistviewtest.entity.ExclusionItem
import com.repo.github.expandablelistviewtest.entity.VariantEntity
import com.repo.github.expandablelistviewtest.entity.VariantGroup
import com.repo.github.expandablelistviewtest.interfaces.GetView

class VariantCategoryAdapter(
        private var getViewListener: GetView,
        private val variantCategoryClickHandler: VariantCategoryClickHandler = VariantCategoryClickHandler() ,
        private var variantGroups: ArrayList<VariantGroup> = ArrayList(),
        private var excludeList: ArrayList<ArrayList<ExclusionItem>> = ArrayList()) :
        RecyclerView.Adapter<VariantCategoryAdapter.ViewHolder>() {


    fun setItems(variantEntity: VariantEntity?) {
        variantEntity?.let {
            variantGroups = it.variantGroups
            excludeList = it.excludeList
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent,
                false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val group = RadioGroup(holder.linearLayout.context)
        group.orientation = RadioGroup.VERTICAL
        variantGroups[position].variations.forEach { variation ->
            val btn = RadioButton(holder.linearLayout.context)
            group.addView(btn)
            group.tag = variantGroups[position].groupId
            btn.tag = variation.id + "," + variantGroups[position].groupId
            val isAvailable = !variation.inStock!!.equals("0", ignoreCase = true)
            val availability = if (isAvailable) "Available" else " Not Available"
            val dataToBeShown = variation.name + ", Rs." + variation.price + ", " + availability
            btn.text = dataToBeShown
            btn.isSelected = variantCategoryClickHandler.isSelected(variation.id + "," + variantGroups[position].groupId)
            btn.isEnabled = !variantCategoryClickHandler.isDisabled(variation.id + "," + variantGroups[position].groupId)
            btn.setOnClickListener {
                variantCategoryClickHandler.handleButtonClick(it, excludeList, getViewListener)
            }
        }
        holder.categoryTextView.text = variantGroups[position].name
        holder.linearLayout.addView(group)
    }

    override fun getItemCount(): Int {
        return variantGroups.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var linearLayout: LinearLayout
        var categoryTextView: TextView

        init {
            linearLayout = itemView.findViewById(R.id.item_name)
            categoryTextView = itemView.findViewById(R.id.category_name)
        }
    }
}
