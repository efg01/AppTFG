package com.tfg.prueba
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/data")
    suspend fun getData(): Data

    @GET("/getBoxes")
    suspend fun getBB(): List<BoundB>

    @POST("/uploadBoxes")
    suspend fun uploadBB(@Body bbData: BoundB): Response<TokenDto>

    @POST("/modifyBoxes")
    suspend fun modifyBB(@Body() bbData: JSONObject): Response<TokenDto>

    @DELETE("/deleteBoxes")
    suspend fun deleteBB(@Query("loc") bbData: String)
}

object ApiClient {
    private const val BASE_URL = " "


    val apiService: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}

suspend fun uploadBB( bbData: BoundB): Response<TokenDto>? {
    try {
        return withContext(Dispatchers.IO) { ApiClient.apiService.uploadBB(bbData) }
    } catch (e: Exception) {
        Log.e("Request", e.toString())
    }
    return null
}

suspend fun modifyBB( bbData: JSONObject): Response<TokenDto>? {
    try {
        return withContext(Dispatchers.IO) { ApiClient.apiService.modifyBB(bbData) }
    } catch (e: Exception) {
        Log.e("Request", e.toString())
    }
    return null
}

suspend fun deleteBB(location: String){
    try{
        withContext(Dispatchers.IO){ApiClient.apiService.deleteBB(location)}
    }catch (e: Exception){
        Log.e("Request", e.toString())
    }
}
