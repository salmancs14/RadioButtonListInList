package com.repo.github.expandablelistviewtest.views

import android.view.View
import com.repo.github.expandablelistviewtest.entity.ExclusionItem
import com.repo.github.expandablelistviewtest.interfaces.GetView

class VariantCategoryClickHandler(private val disabledRadioButtons: ArrayList<String> = ArrayList(),
                                  private val selectedRadioButtons: ArrayList<String> = ArrayList()) {


    fun handleButtonClick(view: View, excludeList: ArrayList<ArrayList<ExclusionItem>>,
                          getViewListener: GetView) {
        val ids = view.tag.toString()
        val variationId = ids.split(",")[0]
        val groupId = ids.split(",")[1]
        selectedRadioButtons.add(ids)
        removeSelectedGroups(groupId, variationId)
        unSelectRadioButtons(getViewListener, excludeList)
        excludeList.forEach { exclusion ->
            for (exclusionData in exclusion) {
                if (exclusionData.groupId!!.equals(groupId, ignoreCase = true) &&
                        exclusionData.variationId!!.equals(variationId, ignoreCase = true)) {
                    markOtherRadioButtonDisable(exclusion, groupId, variationId, getViewListener)
                }
            }
        }
    }

    private fun removeSelectedGroups(groupId : String, variationId : String) {
        val removedIds = ArrayList<String>()
        selectedRadioButtons.forEach {
            if (it.endsWith(",$groupId") && !it.equals("$variationId,$groupId")) {
                removedIds.add(it)
            }
        }
        removedIds.forEach {
            selectedRadioButtons.remove(it)
        }
    }

    private fun unSelectRadioButtons(getViewListener: GetView, excludeList: ArrayList<ArrayList<ExclusionItem>>) {
        val removedIds = ArrayList<String>()
        disabledRadioButtons.forEach {
            val ids = it.split(",")
            if (!checkOtherIsSelected(ids[1], ids[0], excludeList)) {
                getViewListener.getViewByTag(ids[0] + "," + ids[1])?.let { radioButton ->
                    radioButton.isEnabled = true
                    removedIds.add(it)
                }
            }
        }
        removedIds.forEach {
            disabledRadioButtons.remove(it)
        }
    }

    private fun markOtherRadioButtonDisable(excludeList: List<ExclusionItem>,
                                            clickedGroupId: String, clickedVariationId: String,
                                            getViewListener: GetView) {

        for ((groupId, variationId) in excludeList) {
            if (!groupId!!.equals(clickedGroupId, ignoreCase = true) ||
                    !variationId!!.equals(clickedVariationId, ignoreCase = true)) {
                disabledRadioButtons.add("$variationId,$groupId")
                getViewListener.getViewByTag("$variationId,$groupId")?.let { radioButton ->
                    radioButton.isEnabled = false
                }
            }
        }
    }


    private fun checkOtherIsSelected(groupId: String, variationId: String,
                                     excludeList: ArrayList<ArrayList<ExclusionItem>>): Boolean {
        excludeList.forEach {
            val exclusionItems = it
            var existed = false
            for (exclusionItem in exclusionItems) {
                if (exclusionItem.groupId.equals(groupId, ignoreCase = true)
                        && exclusionItem.variationId.equals(variationId, ignoreCase = true)) {
                    existed = true
                }
            }
            if (existed) {
                for (exclusionItem in exclusionItems) {
                    if (!exclusionItem.groupId.equals(groupId, ignoreCase = true)
                            && !exclusionItem.variationId.equals(variationId, ignoreCase = true)) {
                        if (selectedRadioButtons.contains(exclusionItem.variationId + "," + exclusionItem.groupId)) {
                            return true
                        }
                    }
                }
            }
        }
        return false
    }

    fun isSelected(id: String) = selectedRadioButtons.contains(id)

    fun isDisabled(id: String) = disabledRadioButtons.contains(id)

}
