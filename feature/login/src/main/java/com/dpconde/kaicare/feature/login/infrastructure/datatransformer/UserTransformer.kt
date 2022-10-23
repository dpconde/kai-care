package com.dpconde.kaicare.feature.login.infrastructure.datatransformer

import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.dpconde.kaicare.feature.login.domain.entities.AuthUser
import com.google.firebase.auth.FirebaseUser

class UserTransformer: DataTransformer<FirebaseUser, AuthUser> {

    override fun transform(from: FirebaseUser) =
        AuthUser(
            from.uid,
            from.displayName ?: "<Unknown>",
            from.email ?: "<Unknown>"
        )
}