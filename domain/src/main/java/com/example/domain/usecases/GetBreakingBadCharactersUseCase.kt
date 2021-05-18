package com.example.domain.usecases

import com.example.domain.repositories.BreakingBadRepository

class GetBreakingBadCharactersUseCase(private val repository: BreakingBadRepository) {
    suspend fun execute() = repository.getBreakingBadCharacters()
}