package com.example.bookingflight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import com.example.bookingflight.api_service.*
import com.example.bookingflight.model.*
import kotlin.properties.Delegates

//import com.google.gson.GsonBuilder
//import okhttp3.OkHttpClient
//import okhttp3.Request
//import java.io.IOException

class MainActivity : AppCompatActivity(), FlightOfferProcessor.FlightOfferCallback {

    private lateinit var editTextOrigin: EditText
    private lateinit var editTextDestination: EditText
    private lateinit var editTextDepartureDate: EditText
    private lateinit var editTextAdult: EditText
    private lateinit var buttonSearchFlights: Button
    private lateinit var listViewFlights: ListView
    //private  var flightOffers: List<FlightOffer> = emptyList()

    private val flightList: ArrayList<FlightOffer> = ArrayList()
    private lateinit var flightAdapter: ArrayAdapter<FlightOffer>


    private val flightOfferApiClient = FlightOfferApiClient()

    // Déclarez une variable pour l'adaptateur
    //private lateinit var flightAdapter: FlightAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialiser les vues
        editTextOrigin = findViewById(R.id.editTextOrigin)
        editTextDestination = findViewById(R.id.editTextDestination)
        editTextDepartureDate = findViewById(R.id.editTextDepartureDate)
        editTextAdult = findViewById(R.id.editTextAdult)
        buttonSearchFlights = findViewById(R.id.buttonSearchFlights)
        listViewFlights = findViewById(R.id.listViewFlights)

        // Définir un écouteur de clic pour le bouton de recherche
        buttonSearchFlights.setOnClickListener {
            val origin = editTextOrigin.text.toString()
            val destination = editTextDestination.text.toString()
            val departureDate = editTextDepartureDate.text.toString()
            val adults = editTextAdult.text.toString().toIntOrNull() ?: 0


            // Appeler la fonction de recherche de vols
            flightOfferApiClient.getFlightOffers(origin, destination, departureDate, adults, callback = this)
        }

        // Dans la méthode onCreate() de votre activité
        val listViewFlights: ListView = findViewById(R.id.listViewFlights)
        flightAdapter = ArrayAdapter(this, R.layout.item_flight, flightList)
        listViewFlights.adapter = flightAdapter


        //val flightOfferProcessor = FlightOfferProcessor()
        //flightOfferProcessor.fetchFlightOffers(this)
    }

    override fun onFlightOffersReceived(apiResponse: ApiResponse) {
        runOnUiThread() {

            // Mettre à jour la liste des vols avec les résultats de recherche
            //val flightAdapter = FlightAdapter(this, R.layout.item_flight, flightOffers)
            //listViewFlights.adapter = flightAdapter

            /*val count = apiResponse.meta.count
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
            }*/

            /*val flightOffers = apiResponse.data
            // Mettez à jour l'adaptateur avec les nouvelles données
            flightAdapter.clear()
            flightAdapter.addAll(flightOffers)
            // Rafraîchissez la ListView pour afficher les nouvelles données
            flightAdapter.notifyDataSetChanged()*/


            val flightOffers = apiResponse.data
            flightList.clear()
            flightList.addAll(flightOffers)
            flightAdapter.notifyDataSetChanged()
        }
    }

    override fun onFlightOffersError(error: Throwable) {

        Log.e("FlightOffersError", "Failed to retrieve flight offers: ${error.message}")
    }

}