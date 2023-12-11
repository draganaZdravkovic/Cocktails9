package com.example.cocktails9.ui.fragment.cocktails.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.cocktails9.data.model.Cocktails
import com.example.cocktails9.databinding.RecyclerViewItemBinding
import com.example.cocktails9.ui.fragment.cocktails.recyclerview.viewholder.CocktailsViewHolder

class CocktailsAdapter(private val listener: FavoriteClickListener) :
    ListAdapter<Cocktails, CocktailsViewHolder>(CocktailsDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailsViewHolder {
        return CocktailsViewHolder(
            RecyclerViewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            listener, currentList
        )
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

    interface FavoriteClickListener {
        fun onFavoriteClick(cocktail: Cocktails, toAdd: Boolean)
    }
}

