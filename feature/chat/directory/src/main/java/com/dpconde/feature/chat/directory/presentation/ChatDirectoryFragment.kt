package com.dpconde.feature.chat.directory.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dpconde.feature.chat.directory.databinding.FragmentChatDirectoryBinding


class ChatDirectoryFragment : Fragment() {

    private lateinit var binding: FragmentChatDirectoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentChatDirectoryBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }
}