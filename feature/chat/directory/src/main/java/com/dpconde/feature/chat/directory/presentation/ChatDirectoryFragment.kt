package com.dpconde.feature.chat.directory.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dpconde.feature.chat.directory.databinding.FragmentChatDirectoryBinding
import com.dpconde.feature.chat.directory.di.inject
import javax.inject.Inject


class ChatDirectoryFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val chatDirectoryViewModel: ChatDirectoryViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory)[ChatDirectoryViewModel::class.java]
    }

    private lateinit var binding: FragmentChatDirectoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentChatDirectoryBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        chatDirectoryViewModel.fetchMessageThreads()

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
    }
}