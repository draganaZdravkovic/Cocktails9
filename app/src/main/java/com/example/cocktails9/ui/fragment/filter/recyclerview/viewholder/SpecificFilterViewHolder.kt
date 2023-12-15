package com.example.cocktails9.ui.fragment.filter.recyclerview.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cocktails9.databinding.FilterListItemBinding

class SpecificFilterViewHolder(private val itemBinding: FilterListItemBinding) :
    ViewHolder(itemBinding.root) {

    //   Add one space at the end of the word, because the font is cutting the last letter
    fun bindItem(item: String) {
        itemBinding.tvFilterItem.text = buildString {
            append(item)
            append(" ")
        }
        itemBinding.ivForwardArrow.visibility = View.GONE
    }
}