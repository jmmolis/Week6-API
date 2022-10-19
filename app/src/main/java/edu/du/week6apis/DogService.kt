package edu.du.week6apis

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*


interface DogService {

    /* POST METHOD */
    @POST("dogs")
    suspend fun createDog(@Body requestBody: RequestBody): Response<ResponseBody>

    /* GET METHODS */
    @GET("dogs")
    suspend fun getDogs(): Response<ResponseBody>

    @GET("dogs/{id}")
    suspend fun getDog(@Path("id") id: String): Response<ResponseBody>

    /* PUT METHOD */
    @PUT("dogs/{id}")
    suspend fun updateDog(@Path("id") id: String, @Body requestBody: RequestBody): Response<ResponseBody>

    /* DELETE METHOD - when dog is adopted */
    @DELETE("dogs/{id}")
    suspend fun deleteDog(@Path("id") id: String): Response<ResponseBody>

}
