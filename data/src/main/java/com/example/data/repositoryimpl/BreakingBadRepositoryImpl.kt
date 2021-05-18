package com.example.data.repositoryimpl

import android.util.Log
import com.example.data.mappers.BreakingBadMapper
import com.example.data.network.BreakingBadApi
import com.example.domain.common.Resource
import com.example.domain.models.BreakingBadCharacterModel
import com.example.domain.repositories.BreakingBadRepository
import java.lang.Exception

class BreakingBadRepositoryImpl(
    private val breakingBadApi: BreakingBadApi,
    private val mapper: BreakingBadMapper
): BreakingBadRepository {

    override suspend fun getBreakingBadCharacters(): Resource<List<BreakingBadCharacterModel>> {

          return try{
              val response = breakingBadApi.getBreakingBadCharacters()
              if (response.isSuccessful) {
                  response.body()?.let {
                      return@let Resource.Success(mapper.mapBreakingBadApiToModel(it))
                  } ?: Resource.Failure(message = "An unknown error occured")
              } else {
                  Log.i("mLog", "response error = ${response.errorBody()?.string()}")
                  Resource.Failure(message = "An unknown error occured")
              }
          }catch (e: Exception){
              e.printStackTrace()
              Resource.Failure(message = "Couldn't reach the server. Check your internet connection")
          }
    }
}