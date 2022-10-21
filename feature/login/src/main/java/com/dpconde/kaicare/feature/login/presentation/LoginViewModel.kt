package com.dpconde.kaicare.feature.login.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpconde.kaicare.core.sensor.biometric.BiometricAuthResult
import com.dpconde.kaicare.feature.login.domain.entities.AuthResult
import com.dpconde.kaicare.feature.login.presentation.usecase.AuthUseCases
import com.dpconde.kaicare.feature.login.presentation.usecase.BiometricUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val biometricUseCases: BiometricUseCases
) : ViewModel() {

    val accessGranted = MutableLiveData<Boolean>()
    val loginEmail = MutableLiveData<String>()
    val loginPass = MutableLiveData<String>()

    fun authWithBiometricSensor(){
        viewModelScope.launch(Dispatchers.Main) {
            when(biometricUseCases.authenticateWithBiometric()){
                is BiometricAuthResult.Error -> {}
                is BiometricAuthResult.Failure -> {}
                is BiometricAuthResult.Success -> accessGranted.postValue(true)
            }
        }
    }

    fun authWithEmailAndPassword(){
        viewModelScope.launch(Dispatchers.Main) {
            when(val result = authUseCases.doLogin(loginEmail.value!!, loginPass.value!!)){
                is AuthResult.Error -> onFailureAuthWithEmailAndPassword(result)
                is AuthResult.Success -> onSuccessAuthWithEmailAndPassword(result)
            }
        }
    }

    private fun onSuccessAuthWithEmailAndPassword(result: AuthResult.Success){
        authUseCases.saveTokenSession(result.token)
        authUseCases.saveEmailSession(loginEmail.value!!)
        accessGranted.postValue(true)
    }

    private fun onFailureAuthWithEmailAndPassword(result: AuthResult.Error){
        accessGranted.postValue(false)
    }

    fun isSessionAvailable() = authUseCases.isSessionAvailable()

    fun getUserEmail() = when(val email = authUseCases.getSessionEmail()){
        null -> loginEmail.postValue("")
        else -> loginEmail.postValue(email!!)
    }
}