package com.murqdan.curhatanku.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @field:SerializedName("error")
    var error: Boolean
)