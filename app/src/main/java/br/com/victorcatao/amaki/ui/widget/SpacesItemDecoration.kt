package br.com.victorcatao.amaki.ui.widget

import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

class SpacesItemDecoration(private val verticalSpacing: Int, private val horizontalSpacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        addSpaceToView(outRect, parent.getChildAdapterPosition(view), parent)
    }

    private fun addSpaceToView(outRect: Rect, position: Int, parent: RecyclerView) {
        val grid = parent.layoutManager as GridLayoutManager

        val spanSize = grid.spanSizeLookup.getSpanSize(position)
        val spanCount = grid.spanCount
        if (spanSize == spanCount) {
            outRect.right = horizontalSpacing
        } else {
            val spanIndex = grid.spanSizeLookup.getSpanIndex(position, spanCount)
            if (spanCount - spanSize == spanIndex) {
                outRect.right = horizontalSpacing
            }
        }
        outRect.left = horizontalSpacing
        outRect.top = verticalSpacing

//        val itemCount = parent.adapter.itemCount
//        Log.d("SPACES", "\nspanCount: $spanCount \nspanSize: $spanSize \nposition: $position\nitemcount: $itemCount")
//        if (position in (itemCount - spanCount) until itemCount) {
//            outRect.bottom = verticalSpacing
//        }
    }
}