package com.example.domain.models

import kotlinx.serialization.Serializable

@Serializable
class BreakingBadCharacterModel(
    val name: String,
    val imageUrl: String,
    val occupation: List<String>,
    val status: String,
    val nickName: String,
    val seasonAppearance: List<Int>

)