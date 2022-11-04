package com.dpconde.kaicare.core.remotepersistence.firestore.repository

import com.dpconde.kaicare.core.commons.domain.ChatThread
import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.dpconde.kaicare.core.remotepersistence.repository.ChatThreadRemoteRepository
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ChatThreadFirestoreRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val docToChatThreadTransformer: DataTransformer<DocumentSnapshot, ChatThread>
): ChatThreadRemoteRepository {

    companion object{
        const val MESSAGE_THREAD_KEY = "MESSAGE_THREADS"
    }

    override suspend fun getByUser(userId: String): List<ChatThread> =
        suspendCoroutine { cont ->
            val callback = OnSuccessListener<QuerySnapshot> { task ->
                val messageThreads = task.documents.map { docToChatThreadTransformer.transform(it) }
                cont.resume(messageThreads)
            }

            firestore.collection(MESSAGE_THREAD_KEY)
                .whereArrayContains("members", userId)
                .get()
                .addOnSuccessListener(callback)
        }
}