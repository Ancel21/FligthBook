package com.example.bookingflight.api_service

import com.example.bookingflight.model.*
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class FlightOfferProcessor {

    interface FlightOfferCallback {
        fun onFlightOffersReceived(apiResponse: ApiResponse)
        fun onFlightOffersError(error: Throwable)
    }

    fun fetchFlightOffers(callback: FlightOfferCallback) {
        val client = OkHttpClient().newBuilder().build()
        val request = Request.Builder()
            .url("https://test.api.amadeus.com/v2/shopping/flight-offers?originLocationCode=DLA&destinationLocationCode=PAR&departureDate=2023-04-30&returnDate=2023-05-16&adults=1&max=5")
            .method("GET", null)
            .addHeader("client_secret", "eekYANDtj0oElRJF")
            .addHeader("Authorization", "Bearer 1GiUNRATNyltAGnlcCffCtWa9w2O")
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


    fun processFlightOffers(apiResponse: ApiResponse) {
        // Accédez aux données de l'API à partir de l'objet ApiResponse



        // Exemple 1: Afficher le nombre d'offres de vol
        val count = apiResponse.meta.count
        println("Nombre d'offres de vol : $count")

        // Exemple 2: Parcourir toutes les offres de vol et afficher les codes IATA des aéroports
        for (flightOffer in apiResponse.data) {
            for (itinerary in flightOffer.itineraries) {
                for (segment in itinerary.segments) {
                    val departureAirportCode = segment.departure.iataCode
                    val arrivalAirportCode = segment.arrival.iataCode

                    println("Code IATA de l'aéroport de départ : $departureAirportCode")
                    println("Code IATA de l'aéroport d'arrivée : $arrivalAirportCode")
                }
            }
        }

        // Autres traitements des données de l'API...
    }

}