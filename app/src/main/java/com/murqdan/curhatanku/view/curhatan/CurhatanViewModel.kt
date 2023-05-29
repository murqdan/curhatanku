package com.murqdan.curhatanku.view.curhatan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murqdan.curhatanku.api.ApiConfig
import com.murqdan.curhatanku.repository.CurhatankuRepository
import com.murqdan.curhatanku.model.UserModel
import com.murqdan.curhatanku.response.CurhatanResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurhatanViewModel(private val curhatankuRepository: CurhatankuRepository) : ViewModel() {
    private val _listCurhatan = MutableLiveData<CurhatanResponse>()
    val listCurhatan: LiveData<CurhatanResponse> = _listCurhatan

    fun getCurhatanData(token: String) {
        val client = ApiConfig.getApiService().getCurhatanData(token, location=1)
        client.enqueue(object : Callback<CurhatanResponse> {
            override fun onResponse(
                call: Call<CurhatanResponse>,
                response: Response<CurhatanResponse>
            ) {
                if (response.isSuccessful) {
                    _listCurhatan.value = response.body()
                } else {
                    Log.e("CurhatanViewModel", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<CurhatanResponse>, t: Throwable) {
                Log.e("CurhatanViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun getUser(): LiveData<UserModel> {
        return curhatankuRepository.getUser()
    }

    fun logout() {
        viewModelScope.launch {
            curhatankuRepository.userLogout()
        }
    }
}