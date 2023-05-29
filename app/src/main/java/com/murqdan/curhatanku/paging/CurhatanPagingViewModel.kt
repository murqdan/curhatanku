package com.murqdan.curhatanku.paging

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.murqdan.curhatanku.di.Injection
import com.murqdan.curhatanku.repository.CurhatanPagingRepository
import com.murqdan.curhatanku.response.ListCurhatanItem

class CurhatanPagingViewModel(curhatanPagingRepository: CurhatanPagingRepository) : ViewModel() {
    val curhatanPaging: LiveData<PagingData<ListCurhatanItem>> =
        curhatanPagingRepository.getCurhatanPaging().cachedIn(viewModelScope)
}

class ViewModelFactoryPaging(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurhatanPagingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CurhatanPagingViewModel(Injection.provideRepositoryPaging(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}