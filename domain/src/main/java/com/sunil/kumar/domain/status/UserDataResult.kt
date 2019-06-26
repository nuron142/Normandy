package com.sunil.kumar.domain.status

import com.sunil.kumar.domain.models.UserData

sealed class UserDataResult {

    object Error : UserDataResult()
    data class Success(val userData: UserData) : UserDataResult()
}