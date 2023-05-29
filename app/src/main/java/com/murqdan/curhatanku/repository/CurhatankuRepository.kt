package com.murqdan.curhatanku.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.murqdan.curhatanku.model.UserModel
import com.murqdan.curhatanku.model.UserPreference

class CurhatankuRepository private constructor(
    private val pref: UserPreference,
) {
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    suspend fun postUser(session: UserModel) {
        pref.postUser(session)
    }

    suspend fun userLogin() {
        pref.login()
    }

    suspend fun userLogout() {
        pref.logout()
    }

    companion object {
        @Volatile
        private var instance: CurhatankuRepository? = null
        fun getInstance(preference: UserPreference): CurhatankuRepository =
            instance ?: synchronized(this) {
                instance ?: CurhatankuRepository(preference)
            }.also { instance = it }
    }
}