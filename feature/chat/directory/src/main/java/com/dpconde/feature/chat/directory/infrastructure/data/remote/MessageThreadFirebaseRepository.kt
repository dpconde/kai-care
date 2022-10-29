package com.dpconde.feature.chat.directory.infrastructure.data.remote

import com.dpconde.feature.chat.directory.domain.data.remote.MessageThreadRemoteRepository
import com.dpconde.feature.chat.directory.domain.entities.Message
import com.dpconde.feature.chat.directory.domain.entities.MessageThread
import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MessageThreadFirebaseRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val messageThreadTransformer: DataTransformer<DocumentSnapshot, MessageThread>,
    private val messageTransformer: DataTransformer<DocumentSnapshot, Message>,
): MessageThreadRemoteRepository {

    companion object{
        const val MESSAGE_THREAD_KEY = "MESSAGE_THREADS"
        const val MESSAGE_THREAD_MESSAGES_KEY = "MESSAGES"
    }

    override suspend fun getMessageThreadsByUser(userId: String): List<MessageThread> =
        suspendCoroutine { cont ->
            val callback = OnSuccessListener<QuerySnapshot> { task ->
                val messageThreads = task.documents.map { messageThreadTransformer.transform(it) }
                cont.resume(messageThreads)
            }

            firestore.collection(MESSAGE_THREAD_KEY)
                .whereArrayContains("members", userId)
                .get()
                .addOnSuccessListener(callback)
        }

    override suspend fun getMessagesByThreadIdAndDate(messageThreadId: String, fromDate: Date): List<Message> =
        suspendCoroutine { cont ->
            val callback = OnSuccessListener<QuerySnapshot> { task ->
                val messages = task.documents.map { messageTransformer.transform(it) }
                cont.resume(messages)
            }

            val collection = "$MESSAGE_THREAD_KEY/$messageThreadId/$MESSAGE_THREAD_MESSAGES_KEY"
            firestore.collection(collection)
                .get()
                .addOnSuccessListener(callback)
        }
}