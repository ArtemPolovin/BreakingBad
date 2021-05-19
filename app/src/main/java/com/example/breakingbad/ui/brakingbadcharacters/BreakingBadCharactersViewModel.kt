package com.example.breakingbad.ui.brakingbadcharacters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.Resource
import com.example.domain.models.BreakingBadCharacterModel
import com.example.domain.usecases.GetBreakingBadCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreakingBadCharactersViewModel @Inject constructor(
    private val getBreakingBadCharactersUseCase: GetBreakingBadCharactersUseCase
) : ViewModel() {

    private var getDataJob: Job? = null

    var isListFiltered = false

    private val charactersList = mutableListOf<BreakingBadCharacterModel>()

    private val _breakingBadCharacterList =
        MutableLiveData<Resource<List<BreakingBadCharacterModel>>>()
    val breakingBadCharacterList: LiveData<Resource<List<BreakingBadCharacterModel>>> get() = _breakingBadCharacterList

    private val _filteredCharactersList = MutableLiveData<List<BreakingBadCharacterModel>>()
    val filteredCharactersList: LiveData<List<BreakingBadCharacterModel>> get() = _filteredCharactersList

    private fun fetchBreakingBadCharacters() {
        if(getDataJob?.isActive == true)return

        _breakingBadCharacterList.value = Resource.Loading
        isListFiltered = false

       getDataJob =  viewModelScope.launch {
            val result = getBreakingBadCharactersUseCase.execute()
            _breakingBadCharacterList.value = result

            if (result is Resource.Success) {
                charactersList.clear()
                charactersList.addAll(result.data)
            }
        }
    }

    fun filterByName(inputResult: String) {
        if (charactersList.isNotEmpty()) {
            isListFiltered = true

            _filteredCharactersList.value =
                charactersList.filter { it.name.contains(inputResult, true) }
        }
    }

    fun filterBySeason(numberOfSeason: Int) {
        if (charactersList.isNotEmpty()) {
            isListFiltered = true

            _filteredCharactersList.value =
                charactersList.filter { it.seasonAppearance.contains(numberOfSeason) }
        }
    }

    fun refreshBreakingBadCharactersList() {
        fetchBreakingBadCharacters()
    }

}