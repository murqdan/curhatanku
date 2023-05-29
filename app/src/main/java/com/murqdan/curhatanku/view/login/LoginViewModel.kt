package com.murqdan.curhatanku.view.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murqdan.curhatanku.api.ApiConfig
import com.murqdan.curhatanku.repository.CurhatankuRepository
import com.murqdan.curhatanku.model.UserModel
import com.murqdan.curhatanku.response.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val curhatankuRepository: CurhatankuRepository) : ViewModel() {
    private val _login = MutableLiveData<LoginResponse>()
    val login: LiveData<LoginResponse> = _login

    fun postLoginData(email:String, password:String) {
        val client = ApiConfig.getApiService().postLoginData(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    _login.value = response.body()
                } else {
                    Log.e("LoginViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("LoginViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun postUser(session: UserModel) {
        viewModelScope.launch {
            curhatankuRepository.postUser(session)
        }
    }

    fun userLogin() {
        viewModelScope.launch {
            curhatankuRepository.userLogin()
        }
    }
}