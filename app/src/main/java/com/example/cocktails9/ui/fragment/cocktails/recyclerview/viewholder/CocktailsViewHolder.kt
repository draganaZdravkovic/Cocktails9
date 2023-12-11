package com.example.cocktails9.ui.fragment.cocktails.recyclerview.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cocktails9.R
import com.example.cocktails9.data.model.Cocktails
import com.example.cocktails9.databinding.RecyclerViewItemBinding
import com.example.cocktails9.ui.fragment.favorites.viewmodel.FavoritesViewModel

class CocktailsViewHolder(
    private val itemBinding: RecyclerViewItemBinding,
    private val favoritesViewModel: FavoritesViewModel
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

        itemBinding.ivFavorite.setOnClickListener {
            if (cocktails.isFavorite) {
                favoritesViewModel.removeFavorite(cocktails)
                Glide.with(itemView).load(R.drawable.ic_fav_off_background)
                    .into(itemBinding.ivFavorite)
            } else {
                favoritesViewModel.addFavorite(cocktails.copy(isFavorite = true))
                Glide.with(itemView).load(R.drawable.ic_fav_on)
                    .into(itemBinding.ivFavorite)
            }
            cocktails.isFavorite = !cocktails.isFavorite
        }
    }
}


