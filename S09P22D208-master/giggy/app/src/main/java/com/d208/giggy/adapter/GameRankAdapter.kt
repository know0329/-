package com.d208.giggy.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.d208.domain.model.DomainGameRank
import com.d208.giggy.R
import com.d208.giggy.databinding.ItemCommentBinding
import com.d208.giggy.databinding.ItemGameRankBinding
import com.d208.presentation.adapter.AdapterUtil

class GameRankAdapter(val context : Context) : ListAdapter<DomainGameRank, GameRankAdapter.ItemViewHolder>(
    AdapterUtil.diffUtilGameRank) {

    inner class ItemViewHolder(val binding : ItemGameRankBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data : DomainGameRank){
            binding.itemUserNickName.text = data.nickname
            binding.itemScore.text = "${data.score} 점"
            when(data.rank - 1){
                1-> {
                    binding.itemRank.visibility = View.GONE
                    binding.itemRankMedalImageView.visibility = View.VISIBLE
                    binding.itemRankMedalImageView.setImageResource(R.drawable.ic_gold_medal)
                }
                2 -> {
                    binding.itemRank.visibility = View.GONE
                    binding.itemRankMedalImageView.visibility = View.VISIBLE
                    binding.itemRankMedalImageView.setImageResource(R.drawable.ic_silver_medal)
                }
                3 -> {
                    binding.itemRank.visibility = View.GONE
                    binding.itemRankMedalImageView.visibility = View.VISIBLE
                    binding.itemRankMedalImageView.setImageResource(R.drawable.ic_bronze_medal)
                }
                else -> {
                    binding.itemRank.visibility = View.VISIBLE
                    binding.itemRankMedalImageView.visibility = View.GONE
                    binding.itemRank.text = "${data.rank - 1} 위"
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemGameRankBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

}