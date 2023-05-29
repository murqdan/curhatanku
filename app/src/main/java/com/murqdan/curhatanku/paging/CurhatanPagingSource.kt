package com.murqdan.curhatanku.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.murqdan.curhatanku.api.APIService
import com.murqdan.curhatanku.model.UserPreference
import com.murqdan.curhatanku.response.ListCurhatanItem
import kotlinx.coroutines.flow.first

class CurhatanPagingSource(private val apiService: APIService, private val preference: UserPreference) : PagingSource<Int, ListCurhatanItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListCurhatanItem> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val tokenUserPaging = preference.getUser().first().token
            val responseData = apiService.getCurhatanPaging(tokenUserPaging, page, params.loadSize)

            LoadResult.Page(
                data = responseData.listCurhatan,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (responseData.listCurhatan.isNullOrEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ListCurhatanItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}