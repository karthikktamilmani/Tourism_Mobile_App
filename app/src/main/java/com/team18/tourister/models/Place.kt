package com.team18.tourister.models

data class Place(
    var name: String,
    var description: String,
    var type: String,
    var image: String,
    var spot: List<SearchPlace>
)