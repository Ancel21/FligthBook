package com.example.bookingflight.model

data class Links(
    val self: String
)

data class Meta(
    val count: Int,
    val links: Links
)

data class Airport(
    val iataCode: String
)

data class Segment(
    val departure: Airport,
    val arrival: Airport
)

data class Itinerary(
    val segments: List<Segment>
)

data class FlightOffer(
    val itineraries: List<Itinerary>
)

data class ApiResponse(
    val meta: Meta,
    val data: List<FlightOffer>
)