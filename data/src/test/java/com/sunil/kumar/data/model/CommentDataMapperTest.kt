package com.sunil.kumar.data.model

import com.sunil.kumar.data.TestDomainData
import com.sunil.kumar.data.cache.model.comment.CommentDataMapper
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class CommentDataMapperTest {

    private val commentDataMapper = CommentDataMapper()

    @Test
    fun shouldMapCommentDataToCachedComment() {

        val commentData = TestDomainData.getComment()
        val expectedCachedComment = TestDomainData.getCachedComment()

        val cachedComment = commentDataMapper.mapToCached(commentData)

        assertEquals(cachedComment.id, expectedCachedComment.id)
        assertEquals(cachedComment.postId, expectedCachedComment.postId)
        assertEquals(cachedComment.name, expectedCachedComment.name)
        assertEquals(cachedComment.email, expectedCachedComment.email)
        assertEquals(cachedComment.body, expectedCachedComment.body)
    }

    @Test
    fun shouldMapCachedCommentToCommentData() {

        val cachedComment = TestDomainData.getCachedComment()
        val expectedCommentData = TestDomainData.getComment()

        val commentData = commentDataMapper.mapFromCached(cachedComment)

        assertEquals(commentData.id, expectedCommentData.id)
        assertEquals(commentData.postId, expectedCommentData.postId)
        assertEquals(commentData.name, expectedCommentData.name)
        assertEquals(commentData.email, expectedCommentData.email)
        assertEquals(commentData.body, expectedCommentData.body)
    }
}