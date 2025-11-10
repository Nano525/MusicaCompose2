package mx.edu.utez.musicacompose.data.Api

import mx.edu.utez.musicacompose.data.model.AlbumConCancionesApi
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {

    @GET("albums")
    suspend fun getAlbums(): Response<List<AlbumConCancionesApi>>

    @Multipart
    @POST("albums")
    suspend fun postAlbum(
        @Part("nombre") nombre: RequestBody,
        @Part("artista") artista: RequestBody,
        @Part imagen: MultipartBody.Part?,
        @Part("canciones") canciones: RequestBody? // JSON con lista de canciones
    ): Response<Map<String, Any>>
}

object ApiClient {
    private const val BASE_URL = "http://192.168.0.123:5000/"

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
