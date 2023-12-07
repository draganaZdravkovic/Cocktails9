package com.example.cocktails9.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cocktails9.databinding.RecyclerViewCategoryItemBinding
import com.example.cocktails9.databinding.RecyclerViewFavoriteItemBinding
import com.example.cocktails9.model.Category
import com.example.cocktails9.model.Cocktails
import com.example.cocktails9.model.FavoritesItem

class FavoritesAdapter :
    ListAdapter<FavoritesItem, RecyclerView.ViewHolder>(CocktailsDiffCallback()) {

    class RecyclerItemViewHolder(private val itemBinding: RecyclerViewFavoriteItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bindItem(cocktails: Cocktails) {
            itemBinding.tvCocktailName.text = cocktails.name
            Glide.with(itemView).load(cocktails.image).into(itemBinding.imCocktail)
        }
    }

    class RecyclerViewCategoryItemViewHolder(private val itemBinding: RecyclerViewCategoryItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bindItem(category: String) {
            itemBinding.tvCategory.text = category
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            FavoritesItem.Type.COCKTAIL.ordinal -> {
                RecyclerItemViewHolder(
                    RecyclerViewFavoriteItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            FavoritesItem.Type.CATEGORY.ordinal -> {
                RecyclerViewCategoryItemViewHolder(
                    RecyclerViewCategoryItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).getType()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RecyclerItemViewHolder -> {
                val cocktails = getItem(position) as Cocktails
                holder.bindItem(cocktails)
            }
            is RecyclerViewCategoryItemViewHolder -> {
                val category = getItem(position) as Category
                holder.bindItem(category.category)
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        (recyclerView.layoutManager as GridLayoutManager).spanSizeLookup = getSpanSizeLookup()
    }

    private fun getSpanSizeLookup(): GridLayoutManager.SpanSizeLookup {
        return object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (getItem(position).getType()) {
                    FavoritesItem.Type.COCKTAIL.ordinal -> 1
                    FavoritesItem.Type.CATEGORY.ordinal -> 2
                    else -> 2
                }
            }
        }
    }

    // TODO(implement comparing logic for different subclasses)
    class CocktailsDiffCallback : DiffUtil.ItemCallback<FavoritesItem>() {
        override fun areItemsTheSame(oldItem: FavoritesItem, newItem: FavoritesItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: FavoritesItem, newItem: FavoritesItem): Boolean {
            return oldItem == newItem
        }
    }
}