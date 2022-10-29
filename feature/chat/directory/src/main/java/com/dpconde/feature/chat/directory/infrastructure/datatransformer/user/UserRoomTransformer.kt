package com.dpconde.feature.chat.directory.infrastructure.datatransformer.user

import com.dpconde.feature.chat.directory.domain.entities.User
import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.dpconde.kaicare.core.persistence.room.dbo.UserDbo

class UserRoomTransformer: DataTransformer<User, UserDbo> {

    override fun transform(from: User) =
        UserDbo(from.id, from.name, from.role, from.imageUrl)
}