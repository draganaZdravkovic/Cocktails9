package com.example.cocktails9.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cocktails9.databinding.RecyclerViewItemBinding
import com.example.cocktails9.model.Cocktails

class CocktailsAdapter :
    ListAdapter<Cocktails, CocktailsAdapter.CocktailsViewHolder>(CocktailsDiffCallback()) {

    class CocktailsViewHolder(private val itemBinding: RecyclerViewItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bindItem(cocktails: Cocktails) {
            itemBinding.tvCocktailName.text = cocktails.name
            Glide.with(itemView).load(cocktails.image).into(itemBinding.imCocktail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailsViewHolder {
        return CocktailsViewHolder(
            RecyclerViewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CocktailsViewHolder, position: Int) {
        val cocktails = getItem(position)
        holder.bindItem(cocktails)
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

