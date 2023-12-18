package com.example.cocktails9.ui.fragment.cocktails.recyclerview.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.cocktails9.R
import com.example.cocktails9.data.model.Cocktails
import com.example.cocktails9.databinding.RecyclerViewCocktailItemBinding
import com.example.cocktails9.ui.fragment.cocktails.recyclerview.viewholder.CocktailsViewHolder

class CocktailsAdapter(private val resources: Resources) :
    ListAdapter<Cocktails, CocktailsViewHolder>(CocktailsDiffCallback()) {

    var onFavoriteClickListener: ((cocktail: Cocktails) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailsViewHolder {
        val itemBinding = RecyclerViewCocktailItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CocktailsViewHolder(itemBinding, resources).apply {
            onViewHolderCreated(this, itemBinding)
        }
    }

    private fun onViewHolderCreated(
        cocktailsViewHolder: CocktailsViewHolder,
        binding: RecyclerViewCocktailItemBinding
    ) {
        binding.root.setOnClickListener {
            val item = getItem(cocktailsViewHolder.bindingAdapterPosition)
            val imgRes =
                if (item.isFavorite) R.drawable.ic_fav_off_background
                else R.drawable.ic_fav_on

            setImage(binding.ivFavorite, imgRes)

            onFavoriteClickListener?.invoke(item)
        }
    }

    private fun setImage(ivFavorite: ImageView, imgRes: Int) {
        Glide.with(ivFavorite.context).load(imgRes)
            .into(ivFavorite)
    }

    override fun onBindViewHolder(holder: CocktailsViewHolder, position: Int) {
        val cocktail = getItem(position)
        holder.bindItem(cocktail)
    }


    class CocktailsDiffCallback : DiffUtil.ItemCallback<Cocktails>() {
        override fun areItemsTheSame(oldItem: Cocktails, newItem: Cocktails): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cocktails, newItem: Cocktails): Boolean {
            return oldItem == newItem
        }
    }
}

