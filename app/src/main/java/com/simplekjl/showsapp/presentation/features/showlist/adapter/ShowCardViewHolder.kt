package com.simplekjl.showsapp.presentation.features.showlist.adapter

import androidx.recyclerview.widget.RecyclerView
import com.simplekjl.showsapp.data.model.Show
import com.simplekjl.showsapp.databinding.ShowCardItemBinding

class ShowCardViewHolder(
    private val binding: ShowCardItemBinding,
    private val listener: ItemClickListener
) : RecyclerView.ViewHolder(binding.root) {
    private val mListener: ItemClickListener = listener

    fun setItem(show: Show) {
        binding.show = show
    }
}