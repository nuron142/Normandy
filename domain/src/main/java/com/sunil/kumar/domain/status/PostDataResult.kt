package com.sunil.kumar.domain.status

import com.sunil.kumar.domain.models.PostData

sealed class PostDataResult {

    object Error : PostDataResult()
    object Empty : PostDataResult()
    data class Success(val postDataList: List<PostData>) : PostDataResult()
}