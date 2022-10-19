package edu.du.week6apis

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*


interface APIService {

    /* POST METHOD */

    // Raw JSON
    @POST("/api/v1/create")
    suspend fun createDog(@Body requestBody: RequestBody): Response<ResponseBody>


    // Form Data
    @Multipart
    @POST("/post")
    suspend fun uploadDogData(@PartMap map: HashMap<String?, RequestBody?>): Response<ResponseBody>


    // Encoded URL
    @FormUrlEncoded
    @POST("/post")
    suspend fun createDog(@FieldMap params: HashMap<String?, String?>): Response<ResponseBody>




    /* GET METHOD */

    @GET("/api/v1/dogs")
    suspend fun getDogs(): Response<ResponseBody>


    // Request using @Query (e.g https://reqres.in/api/dogs?page=2)
    @GET("/api/dogs")
    suspend fun getDogs(@Query("page") page: String?): Response<ResponseBody>


    // Request using @Path (e.g https://reqres.in/api/dogs/53 - This URL is just an example, it's not working)
    @GET("/api/dogs/{Id}")
    suspend fun getDog(@Path("Id") employeeId: String): Response<ResponseBody>

    /* PUT METHOD */

    @PUT("/api/dogs/2")
    suspend fun updateDog(@Body requestBody: RequestBody): Response<ResponseBody>

    /* DELETE METHOD - when dog is adopted */

    @DELETE("/typicode/demo/posts/1")
    suspend fun deleteDog(): Response<ResponseBody>



}
