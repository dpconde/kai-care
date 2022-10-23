package com.dpconde.kaicare.feature.login.infrastructure.authenticator

import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.dpconde.kaicare.feature.login.domain.authenticator.EmailPasswordAuthenticator
import com.dpconde.kaicare.feature.login.domain.entities.AuthResult
import com.dpconde.kaicare.feature.login.domain.entities.AuthUser
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.google.firebase.auth.AuthResult as FirebaseAuthResult

class EmailPasswordAuthenticatorImpl @Inject constructor(
    private val userTransformer: DataTransformer<FirebaseUser, AuthUser>,
    private val firebaseAuth: FirebaseAuth
) : EmailPasswordAuthenticator {

    override suspend fun authenticate(email: String, password: String): AuthResult =
        suspendCoroutine { cont ->
            val callback = OnCompleteListener<FirebaseAuthResult> { task ->
                if (task.isSuccessful) {
                    cont.resume(AuthResult.Success(userTransformer.transform(firebaseAuth.currentUser!!)))
                } else {
                    cont.resume(AuthResult.Error(1, task.exception?.message ?: "Error login"))
                }
            }
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(callback)
        }

    override fun getLoggedUser() = firebaseAuth.currentUser?.let { userTransformer.transform(it) }
}