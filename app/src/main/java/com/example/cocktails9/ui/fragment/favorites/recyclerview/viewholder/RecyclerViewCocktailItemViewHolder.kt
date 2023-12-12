package com.example.cocktails9.ui.fragment.favorites.recyclerview.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cocktails9.data.model.Cocktails
import com.example.cocktails9.databinding.RecyclerViewFavoriteItemBinding

class RecyclerViewCocktailItemViewHolder(private val itemBinding: RecyclerViewFavoriteItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bindItem(cocktails: Cocktails) {
        itemBinding.tvCocktailName.text = cocktails.name
        Glide.with(itemView).load(cocktails.image).into(itemBinding.imCocktail)
    }
}