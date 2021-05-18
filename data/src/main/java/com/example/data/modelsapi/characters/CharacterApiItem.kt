package com.example.data.modelsapi.characters

data class CharacterApiItem(
    val appearance: List<Int>,
    val img: String,
    val name: String,
    val nickname: String,
    val occupation: List<String>,
    val status: String
)