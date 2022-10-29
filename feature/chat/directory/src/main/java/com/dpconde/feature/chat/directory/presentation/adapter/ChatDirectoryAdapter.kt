package com.dpconde.feature.chat.directory.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dpconde.feature.chat.directory.R
import com.dpconde.feature.chat.directory.databinding.ChatDirectoryItemBinding
import com.dpconde.feature.chat.directory.domain.entities.MessageThread
import com.dpconde.feature.chat.directory.presentation.ChatDirectoryViewModel

class ChatDirectoryAdapter (private val viewModel: ChatDirectoryViewModel):
    ListAdapter<MessageThread, ChatDirectoryAdapter.ViewHolder>(MessageThreadDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.chat_directory_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val messageThread = getItem(position)
        holder.bind(messageThread)
    }

    inner class ViewHolder(private val binding: ChatDirectoryItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                val item = binding.thread
                //TODO open thread
            }
        }

        fun bind(item: MessageThread) {
            binding.thread = item
        }
    }
}

class MessageThreadDiffUtil : DiffUtil.ItemCallback<MessageThread>() {
    override fun areItemsTheSame(oldItem: MessageThread, newItem: MessageThread) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: MessageThread, newItem: MessageThread) = oldItem == newItem
}