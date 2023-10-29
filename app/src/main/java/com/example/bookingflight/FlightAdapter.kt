package com.example.bookingflight



import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.bookingflight.model.FlightOffer
import com.example.bookingflight.model.Itinerary
import com.example.bookingflight.model.Segment

class FlightAdapter(context: Context, private val resource: Int, private val flights: List<FlightOffer>) :
    ArrayAdapter<FlightOffer>(context, resource, flights) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)

        val flightOffer = flights[position]

        // Afficher les informations du vol dans les vues appropriées
        val textViewOrigin = view.findViewById<TextView>(R.id.textViewOrigin)
        val textViewDestination = view.findViewById<TextView>(R.id.textViewDestination)

        val itinerary = flightOffer.itineraries.firstOrNull()
        val segment = itinerary?.segments?.firstOrNull()

        if (segment != null) {
            textViewOrigin.text = segment.departure.iataCode
            textViewDestination.text = segment.arrival.iataCode
        } else {
            textViewOrigin.text = ""
            textViewDestination.text = ""
        }

        // Afficher d'autres informations du vol...

        return view
    }
}

