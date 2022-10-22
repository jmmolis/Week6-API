package edu.du.week6apis

import edu.du.week6apis.model.MovieModel
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*


interface MovieService {

    /* POST METHOD */
    @POST("movies")
    suspend fun createMovie(@Body requestBody: RequestBody): Response<MovieModel>

    /* GET METHODS */
    @GET("movies")
    suspend fun getMovies(): Response<List<MovieModel>>

    @GET("movies/{id}")
    suspend fun getMovie(@Path("id") id: String): Response<MovieModel>

    /* PUT METHOD */
    @PUT("movies/{id}")
    suspend fun updateMovie(@Path("id") id: String, @Body requestBody: RequestBody): Response<MovieModel>

    /* DELETE METHOD*/
    @DELETE("movies/{id}")
    suspend fun deleteMovie(@Path("id") id: String): Response<MovieModel>

}
