package com.murqdan.curhatanku.response

import com.google.gson.annotations.SerializedName

data class AddCurhatanResponse(
    @field:SerializedName("error")
    val error: Boolean
)