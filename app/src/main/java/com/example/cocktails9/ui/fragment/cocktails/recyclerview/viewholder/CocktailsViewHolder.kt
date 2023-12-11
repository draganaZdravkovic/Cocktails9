package com.example.cocktails9.ui.fragment.cocktails.recyclerview.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cocktails9.R
import com.example.cocktails9.data.model.Cocktails
import com.example.cocktails9.databinding.RecyclerViewItemBinding
import com.example.cocktails9.ui.fragment.cocktails.recyclerview.adapter.CocktailsAdapter

class CocktailsViewHolder(
    private val itemBinding: RecyclerViewItemBinding,
    private val listener: CocktailsAdapter.FavoriteClickListener,
    private val list: List<Cocktails>
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    init {
        itemBinding.ivFavorite.setOnClickListener {
            val position = absoluteAdapterPosition
            val toAdd: Boolean
            if (position != RecyclerView.NO_POSITION) {
                val cocktail = list[position]
                if (cocktail.isFavorite) {
                    toAdd = false
                    Glide.with(itemView).load(R.drawable.ic_fav_off_background)
                        .into(itemBinding.ivFavorite)
                } else {
                    toAdd = true
                    Glide.with(itemView).load(R.drawable.ic_fav_on)
                        .into(itemBinding.ivFavorite)
                }
                cocktail.isFavorite = !cocktail.isFavorite
                listener.onFavoriteClick(cocktail, toAdd)
            }
        }
    }


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


