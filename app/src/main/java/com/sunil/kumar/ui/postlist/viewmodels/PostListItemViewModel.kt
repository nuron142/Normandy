package com.sunil.kumar.ui.postlist.viewmodels

import androidx.databinding.ObservableField
import com.sunil.kumar.domain.models.PostData
import com.sunil.kumar.ui.adapter.RecyclerViewModel
import com.sunil.kumar.util.Utilities

class PostListItemViewModel
constructor(
    private val postData: PostData,
    private var onClickAction: ((PostData) -> Unit)? = null
) : RecyclerViewModel {

    val postMessage = ObservableField<String>("")
    val postTitle = ObservableField<String>("")
    val imageUrl = ObservableField<String>("")

    val name = ObservableField<String>("")

    init {
        setUpViewModel()
    }

    private fun setUpViewModel() {

        postData.apply {

            postTitle.set(title)
            postMessage.set(body)
            imageUrl.set(Utilities.getRandomProfilePicUrl())
        }
    }

    fun onClick(): () -> Unit = {

        onClickAction?.invoke(postData)
    }
}