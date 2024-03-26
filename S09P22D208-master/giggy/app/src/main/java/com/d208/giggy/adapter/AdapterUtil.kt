package com.d208.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.d208.domain.model.DomainBeggerRank
import com.d208.domain.model.DomainComment
import com.d208.domain.model.DomainGameRank
import com.d208.domain.model.DomainPost
import com.d208.domain.model.DomainTransaction

object AdapterUtil {

    val diffUtilTransaction = object : DiffUtil.ItemCallback<DomainTransaction>() {
        override fun areItemsTheSame(oldItem: DomainTransaction, newItem: DomainTransaction): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DomainTransaction, newItem: DomainTransaction): Boolean {
            return oldItem == newItem
        }
    }

    val diffUtilPost = object : DiffUtil.ItemCallback<DomainPost>() {
        override fun areItemsTheSame(oldItem: DomainPost, newItem: DomainPost): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DomainPost, newItem: DomainPost): Boolean {
            return oldItem == newItem
        }
    }

    val diffUtilComment = object : DiffUtil.ItemCallback<DomainComment>() {
        override fun areItemsTheSame(oldItem: DomainComment, newItem: DomainComment): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DomainComment, newItem: DomainComment): Boolean {
            return oldItem == newItem
        }

    }

    val diffUtilGameRank = object : DiffUtil.ItemCallback<DomainGameRank>() {
        override fun areItemsTheSame(oldItem: DomainGameRank, newItem: DomainGameRank): Boolean {
            return oldItem.nickname == newItem.nickname
        }

        override fun areContentsTheSame(oldItem: DomainGameRank, newItem: DomainGameRank): Boolean {
            return oldItem == newItem
        }

    }

    val diffUtilBeggerRank = object : DiffUtil.ItemCallback<DomainBeggerRank>() {
        override fun areItemsTheSame(oldItem: DomainBeggerRank, newItem: DomainBeggerRank): Boolean {
            return oldItem.nickname == newItem.nickname
        }

        override fun areContentsTheSame(oldItem: DomainBeggerRank, newItem: DomainBeggerRank): Boolean {
            return oldItem == newItem
        }

    }
}