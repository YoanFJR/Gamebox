package fr.epita.gamebox

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IWebService {
    @GET("game/scores")
    fun scoresList(): Call<List<Score>>

    @POST("game/score")
    fun postScore(@Body parameters : HashMap<String, String>): Call<Boolean>
}