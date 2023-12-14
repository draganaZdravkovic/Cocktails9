package com.example.cocktails9.ui.fragment.filter.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.cocktails9.databinding.FilterListItemBinding
import com.example.cocktails9.ui.fragment.filter.recyclerview.viewholder.SpecificFilterViewHolder

class SpecificFilterAdapter :
    ListAdapter<String, SpecificFilterViewHolder>(SpecificFilterDiffCallback()) {

    var onItemClickListener: ((item: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecificFilterViewHolder {
        val itemBinding = FilterListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SpecificFilterViewHolder(itemBinding).apply {
            onViewHolderCrated(this, itemBinding)
        }
    }

    private fun onViewHolderCrated(
        specificFilterViewHolder: SpecificFilterViewHolder,
        itemBinding: FilterListItemBinding
    ) {
        itemBinding.root.setOnClickListener {
            val item = getItem(specificFilterViewHolder.bindingAdapterPosition)
            onItemClickListener?.invoke(item)
        }
    }


    override fun onBindViewHolder(holder: SpecificFilterViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindItem(item)
    }

    class SpecificFilterDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.contentEquals(newItem)
        }
    }
}