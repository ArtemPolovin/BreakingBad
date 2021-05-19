package com.example.data.repositoryimpl

import com.example.data.mappers.BreakingBadMapper
import com.example.data.modelsapi.characters.BreakingBadCharacterApi
import com.example.data.modelsapi.characters.CharacterApiItem
import com.example.data.network.BreakingBadApi
import com.example.domain.common.Resource
import com.example.domain.models.BreakingBadCharacterModel
import com.example.domain.repositories.BreakingBadRepository
import com.google.common.truth.Truth.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
class BreakingBadRepositoryImplTest {

    @Mock
    private lateinit var mapper: BreakingBadMapper

    @Mock
    private lateinit var api: BreakingBadApi

    private lateinit var repository: BreakingBadRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        repository = BreakingBadRepositoryImpl(api,mapper)
    }

    @Test
    fun `get success result`() {
        runBlockingTest {

            // Given
            val list = arrayListOf<BreakingBadCharacterModel>()

            `when`(api.getBreakingBadCharacters()).thenReturn(
                Response.success(BreakingBadCharacterApi())
            )

            // When
            val result = repository.getBreakingBadCharacters()

            // Then
            assertThat(result).isEqualTo(Resource.Success(list))

        }

    }

    @Test
    fun `get error result`() {
        runBlockingTest {

            `when`(api.getBreakingBadCharacters()).thenReturn(
                Response.error(400, ResponseBody.create(MediaType.parse("type"),""))
            )

            // When
            val result = repository.getBreakingBadCharacters()

            // Then
            assertThat(result).isEqualTo(Resource.Failure(message = "An unknown error occured"))

        }

    }

}