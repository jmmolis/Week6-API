package edu.du.week6apis

import edu.du.week6apis.model.DogModel
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*


interface DogService {

    /* POST METHOD */
    @POST("dogs")
    suspend fun createDog(@Body requestBody: RequestBody): Response<DogModel>

    /* GET METHODS */
    @GET("dogs")
    suspend fun getDogs(): Response<List<DogModel>>

    @GET("dogs/{id}")
    suspend fun getDog(@Path("id") id: String): Response<DogModel>

    /* PUT METHOD */
    @PUT("dogs/{id}")
    suspend fun updateDog(@Path("id") id: String, @Body requestBody: RequestBody): Response<DogModel>

    /* DELETE METHOD - when dog is adopted */
    @DELETE("dogs/{id}")
    suspend fun deleteDog(@Path("id") id: String): Response<DogModel>

}
