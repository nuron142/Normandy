package com.sunil.kumar.domain.status

import com.sunil.kumar.domain.models.CommentData

sealed class CommentDataResult {

    object Error : CommentDataResult()
    object Empty : CommentDataResult()
    data class Success(val commentDataList: List<CommentData>) : CommentDataResult()
}