package com.sunil.kumar.ui.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class PostListDividerDecoration : RecyclerView.ItemDecoration {

    private val offset: Int

    constructor(offset: Int) : super() {
        this.offset = offset
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        outRect.top = 0
        outRect.bottom = offset
    }
}