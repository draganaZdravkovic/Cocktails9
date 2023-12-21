package com.example.cocktails9.ui.fragment.favorites.recyclerview.viewholder

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails9.R
import com.example.cocktails9.databinding.RecyclerViewCategoryItemBinding

class RecyclerViewCategoryItemViewHolder(
    private val itemBinding: RecyclerViewCategoryItemBinding,
    private val resources: Resources
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bindItem(category: String?) {
        itemBinding.tvCategory.text = resources.getString(R.string.text_with_end_space, category)
    }
}