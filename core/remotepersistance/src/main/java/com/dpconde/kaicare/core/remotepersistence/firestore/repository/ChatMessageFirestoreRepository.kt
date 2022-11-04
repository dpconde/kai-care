package com.dpconde.kaicare.core.remotepersistence.firestore.repository

import com.dpconde.kaicare.core.commons.domain.ChatMessage
import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.dpconde.kaicare.core.remotepersistence.repository.ChatMessageRemoteRepository
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.snapshots
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ChatMessageFirestoreRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val messageTransformer: DataTransformer<DocumentSnapshot, ChatMessage>,
    private val mapChatMessageTransformer: DataTransformer<ChatMessage, Map<String, Any>>
): ChatMessageRemoteRepository {

    companion object{
        const val MESSAGE_THREAD_KEY = "MESSAGE_THREADS"
        const val MESSAGE_THREAD_MESSAGES_KEY = "MESSAGES"
    }

    override suspend fun getByThreadIdAndDate(
        messageThreadId: String,
        fromDate: Date,
    ): List<ChatMessage> =
        suspendCoroutine { cont ->
            val callback = OnSuccessListener<QuerySnapshot> { task ->
                val messages = task.documents.map { messageTransformer.transform(it) }
                cont.resume(messages)
            }

            val collection = "$MESSAGE_THREAD_KEY/$messageThreadId/$MESSAGE_THREAD_MESSAGES_KEY"
            firestore.collection(collection)
                .orderBy("sent", Query.Direction.DESCENDING)
                .whereGreaterThan("sent", fromDate)
                .get()
                .addOnSuccessListener(callback)
        }


    override suspend fun getRealTimeByThreadIdAndDate(
        messageThreadId: String,
        fromDate: Date,
    ): Flow<List<ChatMessage>> {
        val collection = "$MESSAGE_THREAD_KEY/$messageThreadId/$MESSAGE_THREAD_MESSAGES_KEY"
        return  withContext(Dispatchers.IO){
            firestore.collection(collection)
                .whereGreaterThan("sent", fromDate)
                .orderBy("sent", Query.Direction.DESCENDING)
                .limit(1)
                .snapshots()
                .map { querySnapshot -> querySnapshot.documents
                    .map { messageTransformer.transform(it) } }
        }
    }

    override suspend fun saveMessage(chatMessage: ChatMessage) {
        val collection = "$MESSAGE_THREAD_KEY/${chatMessage.threadId}/$MESSAGE_THREAD_MESSAGES_KEY"
        withContext(Dispatchers.IO) {
            firestore.collection(collection)
                .document(chatMessage.id)
                .set(mapChatMessageTransformer.transform(chatMessage)) //TODO add listener
        }
    }
}