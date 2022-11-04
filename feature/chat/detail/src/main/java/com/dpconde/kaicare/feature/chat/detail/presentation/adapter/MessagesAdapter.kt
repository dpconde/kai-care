package com.dpconde.kaicare.feature.chat.detail.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dpconde.kaicare.core.commons.domain.ChatMessage
import com.dpconde.kaicare.feature.chat.detail.R
import com.dpconde.kaicare.feature.chat.detail.databinding.ChatIncomeMessageItemBinding
import com.dpconde.kaicare.feature.chat.detail.databinding.ChatOutomeMessageItemBinding
import com.dpconde.kaicare.feature.chat.detail.presentation.ChatDetailViewModel

class MessagesAdapter (private val viewModel: ChatDetailViewModel):
    ListAdapter<ChatMessage, MessagesAdapter.ViewHolderBase>(MessageDiffUtil()) {

    override fun onBindViewHolder(holder: ViewHolderBase, position: Int) {
        val messageThread = getItem(position)
        holder.bind(messageThread)
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position).isMine(viewModel.getSessionUserId())){ //TODO
            true -> 0
            else -> 1
        }
    }

    inner class ViewHolderIncome(private val binding: ChatIncomeMessageItemBinding) : ViewHolderBase(binding) {
        override fun bind(item: ChatMessage) {
            binding.message = item
        }
    }

    inner class ViewHolderOutcome(private val binding: ChatOutomeMessageItemBinding) : ViewHolderBase(binding) {
        override fun bind(item: ChatMessage) {
            binding.message = item
        }
    }

    abstract inner class ViewHolderBase(binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root){
        abstract fun bind(item: ChatMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderBase {
        return when(viewType){
            0 -> ViewHolderOutcome(DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.chat_outome_message_item,
                parent,
                false))
            else -> ViewHolderIncome(DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.chat_income_message_item,
                parent,
                false))
        }
    }
}

class MessageDiffUtil : DiffUtil.ItemCallback<ChatMessage>() {
    override fun areItemsTheSame(oldItem: ChatMessage, newItem: ChatMessage) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: ChatMessage, newItem: ChatMessage) = oldItem == newItem
}