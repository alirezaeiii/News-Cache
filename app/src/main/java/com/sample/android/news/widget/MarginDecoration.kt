package com.sample.android.news.widget

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sample.android.news.R

class MarginDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val margin: Int = context.resources.getDimensionPixelSize(R.dimen.spacing_small)

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(margin, margin, margin, margin)
    }
}