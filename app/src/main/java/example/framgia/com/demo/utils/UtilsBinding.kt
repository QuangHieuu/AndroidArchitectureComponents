package example.framgia.com.demo.utils

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import example.framgia.com.demo.screen.main.ItemAdapter

class UtilsBinding {
    companion object {
        @JvmStatic
        @BindingAdapter("android:setAdapter")
        fun setAdapter(recyclerView: RecyclerView, itemAdapter: ItemAdapter) {
            recyclerView.adapter = itemAdapter
        }
    }
}