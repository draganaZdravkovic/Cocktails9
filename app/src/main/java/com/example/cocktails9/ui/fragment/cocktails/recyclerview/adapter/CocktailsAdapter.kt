package com.example.cocktails9.ui.fragment.cocktails.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.cocktails9.R
import com.example.cocktails9.data.model.Cocktails
import com.example.cocktails9.databinding.RecyclerViewItemBinding
import com.example.cocktails9.ui.fragment.cocktails.recyclerview.viewholder.CocktailsViewHolder

class CocktailsAdapter :
    ListAdapter<Cocktails, CocktailsViewHolder>(CocktailsDiffCallback()) {

    var onFavoriteClickListener: ((cocktail: Cocktails, position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailsViewHolder {
        val itemBinding = RecyclerViewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        return CocktailsViewHolder(itemBinding).apply {
            onViewHolderCreated(this, itemBinding)
        }
    }

    private fun onViewHolderCreated(
        cocktailsViewHolder: CocktailsViewHolder,
        binding: RecyclerViewItemBinding
    ) {
        binding.root.setOnClickListener {
            val item = getItem(cocktailsViewHolder.bindingAdapterPosition)
            val imgRes =
                if (item.isFavorite) R.drawable.ic_fav_off_background
                else R.drawable.ic_fav_on
            Glide.with(binding.ivFavorite.context).load(imgRes)
                .into(binding.ivFavorite)

            onFavoriteClickListener?.invoke(
                getItem(cocktailsViewHolder.bindingAdapterPosition),
                cocktailsViewHolder.bindingAdapterPosition
            )
        }
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

