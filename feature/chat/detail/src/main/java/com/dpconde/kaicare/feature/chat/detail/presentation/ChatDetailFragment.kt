package com.dpconde.kaicare.feature.chat.detail.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.dpconde.kaicare.feature.chat.detail.databinding.FragmentChatDetailBinding
import com.dpconde.kaicare.feature.chat.detail.di.inject
import com.dpconde.kaicare.feature.chat.detail.presentation.adapter.MessagesAdapter
import javax.inject.Inject
import kotlin.math.absoluteValue

class ChatDetailFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val chatDetailViewModel: ChatDetailViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ChatDetailViewModel::class.java]
    }

    private val args: ChatDetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentChatDetailBinding

    private val listAdapter: MessagesAdapter by lazy {
        MessagesAdapter(chatDetailViewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentChatDetailBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = chatDetailViewModel
            chatName = args.threadName
            chatImg = args.threadImg
        }

        setUpRecyclerView()

        chatDetailViewModel.onChatOpened(args.threadId)
        chatDetailViewModel.fetchMessages(args.threadId)
        chatDetailViewModel.startFetchingRealTimeMessages(args.threadId)

        chatDetailViewModel.messages.observe(viewLifecycleOwner){ messages ->
            messages?.let {
                listAdapter.submitList(it){
                    binding.rvMessages.scrollToPosition(it.size-1)
                }
            }
        }

        binding.sendButton.setOnClickListener {
            chatDetailViewModel.sendMessage(args.threadId)
        }


        binding.rvMessages.addOnLayoutChangeListener { _, _, _, _, bottom, _, _, _, oldBottom ->
            if(bottom != oldBottom) {
                val y = (oldBottom - bottom).absoluteValue
                binding.rvMessages.scrollBy(0, y)
            }
        }

        NavigationUI.setupWithNavController(binding.toolbar, findNavController())

        return binding.root
    }

    private fun setUpRecyclerView(){
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvMessages.layoutManager = layoutManager
        binding.rvMessages.adapter = listAdapter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
    }
}