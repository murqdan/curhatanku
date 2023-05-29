package com.murqdan.curhatanku.paging

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.murqdan.curhatanku.DataDummy
import com.murqdan.curhatanku.MainDispatcherRule
import com.murqdan.curhatanku.adapter.CurhatanListAdapter
import com.murqdan.curhatanku.getOrAwaitValue
import com.murqdan.curhatanku.repository.CurhatanPagingRepository
import com.murqdan.curhatanku.response.ListCurhatanItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CurhatanPagingViewModelTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()
    @Mock
    private lateinit var curhatanPagingRepository: CurhatanPagingRepository

    @Test
    fun `when Get Curhatan Should Not Null and Return Data`() = runTest {
        val dummyCurhatan = DataDummy.generateDummyCurhatanResponse()
        val data: PagingData<ListCurhatanItem> = CurhatanPagingSource.snapshot(dummyCurhatan)
        val expectedCurhatan = MutableLiveData<PagingData<ListCurhatanItem>>()
        expectedCurhatan.value = data
        Mockito.`when`(curhatanPagingRepository.getCurhatanPaging()).thenReturn(expectedCurhatan)

        val curhatanPagingViewModel = CurhatanPagingViewModel(curhatanPagingRepository)
        val actualCurhatan: PagingData<ListCurhatanItem> = curhatanPagingViewModel.curhatanPaging.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = CurhatanListAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualCurhatan)

        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyCurhatan.size, differ.snapshot().size)
        Assert.assertEquals(dummyCurhatan[0], differ.snapshot()[0])
    }

    @Test
    fun `when Get Curhatan Empty Should Return No Data`() = runTest {
        val data: PagingData<ListCurhatanItem> = PagingData.from(emptyList())
        val expectedCurhatan = MutableLiveData<PagingData<ListCurhatanItem>>()
        expectedCurhatan.value = data
        Mockito.`when`(curhatanPagingRepository.getCurhatanPaging()).thenReturn(expectedCurhatan)

        val curhatanPagingViewModel = CurhatanPagingViewModel(curhatanPagingRepository)
        val actualCurhatan: PagingData<ListCurhatanItem> = curhatanPagingViewModel.curhatanPaging.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = CurhatanListAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualCurhatan)

        Assert.assertEquals(0, differ.snapshot().size)
    }
}

class CurhatanPagingSource : PagingSource<Int, LiveData<List<ListCurhatanItem>>>() {
    companion object {
        fun snapshot(items: List<ListCurhatanItem>): PagingData<ListCurhatanItem> {
            return PagingData.from(items)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, LiveData<List<ListCurhatanItem>>>): Int {
        return 0
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<ListCurhatanItem>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}