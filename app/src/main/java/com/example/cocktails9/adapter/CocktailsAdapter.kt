package com.example.cocktails9.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cocktails9.databinding.RecyclerViewItemBinding
import com.example.cocktails9.model.Cocktails

class CocktailsAdapter(private var cocktailsList: List<Cocktails>) :
    RecyclerView.Adapter<CocktailsAdapter.CocktailsViewHolder>() {

    class CocktailsViewHolder(var itemBinding: RecyclerViewItemBinding) :
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
        val cocktails = cocktailsList[position]
        holder.bindItem(cocktails)
    }

    override fun getItemCount(): Int {
        return cocktailsList.size
    }
}