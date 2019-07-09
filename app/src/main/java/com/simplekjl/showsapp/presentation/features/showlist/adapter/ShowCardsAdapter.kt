package com.simplekjl.showsapp.presentation.features.showlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.simplekjl.showsapp.data.model.Show
import com.simplekjl.showsapp.databinding.ShowCardItemBinding

class ShowCardsAdapter(
    private var showList: List<Show>,
    private val listener: ItemClickListener
) : RecyclerView.Adapter<ShowCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowCardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ShowCardItemBinding.inflate(inflater, parent, false)

        return ShowCardViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = showList.size

    override fun onBindViewHolder(holder: ShowCardViewHolder, position: Int) {
        holder.setItem(showList[position])
    }
}