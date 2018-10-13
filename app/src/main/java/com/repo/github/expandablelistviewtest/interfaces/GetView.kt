package com.repo.github.expandablelistviewtest.interfaces

import android.widget.RadioButton

interface GetView {
    fun getViewByTag(id: String): RadioButton?
}
