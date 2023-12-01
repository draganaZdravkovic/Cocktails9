package com.example.cocktails9

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails9.databinding.RecyclerViewItemBinding

class CocktailsAdapter(private var cocktailsList: MutableList<Cocktails>) :
    RecyclerView.Adapter<CocktailsAdapter.CocktailsViewHolder>() {

    class CocktailsViewHolder(private val itemBinding: RecyclerViewItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bindItem(cocktails: Cocktails) {
            itemBinding.imCocktail.setImageResource(cocktails.image)
            itemBinding.tvCocktailName.text = cocktails.name
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