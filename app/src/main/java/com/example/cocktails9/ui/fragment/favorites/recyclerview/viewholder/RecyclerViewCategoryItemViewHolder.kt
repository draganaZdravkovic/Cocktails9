package com.example.cocktails9.ui.fragment.favorites.recyclerview.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails9.databinding.RecyclerViewCategoryItemBinding

class RecyclerViewCategoryItemViewHolder(private val itemBinding: RecyclerViewCategoryItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    //   Add one space at the end of the word, because the font is cutting the last letter
    fun bindItem(category: String?) {
        itemBinding.tvCategory.text = buildString {
            append(category)
            append(" ")
        }
    }
}