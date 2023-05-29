package com.murqdan.curhatanku.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.murqdan.curhatanku.api.APIService
import com.murqdan.curhatanku.model.UserPreference
import com.murqdan.curhatanku.paging.CurhatanPagingSource
import com.murqdan.curhatanku.response.ListCurhatanItem

class CurhatanPagingRepository(
    private val apiService: APIService,
    private val preference: UserPreference
) {
    fun getCurhatanPaging(): LiveData<PagingData<ListCurhatanItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                CurhatanPagingSource(apiService, preference)
            }
        ).liveData
    }
}