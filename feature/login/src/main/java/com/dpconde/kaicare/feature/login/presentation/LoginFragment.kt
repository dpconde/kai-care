package com.dpconde.kaicare.feature.login.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dpconde.kaicare.feature.login.databinding.FragmentLoginBinding
import com.dpconde.kaicare.feature.login.di.inject
import com.dpconde.kaicare.presentation.AuthActivity
import javax.inject.Inject

class LoginFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory)[LoginViewModel::class.java]
    }

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false).apply {
            viewModel = loginViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        if(loginViewModel.isSessionAvailable()) loginViewModel.authWithBiometricSensor()

        loginViewModel.getUserEmail()

        loginViewModel.accessGranted.observe(viewLifecycleOwner){
            it?.let {
                when(it){
                    true -> (requireActivity() as AuthActivity).goToMainActivity()
                    false -> Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        loginViewModel.accessGranted.value = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
    }
}