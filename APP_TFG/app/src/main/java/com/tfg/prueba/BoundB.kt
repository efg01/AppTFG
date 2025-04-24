package com.tfg.prueba

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class BoundB(
    @SerializedName("id") val id : String = "",
    @SerializedName("location") val location: String = "",
    @SerializedName("x_min") val x_min: String = "",
    @SerializedName("x_max") val x_max: String ="",
    @SerializedName("y_min") val y_min: String ="",
    @SerializedName("y_max") val y_max: String =""
)

data class TokenDto(
    @SerializedName("accessToken") val accessTokenVerify: String
)