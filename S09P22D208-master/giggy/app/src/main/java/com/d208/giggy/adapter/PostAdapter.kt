package com.d208.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.d208.domain.model.DomainPost
import com.d208.domain.utils.StringFormatUtil
import com.d208.giggy.R
import com.d208.giggy.databinding.ItemPostBinding


class PostAdapter (var context : Context) : ListAdapter<DomainPost, PostAdapter.ItemViewHolder>(AdapterUtil.diffUtilPost) {
    inner class ItemViewHolder (var binding : ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data : DomainPost) = with(binding){
            itemPostWriterName.text = data.nickName
            itemPostWriteTime.text = StringFormatUtil.dateToString(data.createdAt)
            itemPostTitle.text = data.title
            if(data.likePost){
                itemPostLike.setImageResource(R.drawable.ic_heart_color)
            }
            else{
                itemPostLike.setImageResource(R.drawable.heart_transparent)
            }
            when(data.postType){
                "FREE" -> itemPostTab.text = "[자유]"
                "TIP" -> itemPostTab.text = "[꿀팁]"
                "BOAST" -> itemPostTab.text = "[자랑]"
            }


            root.setOnClickListener {
                itemClickListener.onClick(binding, layoutPosition, data)
            }
            itemPostLike.setOnClickListener {
                if(!data.likePost){
                    itemPostLike.setImageResource(R.drawable.ic_heart_color)
                    itemHeartListener.onClick(binding, layoutPosition, data)
                }
                else{
                    itemPostLike.setImageResource(R.drawable.heart_transparent)
                    itemHeartListener.onClick(binding, layoutPosition, data)
                }

            }



        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemPostBinding.inflate(
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

    lateinit var itemHeartListener: ItemClickListener
    interface ItemClickListener {
        fun onClick(binding: ItemPostBinding, position: Int, data: DomainPost)
    }


}