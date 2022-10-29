package com.dpconde.feature.chat.directory.infrastructure.datatransformer.user

import com.dpconde.feature.chat.directory.domain.entities.User
import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.dpconde.kaicare.core.persistence.room.dbo.UserDbo

class RoomUserTransformer: DataTransformer<UserDbo, User> {

    override fun transform(from: UserDbo) =
        User(from.id, from.name, from.role, from.profileImg)
}