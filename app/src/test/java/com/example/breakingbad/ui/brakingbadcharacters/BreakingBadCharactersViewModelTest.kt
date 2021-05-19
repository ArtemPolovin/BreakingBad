package com.example.breakingbad.ui.brakingbadcharacters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.breakingbad.utils.TestCoroutineRule
import com.example.domain.common.Resource
import com.example.domain.models.BreakingBadCharacterModel
import com.example.domain.repositories.BreakingBadRepository
import com.example.domain.usecases.GetBreakingBadCharactersUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class BreakingBadCharactersViewModelTest {


    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var getBreakingBadCharactersUseCase: GetBreakingBadCharactersUseCase

    @Mock
    private lateinit var resourceObserver: Observer<Resource<List<BreakingBadCharacterModel>>>

    @Mock
    private lateinit var filterObserver: Observer<List<BreakingBadCharacterModel>>

    private lateinit var viewModel: BreakingBadCharactersViewModel


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `should success when fetchFromServer return proper data`() {
        testCoroutineRule.runBlockingTest {

            // Given
            val charactersList = listOf(
                BreakingBadCharacterModel(
                    "Alex1", "url1", listOf("some data"), "status1", "nickname1",
                    listOf(2, 5)
                ),
                BreakingBadCharacterModel(
                    "Alex2", "url2", listOf("some data"), "status2", "nickname2",
                    listOf(2, 5)
                )
            )

            `when`(getBreakingBadCharactersUseCase.execute()).thenReturn(
                Resource.Success(
                    charactersList
                )
            )

            // When
            viewModel = BreakingBadCharactersViewModel(getBreakingBadCharactersUseCase).apply {
                breakingBadCharacterList.observeForever(resourceObserver)
            }
            viewModel.refreshBreakingBadCharactersList()

            //Then
            verify(resourceObserver).onChanged(Resource.Loading)
            verify(resourceObserver).onChanged(Resource.Success(charactersList))

        }
    }

    @Test
    fun `should error when fetchFromServer return failure`() {
        testCoroutineRule.runBlockingTest {

            // Given
            `when`(getBreakingBadCharactersUseCase.execute()).thenReturn(
                Resource.Failure(message = "An unknown error occured")
            )

            // When
            viewModel = BreakingBadCharactersViewModel(getBreakingBadCharactersUseCase).apply {
                breakingBadCharacterList.observeForever(resourceObserver)
            }
            viewModel.refreshBreakingBadCharactersList()

            //Then
            verify(resourceObserver).onChanged(Resource.Loading)
            verify(resourceObserver).onChanged(Resource.Failure(message = "An unknown error occured"))

        }
    }

    @Test
    fun `return proper filtered by name data`() {

        testCoroutineRule.runBlockingTest {

            // Given
            val name1 = "Alex"
            val name2 = "Jon"
            val charactersList = listOf(
                BreakingBadCharacterModel(
                    name1, "url1", listOf("some data"), "status1", "nickname1",
                    listOf(2, 5)
                ),
                BreakingBadCharacterModel(
                    name2, "url2", listOf("some data"), "status2", "nickname2",
                    listOf(2, 5)
                )
            )

            `when`(getBreakingBadCharactersUseCase.execute()).thenReturn(
                Resource.Success(
                    charactersList
                )
            )

            //Then
            viewModel = BreakingBadCharactersViewModel(getBreakingBadCharactersUseCase).apply {
                filteredCharactersList.observeForever(filterObserver)
            }
            viewModel.refreshBreakingBadCharactersList()
            viewModel.filterByName(name1)

            val filteredList = listOf(
                BreakingBadCharacterModel(
                    name1, "url1", listOf("some data"), "status1", "nickname1",
                    listOf(2, 5)
                )
            )

            verify(filterObserver).onChanged(filteredList)

        }
    }

    @Test
    fun `return empty filtered list by name because not found name`() {
        testCoroutineRule.runBlockingTest {

            // Given
            val name1 = "Alex"
            val name2 = "Jon"
            val inputName = "Albert"
            val charactersList = listOf(
                BreakingBadCharacterModel(
                    name1, "url1", listOf("some data"), "status1", "nickname1",
                    listOf(2, 5)
                ),
                BreakingBadCharacterModel(
                    name2, "url2", listOf("some data"), "status2", "nickname2",
                    listOf(2, 5)
                )
            )

            `when`(getBreakingBadCharactersUseCase.execute()).thenReturn(
                Resource.Success(
                    charactersList
                )
            )

            //Then
            viewModel = BreakingBadCharactersViewModel(getBreakingBadCharactersUseCase).apply {
                filteredCharactersList.observeForever(filterObserver)
            }
            viewModel.refreshBreakingBadCharactersList()
            viewModel.filterByName(inputName)

            val filteredList = listOf<BreakingBadCharacterModel>()

            verify(filterObserver).onChanged(filteredList)

        }
    }

    @Test
    fun `return proper filtered by season data`() {
        testCoroutineRule.runBlockingTest {

            // Given
            val inputSeason = 1
            val seasons1 = listOf(2,4)
            val seasons2 = listOf(5,inputSeason)

            val charactersList = listOf(
                BreakingBadCharacterModel(
                    "Alex", "url1", listOf("some data"), "status1", "nickname1",
                    seasons1
                ),
                BreakingBadCharacterModel(
                    "Bob", "url2", listOf("some data"), "status2", "nickname2",
                    seasons2
                )
            )

            `when`(getBreakingBadCharactersUseCase.execute()).thenReturn(
                Resource.Success(
                    charactersList
                )
            )

            //Then
            viewModel = BreakingBadCharactersViewModel(getBreakingBadCharactersUseCase).apply {
                filteredCharactersList.observeForever(filterObserver)
            }
            viewModel.refreshBreakingBadCharactersList()
            viewModel.filterBySeason(inputSeason)

            val filteredList = listOf(
                BreakingBadCharacterModel(
                    "Bob", "url2", listOf("some data"), "status2", "nickname2",
                    seasons2
                )
            )

            verify(filterObserver).onChanged(filteredList)

        }
    }

    @Test
    fun `return empty filtered list by season because not found season`() {
        testCoroutineRule.runBlockingTest {

            // Given
            val inputSeason = 12
            val seasons1 = listOf(2,4)
            val seasons2 = listOf(5,1)

            val charactersList = listOf(
                BreakingBadCharacterModel(
                    "Alex", "url1", listOf("some data"), "status1", "nickname1",
                    seasons1
                ),
                BreakingBadCharacterModel(
                    "Bob", "url2", listOf("some data"), "status2", "nickname2",
                    seasons2
                )
            )

            `when`(getBreakingBadCharactersUseCase.execute()).thenReturn(
                Resource.Success(
                    charactersList
                )
            )

            //Then
            viewModel = BreakingBadCharactersViewModel(getBreakingBadCharactersUseCase).apply {
                filteredCharactersList.observeForever(filterObserver)
            }
            viewModel.refreshBreakingBadCharactersList()
            viewModel.filterBySeason(inputSeason)

            val filteredList = listOf<BreakingBadCharacterModel>()

            verify(filterObserver).onChanged(filteredList)

        }
    }
}