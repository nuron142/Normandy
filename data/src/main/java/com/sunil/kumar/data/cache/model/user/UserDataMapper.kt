package com.sunil.kumar.data.cache.model.user

import com.sunil.kumar.data.cache.EntityMapper
import com.sunil.kumar.domain.models.UserData
import javax.inject.Inject

class UserDataMapper
@Inject
constructor() : EntityMapper<UserData, CachedUser> {

    override fun mapToCached(userData: UserData): CachedUser {
        return CachedUser(
            userData.id,
            userData.name,
            userData.username,
            userData.email
        )
    }

    override fun mapFromCached(cachedUser: CachedUser): UserData {
        return UserData(cachedUser.id, cachedUser.name, cachedUser.username, cachedUser.email)
    }

}