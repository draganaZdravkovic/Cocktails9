package com.example.cocktails9.ui.fragment.cocktails.recyclerview.viewholder

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cocktails9.R
import com.example.cocktails9.data.model.Cocktails
import com.example.cocktails9.databinding.RecyclerViewCocktailItemBinding

class CocktailsViewHolder(
    private val itemBinding: RecyclerViewCocktailItemBinding
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bindItem(cocktails: Cocktails) {
        itemBinding.tvCocktailName.text = cocktails.name
        Glide.with(itemView).load(cocktails.image).into(itemBinding.imCocktail)
        if (cocktails.isFavorite) setImage(itemBinding.ivFavorite, R.drawable.ic_fav_on)
        else setImage(itemBinding.ivFavorite, R.drawable.ic_fav_off_background)
    }

    private fun setImage(ivFavorite: ImageView, imgRes: Int) {
        Glide.with(ivFavorite.context).load(imgRes)
            .into(ivFavorite)
    }
}


