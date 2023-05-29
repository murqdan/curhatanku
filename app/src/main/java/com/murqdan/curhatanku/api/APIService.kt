package com.murqdan.curhatanku.api

import com.murqdan.curhatanku.response.AddCurhatanResponse
import com.murqdan.curhatanku.response.CurhatanResponse
import com.murqdan.curhatanku.response.LoginResponse
import com.murqdan.curhatanku.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface APIService {
    @FormUrlEncoded
    @POST("login")
    fun postLoginData(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("register")
    fun postRegisterData(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @GET("stories")
    fun getCurhatanData(
        @Header("Authorization") token: String,
        @Query("location") location: Int? = null
    ): Call<CurhatanResponse>

    @GET("stories")
    suspend fun getCurhatanPaging(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): CurhatanResponse

    @Multipart
    @POST("stories")
    fun postCurhatanData(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ):Call<AddCurhatanResponse>
}