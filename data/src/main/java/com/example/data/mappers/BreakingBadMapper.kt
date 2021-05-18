package com.example.data.mappers

import com.example.data.modelsapi.characters.BreakingBadCharacterApi
import com.example.domain.models.BreakingBadCharacterModel

class BreakingBadMapper {
    fun mapBreakingBadApiToModel(breakingBadCharacterApi: BreakingBadCharacterApi): List<BreakingBadCharacterModel> {

        return breakingBadCharacterApi.map { characterApi ->
            BreakingBadCharacterModel(
                name = characterApi.name,
                imageUrl = characterApi.img,
                occupation = characterApi.occupation,
                status = characterApi.status,
                nickName = characterApi.nickname,
                seasonAppearance = characterApi.appearance
            )
        }
    }
}