package fr.epita.gamebox

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IWebService {
    @GET("game/scores")
    fun scoresList(): Call<List<Score>>

    /*
    @GET("game/score")
    fun getDetail(@Query("game_id") userId: Int): Call<GameDetails>
    */
}