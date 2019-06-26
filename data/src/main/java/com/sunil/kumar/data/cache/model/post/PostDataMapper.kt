package com.sunil.kumar.data.cache.model.post

import com.sunil.kumar.data.cache.EntityMapper
import com.sunil.kumar.domain.models.PostData
import javax.inject.Inject

class PostDataMapper
@Inject
constructor() : EntityMapper<PostData, CachedPost> {

    override fun mapToCached(postData: PostData): CachedPost {
        return CachedPost(
            postData.id,
            postData.userId,
            postData.title,
            postData.body
        )
    }

    override fun mapFromCached(cachedPost: CachedPost): PostData {
        return PostData(cachedPost.id, cachedPost.userId, cachedPost.title, cachedPost.body)
    }

}