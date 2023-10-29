package com.example.bookingflight.api_service

import com.example.bookingflight.model.ApiResponse
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class FlightOfferApiClient {

    fun getFlightOffers(
        origin: String,
        destination: String,
        departureDate: String,
        //returnDate: String,
        adults: Int,
        //maxResults: Int,
        callback: FlightOfferProcessor.FlightOfferCallback
    ) {
        val client = OkHttpClient().newBuilder().build()

        val url = HttpUrl.Builder()
            .scheme("https")
            .host("test.api.amadeus.com")
            .addPathSegments("v2/shopping/flight-offers")
            .addQueryParameter("originLocationCode", origin)
            .addQueryParameter("destinationLocationCode", destination)
            .addQueryParameter("departureDate", departureDate)
            //.addQueryParameter("returnDate", returnDate)
            .addQueryParameter("adults", adults.toString())
            //.addQueryParameter("max", maxResults.toString())
            .build()

        val request = Request.Builder()
            .url(url)
            .method("GET", null)
            .addHeader("client_id", "ySMOz1B9DOXx2GYraDkk9URBCmJsE5Lu")
            .addHeader("client_secret", "eekYANDtj0oElRJF")
            .addHeader("Authorization", "Bearer 3O9bOpYPtNLSefB4Y6hDGiXGFM08")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFlightOffersError(e)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
                    val gson = GsonBuilder().create()
                    val apiResponse = gson.fromJson(responseBody, ApiResponse::class.java)
                    callback.onFlightOffersReceived(apiResponse)
                } else {
                    callback.onFlightOffersError(RuntimeException("Failed to retrieve flight offers: ${response.code}"))
                }
            }
        })
    }

}