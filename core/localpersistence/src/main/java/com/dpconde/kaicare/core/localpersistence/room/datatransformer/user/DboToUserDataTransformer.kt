package com.dpconde.kaicare.core.localpersistence.room.datatransformer.user

import com.dpconde.kaicare.core.commons.domain.User
import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.dpconde.kaicare.core.localpersistence.room.dbo.UserDbo

class DboToUserDataTransformer: DataTransformer<UserDbo, User> {

    override fun transform(from: UserDbo) =
        User(from.id, from.name, from.role, from.profileImg)
}