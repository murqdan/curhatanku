package com.murqdan.curhatanku.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.murqdan.curhatanku.api.ApiConfig
import com.murqdan.curhatanku.repository.CurhatankuRepository
import com.murqdan.curhatanku.model.UserPreference
import com.murqdan.curhatanku.paging.CurhatanDatabase
import com.murqdan.curhatanku.repository.CurhatanPagingRepository

val Context.database: DataStore<Preferences> by preferencesDataStore("token")

object Injection {
    fun provideRepository(context: Context): CurhatankuRepository {
        val preference = UserPreference.getInstance(context.database)
        return CurhatankuRepository.getInstance(preference)
    }

    fun provideRepositoryPaging(context: Context): CurhatanPagingRepository {
        CurhatanDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        val preference = UserPreference.getInstance(context.database)
        return CurhatanPagingRepository(apiService, preference)
    }
}
