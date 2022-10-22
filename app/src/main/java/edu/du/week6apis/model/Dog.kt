package edu.du.week6apis.model

data class DogModel(
    val name: String,
    val breed: String,
    val weight: Int,
    val shelter: String,
    val adoptable: Boolean
)