package com.d208.giggy.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.d208.domain.model.DomainComment
import com.d208.domain.utils.StringFormatUtil
import com.d208.giggy.databinding.ItemCommentBinding
import com.d208.presentation.adapter.AdapterUtil
import java.util.UUID

class CommentAdapter (var context : Context, val userId : UUID) : ListAdapter<DomainComment, CommentAdapter.ItemViewHolder>(
    AdapterUtil.diffUtilComment
){
    inner class ItemViewHolder (var binding : ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data : DomainComment){
            binding.itemCommentContent.text = data.content
            binding.itemCommentWriterName.text = data.nickName
            binding.itemCommentWriterTime.text = StringFormatUtil.dateTimeToString(data.createdAt)
            if(userId == data.userId){

                binding.itemCommentErase.visibility = View.VISIBLE
            }
            binding.itemCommentErase.setOnClickListener {
                itemClickListener.onClick(binding, layoutPosition, data)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
    lateinit var itemClickListener: ItemClickListener
    interface ItemClickListener {
        fun onClick(binding: ItemCommentBinding, position: Int, data: DomainComment)
    }
}