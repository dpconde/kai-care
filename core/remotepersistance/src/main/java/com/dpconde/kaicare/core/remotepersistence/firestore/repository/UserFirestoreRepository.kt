package com.dpconde.kaicare.core.remotepersistence.firestore.repository

import com.dpconde.kaicare.core.commons.domain.User
import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.dpconde.kaicare.core.remotepersistence.repository.UserRemoteRepository
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserFirestoreRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val userTransformer: DataTransformer<DocumentSnapshot, User>
): UserRemoteRepository  {

    companion object {
        const val USERS_KEY = "USERS"
    }

    override suspend fun getAllUsers(): List<User> =
        suspendCoroutine { cont ->
            val callback = OnSuccessListener<QuerySnapshot> { task ->
                val messageThreads = task.documents.map { userTransformer.transform(it) }
                cont.resume(messageThreads)
            }

            firestore.collection(USERS_KEY)
                .get()
                .addOnSuccessListener(callback)
        }
}