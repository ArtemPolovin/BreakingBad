package com.example.domain.repositories

import com.example.domain.common.Resource
import com.example.domain.models.BreakingBadCharacterModel

interface BreakingBadRepository {
   suspend fun getBreakingBadCharacters():Resource<List<BreakingBadCharacterModel>>
}