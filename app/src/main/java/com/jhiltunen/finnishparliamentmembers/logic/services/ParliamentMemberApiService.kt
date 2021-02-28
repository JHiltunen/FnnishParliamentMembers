package com.jhiltunen.finnishparliamentmembers.logic.services

import com.jhiltunen.finnishparliamentmembers.ParliamentMember
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://avoindata.eduskunta.fi/"

private val moshi = Moshi.Builder() // create instance of Moshi
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder() // create an instance ofRetrofit and pass an instanceof MoshiConverter
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ParliamentMemberApiService {
    @GET("api/v1/seating/") // end point
    suspend fun getParliamentMembers():List<ParliamentMember>
}

object ParliamentMemberApi {
    val retrofitService: ParliamentMemberApiService by lazy {
        retrofit.create(ParliamentMemberApiService::class.java)
    }
}