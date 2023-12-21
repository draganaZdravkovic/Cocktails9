package com.example.cocktails9.ui.fragment.favorites.recyclerview.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails9.data.model.Category
import com.example.cocktails9.data.model.Cocktails
import com.example.cocktails9.data.model.FavoritesItem
import com.example.cocktails9.databinding.RecyclerViewCategoryItemBinding
import com.example.cocktails9.databinding.RecyclerViewFavoriteItemBinding
import com.example.cocktails9.ui.fragment.favorites.recyclerview.viewholder.RecyclerViewCategoryItemViewHolder
import com.example.cocktails9.ui.fragment.favorites.recyclerview.viewholder.RecyclerViewCocktailItemViewHolder

class FavoritesAdapter(private val resources: Resources) :
    ListAdapter<FavoritesItem, RecyclerView.ViewHolder>(CocktailsDiffCallback()) {

    var onItemClickListener: ((cocktail: Cocktails) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            FavoritesItem.Type.COCKTAIL.ordinal -> {
                val itemBinding =
                    RecyclerViewFavoriteItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return RecyclerViewCocktailItemViewHolder(
                    itemBinding,
                    resources
                ).apply { onCocktailViewHolderCreated(this, itemBinding) }
            }
            FavoritesItem.Type.CATEGORY.ordinal -> {
                val itemBinding = RecyclerViewCategoryItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return RecyclerViewCategoryItemViewHolder(itemBinding, resources)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    private fun onCocktailViewHolderCreated(
        holder: RecyclerView.ViewHolder,
        binding: RecyclerViewFavoriteItemBinding
    ) {
        binding.root.setOnClickListener {
            when (holder) {
                is RecyclerViewCocktailItemViewHolder -> {
                    val item = getItem(holder.bindingAdapterPosition) as Cocktails
                    onItemClickListener?.invoke(item)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).getType()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RecyclerViewCocktailItemViewHolder -> {
                val cocktails = getItem(position) as Cocktails
                holder.bindItem(cocktails)
            }
            is RecyclerViewCategoryItemViewHolder -> {
                val category = getItem(position) as Category
                holder.bindItem(category.alcoholic)
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

    class CocktailsDiffCallback : DiffUtil.ItemCallback<FavoritesItem>() {
        override fun areItemsTheSame(oldItem: FavoritesItem, newItem: FavoritesItem): Boolean {
            return oldItem.getType() == newItem.getType() &&
                    when (oldItem) {
                        is Cocktails -> oldItem.id == (newItem as? Cocktails)?.id
                        is Category -> oldItem.alcoholic == (newItem as? Category)?.alcoholic
                    }
        }

        override fun areContentsTheSame(
            oldItem: FavoritesItem,
            newItem: FavoritesItem
        ): Boolean {
            return oldItem == newItem
        }
    }
}