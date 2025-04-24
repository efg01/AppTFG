package com.tfg.prueba

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerializedName("timestamp") val timestamp: String = "",
    @SerializedName("x") val x: String ="",
    @SerializedName("y") val y: String ="",
    @SerializedName("label") val label: String = ""
)
