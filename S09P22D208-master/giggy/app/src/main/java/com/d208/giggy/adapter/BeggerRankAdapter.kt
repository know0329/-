package com.d208.giggy.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.d208.domain.model.DomainBeggerRank
import com.d208.giggy.R
import com.d208.giggy.databinding.ItemGameRankBinding
import com.d208.presentation.adapter.AdapterUtil

class BeggerRankAdapter : ListAdapter<DomainBeggerRank, BeggerRankAdapter.ItemViewHolder>(AdapterUtil.diffUtilBeggerRank) {

    inner class ItemViewHolder(val binding : ItemGameRankBinding)  : RecyclerView.ViewHolder(binding.root){
        fun bind(data : DomainBeggerRank, rank : Int){
            binding.itemUserNickName.text = data.nickname
            binding.itemScore.text = "${String.format("%.3f ", data.ratio * 100)} %"
            when(rank){
                0 -> {
                    binding.itemRankMedalImageView.visibility = View.VISIBLE
                    binding.itemRankMedalImageView.setImageResource(R.drawable.ic_gold_medal)
                    binding.itemRank.visibility = View.GONE

                }
                1 -> {
                    binding.itemRankMedalImageView.visibility = View.VISIBLE
                    binding.itemRankMedalImageView.setImageResource(R.drawable.ic_silver_medal)
                    binding.itemRank.visibility = View.GONE
                }
                2 -> {
                    binding.itemRankMedalImageView.visibility = View.VISIBLE
                    binding.itemRankMedalImageView.setImageResource(R.drawable.ic_bronze_medal)
                    binding.itemRank.visibility = View.GONE
                }
                else -> {
                    binding.itemRankMedalImageView.visibility = View.GONE
                    binding.itemRank.visibility = View.VISIBLE
                    binding.itemRank.text = "${rank+1} 위"
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
        holder.bind(currentList[position], position)
    }
}