package com.example.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

@JsonClass(generateAdapter = true)
data class InlineDataPayload(
    @Json(name = "mimeType") val mimeType: String,
    @Json(name = "data") val data: String
)

@JsonClass(generateAdapter = true)
data class PartPayload(
    @Json(name = "text") val text: String? = null,
    @Json(name = "inlineData") val inlineData: InlineDataPayload? = null
)

@JsonClass(generateAdapter = true)
data class ContentPayload(
    @Json(name = "parts") val parts: List<PartPayload>
)

@JsonClass(generateAdapter = true)
data class ResponseFormatTextPayload(
    @Json(name = "mimeType") val mimeType: String = "application/json"
)

@JsonClass(generateAdapter = true)
data class ResponseFormatPayload(
    @Json(name = "text") val text: ResponseFormatTextPayload = ResponseFormatTextPayload()
)

@JsonClass(generateAdapter = true)
data class GenerationConfigPayload(
    @Json(name = "temperature") val temperature: Float = 0.2f
)

@JsonClass(generateAdapter = true)
data class GenerateContentReq(
    @Json(name = "contents") val contents: List<ContentPayload>,
    @Json(name = "generationConfig") val generationConfig: GenerationConfigPayload? = null,
    @Json(name = "systemInstruction") val systemInstruction: ContentPayload? = null
)

@JsonClass(generateAdapter = true)
data class CandidatePayload(
    @Json(name = "content") val content: ContentPayload
)

@JsonClass(generateAdapter = true)
data class GenerateContentResp(
    @Json(name = "candidates") val candidates: List<CandidatePayload>? = null
)

interface GeminiApiService {
    @POST("v1beta/models/gemini-3.5-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GenerateContentReq
    ): GenerateContentResp
}

object RetrofitClient {
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    val service: GeminiApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(GeminiApiService::class.java)
    }
}
