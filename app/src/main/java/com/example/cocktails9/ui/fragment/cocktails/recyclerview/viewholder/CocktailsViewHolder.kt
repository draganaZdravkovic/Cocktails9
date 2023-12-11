package com.example.cocktails9.ui.fragment.cocktails.recyclerview.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cocktails9.R
import com.example.cocktails9.data.model.Cocktails
import com.example.cocktails9.databinding.RecyclerViewItemBinding

class CocktailsViewHolder(
    private val itemBinding: RecyclerViewItemBinding
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bindItem(cocktails: Cocktails) {
        itemBinding.tvCocktailName.text = cocktails.name
        Glide.with(itemView).load(cocktails.image).into(itemBinding.imCocktail)
        if (cocktails.isFavorite) {
            Glide.with(itemView).load(R.drawable.ic_fav_on).into(itemBinding.ivFavorite)
        } else {
            Glide.with(itemView).load(R.drawable.ic_fav_off_background)
                .into(itemBinding.ivFavorite)
        }
    }
}


