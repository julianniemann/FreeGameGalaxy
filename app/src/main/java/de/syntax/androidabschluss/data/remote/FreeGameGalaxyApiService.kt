package de.syntax.androidabschluss.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.syntax.androidabschluss.data.models.Giveaway
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Base URL for connecting to the server
const val BASE_URL = "https://www.gamerpower.com/api/"

// HTTP logging interceptor for monitoring network requests and responses
private val logger: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

// HTTP client for communicating with the server
private val httpClient = OkHttpClient.Builder()
    .addInterceptor(logger) // Adding the logging interceptor
    .build()

// Moshi object for JSON serialization and deserialization
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory()) // Adding the KotlinJsonAdapterFactory
    .build()

// Retrofit object for creating the API service
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi)) // Adding the MoshiConverterFactory
    .baseUrl(BASE_URL) // Setting the base URL
    .client(httpClient) // Setting the HTTP client
    .build()

// Retrofit interface for defining the API endpoints
interface FreeGameGalaxyApiService {
    @GET("giveaways")
    suspend fun getGiveaways(): List<Giveaway>

    @GET("giveaway")
    suspend fun getGiveawayById(@Query("id") id: Int): Giveaway

    @GET("giveaways")
    suspend fun getGiveawaysSortedBy(
        @Query("platform") platform: String?,
        @Query("sort-by") sortBy: String?
    ): List<Giveaway>
}

// Object containing a singleton instance of the Retrofit service
object FreeGameGalaxyApi {
    val retrofitService: FreeGameGalaxyApiService by lazy { retrofit.create(FreeGameGalaxyApiService::class.java) }
}
