package com.dpconde.kaicare.core.remotepersistence.firestore.datatransformer.user

import com.dpconde.kaicare.core.commons.domain.User
import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.google.firebase.firestore.DocumentSnapshot

class DocToUserTransformer : DataTransformer<DocumentSnapshot, User> {

    companion object{
        const val NAME_KEY = "name"
        const val PROFILE_IMG_KEY = "profileImg"
        const val ROLE_KEY = "role"
        const val DEFAULT_PROFILE = "patient"
        const val DEFAULT_URL_IMG =
            "https://t4.ftcdn.net/jpg/02/15/84/43/240_F_215844325_ttX9YiIIyeaR7Ne6EaLLjMAmy4GvPC69.jpg"
    }

    override fun transform(from: DocumentSnapshot) =
        User(
            from.id,
            from.getString(NAME_KEY) ?: "<No name>",
            from.getString(ROLE_KEY) ?: DEFAULT_PROFILE,
            from.getString(PROFILE_IMG_KEY) ?: DEFAULT_URL_IMG
        )
}