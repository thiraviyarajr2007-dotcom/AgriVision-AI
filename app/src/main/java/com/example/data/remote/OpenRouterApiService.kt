package com.example.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

@JsonClass(generateAdapter = true)
data class OpenRouterImageUrl(
    @Json(name = "url") val url: String
)

@JsonClass(generateAdapter = true)
data class OpenRouterContentPart(
    @Json(name = "type") val type: String,
    @Json(name = "text") val text: String? = null,
    @Json(name = "image_url") val imageUrl: OpenRouterImageUrl? = null
)

@JsonClass(generateAdapter = true)
data class OpenRouterMessage(
    @Json(name = "role") val role: String,
    @Json(name = "content") val content: List<OpenRouterContentPart>
)

@JsonClass(generateAdapter = true)
data class OpenRouterChatReq(
    @Json(name = "model") val model: String = "google/gemini-2.5-flash",
    @Json(name = "messages") val messages: List<OpenRouterMessage>,
    @Json(name = "temperature") val temperature: Float = 0.2f
)

@JsonClass(generateAdapter = true)
data class OpenRouterChoiceMessage(
    @Json(name = "content") val content: String? = null
)

@JsonClass(generateAdapter = true)
data class OpenRouterChoice(
    @Json(name = "message") val message: OpenRouterChoiceMessage? = null
)

@JsonClass(generateAdapter = true)
data class OpenRouterChatResp(
    @Json(name = "choices") val choices: List<OpenRouterChoice>? = null
)

interface OpenRouterApiService {
    @POST("api/v1/chat/completions")
    suspend fun chatCompletions(
        @Header("Authorization") authHeader: String,
        @Header("HTTP-Referer") referer: String = "https://aistudio.google.com",
        @Header("X-Title") appTitle: String = "AgriVision AI",
        @Body request: OpenRouterChatReq
    ): OpenRouterChatResp
}

object OpenRouterClient {
    private const val BASE_URL = "https://openrouter.ai/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    val service: OpenRouterApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(OpenRouterApiService::class.java)
    }
}
