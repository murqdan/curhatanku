package com.murqdan.curhatanku.view.addcurhatan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.murqdan.curhatanku.api.ApiConfig
import com.murqdan.curhatanku.model.UserModel
import com.murqdan.curhatanku.repository.CurhatankuRepository
import com.murqdan.curhatanku.response.AddCurhatanResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCurhatanViewModel(private val curhatankuRepository: CurhatankuRepository) : ViewModel() {
    private val _addCurhatan = MutableLiveData<AddCurhatanResponse>()
    val addCurhatan: LiveData<AddCurhatanResponse> = _addCurhatan

    fun postCurhatanData(token: String, file: MultipartBody.Part, description: RequestBody) {
        val client = ApiConfig.getApiService().postCurhatanData(token, file, description)
        client.enqueue(object : Callback<AddCurhatanResponse> {
            override fun onResponse(
                call: Call<AddCurhatanResponse>,
                response: Response<AddCurhatanResponse>
            ) {
                if (response.isSuccessful) {
                    _addCurhatan.value = response.body()
                } else {
                    Log.e("AddCurhatanViewModel", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<AddCurhatanResponse>, t: Throwable) {
                Log.e("AddCurhatanViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun getUser(): LiveData<UserModel> {
        return curhatankuRepository.getUser()
    }
}