package com.sunil.kumar.ui.postdetail.viewmodels

import androidx.databinding.ObservableField
import com.sunil.kumar.domain.models.CommentData
import com.sunil.kumar.ui.adapter.RecyclerViewModel
import com.sunil.kumar.util.Utilities

class CommentItemViewModel
constructor(private val postData: CommentData) : RecyclerViewModel {

    val comment = ObservableField<String>("")
    val userName = ObservableField<String>("")
    val emailAddress = ObservableField<String>("")
    val imageUrl = ObservableField<String>("")

    val name = ObservableField<String>("")

    init {
        setUpViewModel()
    }

    private fun setUpViewModel() {

        postData.apply {

            userName.set(name)
            comment.set(body)
            emailAddress.set(email?.toLowerCase())
            imageUrl.set(Utilities.getRandomProfilePicUrl())
        }
    }
}