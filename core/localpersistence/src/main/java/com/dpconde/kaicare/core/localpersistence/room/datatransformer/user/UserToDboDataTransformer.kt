package com.dpconde.kaicare.core.localpersistence.room.datatransformer.user

import com.dpconde.kaicare.core.commons.domain.User
import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.dpconde.kaicare.core.localpersistence.room.dbo.UserDbo

class UserToDboDataTransformer: DataTransformer<User, UserDbo> {

    override fun transform(from: User) =
        UserDbo(from.id, from.name, from.role, from.imageUrl)
}