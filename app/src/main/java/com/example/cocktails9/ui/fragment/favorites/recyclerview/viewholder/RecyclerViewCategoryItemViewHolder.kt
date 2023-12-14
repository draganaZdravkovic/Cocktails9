package com.example.cocktails9.ui.fragment.favorites.recyclerview.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails9.databinding.RecyclerViewCategoryItemBinding

class RecyclerViewCategoryItemViewHolder(private val itemBinding: RecyclerViewCategoryItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bindItem(category: String?) {
        itemBinding.tvCategory.text = category
    }
}