package com.sunil.kumar.data.cache.model.comment

import com.sunil.kumar.data.cache.EntityMapper
import com.sunil.kumar.domain.models.CommentData
import javax.inject.Inject

class CommentDataMapper
@Inject
constructor() : EntityMapper<CommentData, CachedComment> {

    override fun mapToCached(commentData: CommentData): CachedComment {
        return CachedComment(
            commentData.id,
            commentData.postId,
            commentData.name,
            commentData.email,
            commentData.body
        )
    }

    override fun mapFromCached(cachedComment: CachedComment): CommentData {
        return CommentData(cachedComment.id, cachedComment.postId, cachedComment.name, cachedComment.email, cachedComment.body)
    }

}