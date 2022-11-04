package com.dpconde.feature.chat.directory.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dpconde.feature.chat.directory.R
import com.dpconde.feature.chat.directory.databinding.ChatDirectoryItemBinding
import com.dpconde.feature.chat.directory.presentation.ChatDirectoryViewModel
import com.dpconde.kaicare.core.commons.domain.ChatThread

class ChatDirectoryAdapter (private val viewModel: ChatDirectoryViewModel):
    ListAdapter<ChatThread, ChatDirectoryAdapter.ViewHolder>(MessageThreadDiffUtil()) {

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
                binding.root.findNavController()
                    .navigate(com.dpconde.kaicare.R.id.chatDirectoryFragment_to_chatDetailFragment,
                    bundleOf(
                        "threadId" to item!!.id,
                        "threadName" to item.name,
                        "threadImg" to item.imageUrl))
            }
        }

        fun bind(item: ChatThread) {
            binding.thread = item
        }
    }
}

class MessageThreadDiffUtil : DiffUtil.ItemCallback<ChatThread>() {
    override fun areItemsTheSame(oldItem: ChatThread, newItem: ChatThread) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: ChatThread, newItem: ChatThread) = oldItem == newItem
}